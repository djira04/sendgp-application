package com.sengpgroup.sengp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sengpgroup.sengp.IntegrationTest;
import com.sengpgroup.sengp.domain.Voyage;
import com.sengpgroup.sengp.repository.VoyageRepository;
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
 * Integration tests for the {@link VoyageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoyageResourceIT {

    private static final String DEFAULT_DEPATURE_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_DEPATURE_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPATURE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DEPATURE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DEPATURE_CITY = "AAAAAAAAAA";
    private static final String UPDATED_DEPATURE_CITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEPATURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPATURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEPATURE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPATURE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ARRIVAL_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ARRIVAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_ARRIVAL = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ARRIVAL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ARRIVAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ARRIVAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ARRIVAL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_KILOS = 1;
    private static final Integer UPDATED_KILOS = 2;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/voyages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoyageMockMvc;

    private Voyage voyage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createEntity(EntityManager em) {
        Voyage voyage = new Voyage()
            .depatureCountry(DEFAULT_DEPATURE_COUNTRY)
            .depatureAddress(DEFAULT_DEPATURE_ADDRESS)
            .depatureCity(DEFAULT_DEPATURE_CITY)
            .depatureDate(DEFAULT_DEPATURE_DATE)
            .depatureTime(DEFAULT_DEPATURE_TIME)
            .arrivalCountry(DEFAULT_ARRIVAL_COUNTRY)
            .arrivalAddress(DEFAULT_ARRIVAL_ADDRESS)
            .cityArrival(DEFAULT_CITY_ARRIVAL)
            .dateArrival(DEFAULT_DATE_ARRIVAL)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .kilos(DEFAULT_KILOS)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .valid(DEFAULT_VALID);
        return voyage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createUpdatedEntity(EntityManager em) {
        Voyage voyage = new Voyage()
            .depatureCountry(UPDATED_DEPATURE_COUNTRY)
            .depatureAddress(UPDATED_DEPATURE_ADDRESS)
            .depatureCity(UPDATED_DEPATURE_CITY)
            .depatureDate(UPDATED_DEPATURE_DATE)
            .depatureTime(UPDATED_DEPATURE_TIME)
            .arrivalCountry(UPDATED_ARRIVAL_COUNTRY)
            .arrivalAddress(UPDATED_ARRIVAL_ADDRESS)
            .cityArrival(UPDATED_CITY_ARRIVAL)
            .dateArrival(UPDATED_DATE_ARRIVAL)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .kilos(UPDATED_KILOS)
            .unitPrice(UPDATED_UNIT_PRICE)
            .valid(UPDATED_VALID);
        return voyage;
    }

    @BeforeEach
    public void initTest() {
        voyage = createEntity(em);
    }

    @Test
    @Transactional
    void createVoyage() throws Exception {
        int databaseSizeBeforeCreate = voyageRepository.findAll().size();
        // Create the Voyage
        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isCreated());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate + 1);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDepatureCountry()).isEqualTo(DEFAULT_DEPATURE_COUNTRY);
        assertThat(testVoyage.getDepatureAddress()).isEqualTo(DEFAULT_DEPATURE_ADDRESS);
        assertThat(testVoyage.getDepatureCity()).isEqualTo(DEFAULT_DEPATURE_CITY);
        assertThat(testVoyage.getDepatureDate()).isEqualTo(DEFAULT_DEPATURE_DATE);
        assertThat(testVoyage.getDepatureTime()).isEqualTo(DEFAULT_DEPATURE_TIME);
        assertThat(testVoyage.getArrivalCountry()).isEqualTo(DEFAULT_ARRIVAL_COUNTRY);
        assertThat(testVoyage.getArrivalAddress()).isEqualTo(DEFAULT_ARRIVAL_ADDRESS);
        assertThat(testVoyage.getCityArrival()).isEqualTo(DEFAULT_CITY_ARRIVAL);
        assertThat(testVoyage.getDateArrival()).isEqualTo(DEFAULT_DATE_ARRIVAL);
        assertThat(testVoyage.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testVoyage.getKilos()).isEqualTo(DEFAULT_KILOS);
        assertThat(testVoyage.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testVoyage.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createVoyageWithExistingId() throws Exception {
        // Create the Voyage with an existing ID
        voyage.setId(1L);

        int databaseSizeBeforeCreate = voyageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoyages() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].depatureCountry").value(hasItem(DEFAULT_DEPATURE_COUNTRY)))
            .andExpect(jsonPath("$.[*].depatureAddress").value(hasItem(DEFAULT_DEPATURE_ADDRESS)))
            .andExpect(jsonPath("$.[*].depatureCity").value(hasItem(DEFAULT_DEPATURE_CITY)))
            .andExpect(jsonPath("$.[*].depatureDate").value(hasItem(DEFAULT_DEPATURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].depatureTime").value(hasItem(DEFAULT_DEPATURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrivalCountry").value(hasItem(DEFAULT_ARRIVAL_COUNTRY)))
            .andExpect(jsonPath("$.[*].arrivalAddress").value(hasItem(DEFAULT_ARRIVAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].cityArrival").value(hasItem(DEFAULT_CITY_ARRIVAL)))
            .andExpect(jsonPath("$.[*].dateArrival").value(hasItem(DEFAULT_DATE_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].kilos").value(hasItem(DEFAULT_KILOS)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    void getVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get the voyage
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL_ID, voyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voyage.getId().intValue()))
            .andExpect(jsonPath("$.depatureCountry").value(DEFAULT_DEPATURE_COUNTRY))
            .andExpect(jsonPath("$.depatureAddress").value(DEFAULT_DEPATURE_ADDRESS))
            .andExpect(jsonPath("$.depatureCity").value(DEFAULT_DEPATURE_CITY))
            .andExpect(jsonPath("$.depatureDate").value(DEFAULT_DEPATURE_DATE.toString()))
            .andExpect(jsonPath("$.depatureTime").value(DEFAULT_DEPATURE_TIME.toString()))
            .andExpect(jsonPath("$.arrivalCountry").value(DEFAULT_ARRIVAL_COUNTRY))
            .andExpect(jsonPath("$.arrivalAddress").value(DEFAULT_ARRIVAL_ADDRESS))
            .andExpect(jsonPath("$.cityArrival").value(DEFAULT_CITY_ARRIVAL))
            .andExpect(jsonPath("$.dateArrival").value(DEFAULT_DATE_ARRIVAL.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.kilos").value(DEFAULT_KILOS))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingVoyage() throws Exception {
        // Get the voyage
        restVoyageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage
        Voyage updatedVoyage = voyageRepository.findById(voyage.getId()).get();
        // Disconnect from session so that the updates on updatedVoyage are not directly saved in db
        em.detach(updatedVoyage);
        updatedVoyage
            .depatureCountry(UPDATED_DEPATURE_COUNTRY)
            .depatureAddress(UPDATED_DEPATURE_ADDRESS)
            .depatureCity(UPDATED_DEPATURE_CITY)
            .depatureDate(UPDATED_DEPATURE_DATE)
            .depatureTime(UPDATED_DEPATURE_TIME)
            .arrivalCountry(UPDATED_ARRIVAL_COUNTRY)
            .arrivalAddress(UPDATED_ARRIVAL_ADDRESS)
            .cityArrival(UPDATED_CITY_ARRIVAL)
            .dateArrival(UPDATED_DATE_ARRIVAL)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .kilos(UPDATED_KILOS)
            .unitPrice(UPDATED_UNIT_PRICE)
            .valid(UPDATED_VALID);

        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoyage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDepatureCountry()).isEqualTo(UPDATED_DEPATURE_COUNTRY);
        assertThat(testVoyage.getDepatureAddress()).isEqualTo(UPDATED_DEPATURE_ADDRESS);
        assertThat(testVoyage.getDepatureCity()).isEqualTo(UPDATED_DEPATURE_CITY);
        assertThat(testVoyage.getDepatureDate()).isEqualTo(UPDATED_DEPATURE_DATE);
        assertThat(testVoyage.getDepatureTime()).isEqualTo(UPDATED_DEPATURE_TIME);
        assertThat(testVoyage.getArrivalCountry()).isEqualTo(UPDATED_ARRIVAL_COUNTRY);
        assertThat(testVoyage.getArrivalAddress()).isEqualTo(UPDATED_ARRIVAL_ADDRESS);
        assertThat(testVoyage.getCityArrival()).isEqualTo(UPDATED_CITY_ARRIVAL);
        assertThat(testVoyage.getDateArrival()).isEqualTo(UPDATED_DATE_ARRIVAL);
        assertThat(testVoyage.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testVoyage.getKilos()).isEqualTo(UPDATED_KILOS);
        assertThat(testVoyage.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testVoyage.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voyage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        partialUpdatedVoyage
            .depatureCountry(UPDATED_DEPATURE_COUNTRY)
            .depatureAddress(UPDATED_DEPATURE_ADDRESS)
            .depatureDate(UPDATED_DEPATURE_DATE)
            .cityArrival(UPDATED_CITY_ARRIVAL)
            .unitPrice(UPDATED_UNIT_PRICE)
            .valid(UPDATED_VALID);

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDepatureCountry()).isEqualTo(UPDATED_DEPATURE_COUNTRY);
        assertThat(testVoyage.getDepatureAddress()).isEqualTo(UPDATED_DEPATURE_ADDRESS);
        assertThat(testVoyage.getDepatureCity()).isEqualTo(DEFAULT_DEPATURE_CITY);
        assertThat(testVoyage.getDepatureDate()).isEqualTo(UPDATED_DEPATURE_DATE);
        assertThat(testVoyage.getDepatureTime()).isEqualTo(DEFAULT_DEPATURE_TIME);
        assertThat(testVoyage.getArrivalCountry()).isEqualTo(DEFAULT_ARRIVAL_COUNTRY);
        assertThat(testVoyage.getArrivalAddress()).isEqualTo(DEFAULT_ARRIVAL_ADDRESS);
        assertThat(testVoyage.getCityArrival()).isEqualTo(UPDATED_CITY_ARRIVAL);
        assertThat(testVoyage.getDateArrival()).isEqualTo(DEFAULT_DATE_ARRIVAL);
        assertThat(testVoyage.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testVoyage.getKilos()).isEqualTo(DEFAULT_KILOS);
        assertThat(testVoyage.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testVoyage.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void fullUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        partialUpdatedVoyage
            .depatureCountry(UPDATED_DEPATURE_COUNTRY)
            .depatureAddress(UPDATED_DEPATURE_ADDRESS)
            .depatureCity(UPDATED_DEPATURE_CITY)
            .depatureDate(UPDATED_DEPATURE_DATE)
            .depatureTime(UPDATED_DEPATURE_TIME)
            .arrivalCountry(UPDATED_ARRIVAL_COUNTRY)
            .arrivalAddress(UPDATED_ARRIVAL_ADDRESS)
            .cityArrival(UPDATED_CITY_ARRIVAL)
            .dateArrival(UPDATED_DATE_ARRIVAL)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .kilos(UPDATED_KILOS)
            .unitPrice(UPDATED_UNIT_PRICE)
            .valid(UPDATED_VALID);

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDepatureCountry()).isEqualTo(UPDATED_DEPATURE_COUNTRY);
        assertThat(testVoyage.getDepatureAddress()).isEqualTo(UPDATED_DEPATURE_ADDRESS);
        assertThat(testVoyage.getDepatureCity()).isEqualTo(UPDATED_DEPATURE_CITY);
        assertThat(testVoyage.getDepatureDate()).isEqualTo(UPDATED_DEPATURE_DATE);
        assertThat(testVoyage.getDepatureTime()).isEqualTo(UPDATED_DEPATURE_TIME);
        assertThat(testVoyage.getArrivalCountry()).isEqualTo(UPDATED_ARRIVAL_COUNTRY);
        assertThat(testVoyage.getArrivalAddress()).isEqualTo(UPDATED_ARRIVAL_ADDRESS);
        assertThat(testVoyage.getCityArrival()).isEqualTo(UPDATED_CITY_ARRIVAL);
        assertThat(testVoyage.getDateArrival()).isEqualTo(UPDATED_DATE_ARRIVAL);
        assertThat(testVoyage.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testVoyage.getKilos()).isEqualTo(UPDATED_KILOS);
        assertThat(testVoyage.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testVoyage.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeDelete = voyageRepository.findAll().size();

        // Delete the voyage
        restVoyageMockMvc
            .perform(delete(ENTITY_API_URL_ID, voyage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
