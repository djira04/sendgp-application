package com.sengpgroup.sengp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sengpgroup.sengp.IntegrationTest;
import com.sengpgroup.sengp.domain.Billet;
import com.sengpgroup.sengp.repository.BilletRepository;
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
 * Integration tests for the {@link BilletResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BilletResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/billets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBilletMockMvc;

    private Billet billet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billet createEntity(EntityManager em) {
        Billet billet = new Billet().label(DEFAULT_LABEL).url(DEFAULT_URL).website(DEFAULT_WEBSITE);
        return billet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billet createUpdatedEntity(EntityManager em) {
        Billet billet = new Billet().label(UPDATED_LABEL).url(UPDATED_URL).website(UPDATED_WEBSITE);
        return billet;
    }

    @BeforeEach
    public void initTest() {
        billet = createEntity(em);
    }

    @Test
    @Transactional
    void createBillet() throws Exception {
        int databaseSizeBeforeCreate = billetRepository.findAll().size();
        // Create the Billet
        restBilletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billet)))
            .andExpect(status().isCreated());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeCreate + 1);
        Billet testBillet = billetList.get(billetList.size() - 1);
        assertThat(testBillet.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBillet.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBillet.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
    }

    @Test
    @Transactional
    void createBilletWithExistingId() throws Exception {
        // Create the Billet with an existing ID
        billet.setId(1L);

        int databaseSizeBeforeCreate = billetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBilletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billet)))
            .andExpect(status().isBadRequest());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBillets() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        // Get all the billetList
        restBilletMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billet.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)));
    }

    @Test
    @Transactional
    void getBillet() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        // Get the billet
        restBilletMockMvc
            .perform(get(ENTITY_API_URL_ID, billet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billet.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE));
    }

    @Test
    @Transactional
    void getNonExistingBillet() throws Exception {
        // Get the billet
        restBilletMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBillet() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        int databaseSizeBeforeUpdate = billetRepository.findAll().size();

        // Update the billet
        Billet updatedBillet = billetRepository.findById(billet.getId()).get();
        // Disconnect from session so that the updates on updatedBillet are not directly saved in db
        em.detach(updatedBillet);
        updatedBillet.label(UPDATED_LABEL).url(UPDATED_URL).website(UPDATED_WEBSITE);

        restBilletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBillet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBillet))
            )
            .andExpect(status().isOk());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
        Billet testBillet = billetList.get(billetList.size() - 1);
        assertThat(testBillet.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBillet.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBillet.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void putNonExistingBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBilletWithPatch() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        int databaseSizeBeforeUpdate = billetRepository.findAll().size();

        // Update the billet using partial update
        Billet partialUpdatedBillet = new Billet();
        partialUpdatedBillet.setId(billet.getId());

        partialUpdatedBillet.website(UPDATED_WEBSITE);

        restBilletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillet))
            )
            .andExpect(status().isOk());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
        Billet testBillet = billetList.get(billetList.size() - 1);
        assertThat(testBillet.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBillet.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBillet.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void fullUpdateBilletWithPatch() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        int databaseSizeBeforeUpdate = billetRepository.findAll().size();

        // Update the billet using partial update
        Billet partialUpdatedBillet = new Billet();
        partialUpdatedBillet.setId(billet.getId());

        partialUpdatedBillet.label(UPDATED_LABEL).url(UPDATED_URL).website(UPDATED_WEBSITE);

        restBilletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillet))
            )
            .andExpect(status().isOk());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
        Billet testBillet = billetList.get(billetList.size() - 1);
        assertThat(testBillet.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBillet.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBillet.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void patchNonExistingBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillet() throws Exception {
        int databaseSizeBeforeUpdate = billetRepository.findAll().size();
        billet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBilletMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(billet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billet in the database
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillet() throws Exception {
        // Initialize the database
        billetRepository.saveAndFlush(billet);

        int databaseSizeBeforeDelete = billetRepository.findAll().size();

        // Delete the billet
        restBilletMockMvc
            .perform(delete(ENTITY_API_URL_ID, billet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Billet> billetList = billetRepository.findAll();
        assertThat(billetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
