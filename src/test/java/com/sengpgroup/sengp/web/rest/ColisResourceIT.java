package com.sengpgroup.sengp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sengpgroup.sengp.IntegrationTest;
import com.sengpgroup.sengp.domain.Colis;
import com.sengpgroup.sengp.repository.ColisRepository;
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
 * Integration tests for the {@link ColisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColisResourceIT {

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColisMockMvc;

    private Colis colis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colis createEntity(EntityManager em) {
        Colis colis = new Colis().weight(DEFAULT_WEIGHT).details(DEFAULT_DETAILS);
        return colis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colis createUpdatedEntity(EntityManager em) {
        Colis colis = new Colis().weight(UPDATED_WEIGHT).details(UPDATED_DETAILS);
        return colis;
    }

    @BeforeEach
    public void initTest() {
        colis = createEntity(em);
    }

    @Test
    @Transactional
    void createColis() throws Exception {
        int databaseSizeBeforeCreate = colisRepository.findAll().size();
        // Create the Colis
        restColisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isCreated());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeCreate + 1);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testColis.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void createColisWithExistingId() throws Exception {
        // Create the Colis with an existing ID
        colis.setId(1L);

        int databaseSizeBeforeCreate = colisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isBadRequest());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllColis() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        // Get all the colisList
        restColisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colis.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }

    @Test
    @Transactional
    void getColis() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        // Get the colis
        restColisMockMvc
            .perform(get(ENTITY_API_URL_ID, colis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colis.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    void getNonExistingColis() throws Exception {
        // Get the colis
        restColisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewColis() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        int databaseSizeBeforeUpdate = colisRepository.findAll().size();

        // Update the colis
        Colis updatedColis = colisRepository.findById(colis.getId()).get();
        // Disconnect from session so that the updates on updatedColis are not directly saved in db
        em.detach(updatedColis);
        updatedColis.weight(UPDATED_WEIGHT).details(UPDATED_DETAILS);

        restColisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColis))
            )
            .andExpect(status().isOk());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testColis.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateColisWithPatch() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        int databaseSizeBeforeUpdate = colisRepository.findAll().size();

        // Update the colis using partial update
        Colis partialUpdatedColis = new Colis();
        partialUpdatedColis.setId(colis.getId());

        partialUpdatedColis.details(UPDATED_DETAILS);

        restColisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColis))
            )
            .andExpect(status().isOk());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testColis.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateColisWithPatch() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        int databaseSizeBeforeUpdate = colisRepository.findAll().size();

        // Update the colis using partial update
        Colis partialUpdatedColis = new Colis();
        partialUpdatedColis.setId(colis.getId());

        partialUpdatedColis.weight(UPDATED_WEIGHT).details(UPDATED_DETAILS);

        restColisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColis))
            )
            .andExpect(status().isOk());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testColis.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();
        colis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteColis() throws Exception {
        // Initialize the database
        colisRepository.saveAndFlush(colis);

        int databaseSizeBeforeDelete = colisRepository.findAll().size();

        // Delete the colis
        restColisMockMvc
            .perform(delete(ENTITY_API_URL_ID, colis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
