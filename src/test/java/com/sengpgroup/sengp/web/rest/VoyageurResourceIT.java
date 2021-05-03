package com.sengpgroup.sengp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sengpgroup.sengp.IntegrationTest;
import com.sengpgroup.sengp.domain.Voyageur;
import com.sengpgroup.sengp.repository.VoyageurRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoyageurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoyageurResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final Instant DEFAULT_BORN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BORN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voyageurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoyageurRepository voyageurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoyageurMockMvc;

    private Voyageur voyageur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyageur createEntity(EntityManager em) {
        Voyageur voyageur = new Voyageur()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .telephone(DEFAULT_TELEPHONE)
            .bornDate(DEFAULT_BORN_DATE)
            .cin(DEFAULT_CIN)
            .photo(DEFAULT_PHOTO);
        return voyageur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyageur createUpdatedEntity(EntityManager em) {
        Voyageur voyageur = new Voyageur()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .telephone(UPDATED_TELEPHONE)
            .bornDate(UPDATED_BORN_DATE)
            .cin(UPDATED_CIN)
            .photo(UPDATED_PHOTO);
        return voyageur;
    }

    @BeforeEach
    public void initTest() {
        voyageur = createEntity(em);
    }

    @Test
    @Transactional
    void createVoyageur() throws Exception {
        int databaseSizeBeforeCreate = voyageurRepository.findAll().size();
        // Create the Voyageur
        restVoyageurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyageur)))
            .andExpect(status().isCreated());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeCreate + 1);
        Voyageur testVoyageur = voyageurList.get(voyageurList.size() - 1);
        assertThat(testVoyageur.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testVoyageur.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testVoyageur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVoyageur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testVoyageur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testVoyageur.getBornDate()).isEqualTo(DEFAULT_BORN_DATE);
        assertThat(testVoyageur.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testVoyageur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void createVoyageurWithExistingId() throws Exception {
        // Create the Voyageur with an existing ID
        voyageur.setId(1L);

        int databaseSizeBeforeCreate = voyageurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoyageurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyageur)))
            .andExpect(status().isBadRequest());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoyageurs() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        // Get all the voyageurList
        restVoyageurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyageur.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].bornDate").value(hasItem(DEFAULT_BORN_DATE.toString())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getVoyageur() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        // Get the voyageur
        restVoyageurMockMvc
            .perform(get(ENTITY_API_URL_ID, voyageur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voyageur.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.bornDate").value(DEFAULT_BORN_DATE.toString()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO));
    }

    @Test
    @Transactional
    void getNonExistingVoyageur() throws Exception {
        // Get the voyageur
        restVoyageurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoyageur() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();

        // Update the voyageur
        Voyageur updatedVoyageur = voyageurRepository.findById(voyageur.getId()).get();
        // Disconnect from session so that the updates on updatedVoyageur are not directly saved in db
        em.detach(updatedVoyageur);
        updatedVoyageur
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .telephone(UPDATED_TELEPHONE)
            .bornDate(UPDATED_BORN_DATE)
            .cin(UPDATED_CIN)
            .photo(UPDATED_PHOTO);

        restVoyageurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoyageur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVoyageur))
            )
            .andExpect(status().isOk());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
        Voyageur testVoyageur = voyageurList.get(voyageurList.size() - 1);
        assertThat(testVoyageur.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testVoyageur.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVoyageur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVoyageur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testVoyageur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testVoyageur.getBornDate()).isEqualTo(UPDATED_BORN_DATE);
        assertThat(testVoyageur.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testVoyageur.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void putNonExistingVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voyageur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyageur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyageur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyageur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoyageurWithPatch() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();

        // Update the voyageur using partial update
        Voyageur partialUpdatedVoyageur = new Voyageur();
        partialUpdatedVoyageur.setId(voyageur.getId());

        partialUpdatedVoyageur.firstname(UPDATED_FIRSTNAME).lastname(UPDATED_LASTNAME).telephone(UPDATED_TELEPHONE);

        restVoyageurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyageur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyageur))
            )
            .andExpect(status().isOk());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
        Voyageur testVoyageur = voyageurList.get(voyageurList.size() - 1);
        assertThat(testVoyageur.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testVoyageur.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVoyageur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVoyageur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testVoyageur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testVoyageur.getBornDate()).isEqualTo(DEFAULT_BORN_DATE);
        assertThat(testVoyageur.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testVoyageur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void fullUpdateVoyageurWithPatch() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();

        // Update the voyageur using partial update
        Voyageur partialUpdatedVoyageur = new Voyageur();
        partialUpdatedVoyageur.setId(voyageur.getId());

        partialUpdatedVoyageur
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .telephone(UPDATED_TELEPHONE)
            .bornDate(UPDATED_BORN_DATE)
            .cin(UPDATED_CIN)
            .photo(UPDATED_PHOTO);

        restVoyageurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyageur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyageur))
            )
            .andExpect(status().isOk());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
        Voyageur testVoyageur = voyageurList.get(voyageurList.size() - 1);
        assertThat(testVoyageur.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testVoyageur.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVoyageur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVoyageur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testVoyageur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testVoyageur.getBornDate()).isEqualTo(UPDATED_BORN_DATE);
        assertThat(testVoyageur.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testVoyageur.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void patchNonExistingVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voyageur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyageur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyageur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoyageur() throws Exception {
        int databaseSizeBeforeUpdate = voyageurRepository.findAll().size();
        voyageur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voyageur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyageur in the database
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoyageur() throws Exception {
        // Initialize the database
        voyageurRepository.saveAndFlush(voyageur);

        int databaseSizeBeforeDelete = voyageurRepository.findAll().size();

        // Delete the voyageur
        restVoyageurMockMvc
            .perform(delete(ENTITY_API_URL_ID, voyageur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voyageur> voyageurList = voyageurRepository.findAll();
        assertThat(voyageurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
