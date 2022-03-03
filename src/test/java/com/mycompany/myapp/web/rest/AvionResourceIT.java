package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;
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
 * Integration tests for the {@link AvionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvionResourceIT {

    private static final String DEFAULT_TIPO = "Z\\\\-D";
    private static final String UPDATED_TIPO = "#\\\\-Q";

    private static final String DEFAULT_MATRICULA = "7\\\\-U";
    private static final String UPDATED_MATRICULA = "b\\\\-)";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 0;
    private static final Integer UPDATED_EDAD = 1;

    private static final String ENTITY_API_URL = "/api/avions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvionMockMvc;

    private Avion avion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createEntity(EntityManager em) {
        Avion avion = new Avion().tipo(DEFAULT_TIPO).matricula(DEFAULT_MATRICULA).numeroSerie(DEFAULT_NUMERO_SERIE).edad(DEFAULT_EDAD);
        return avion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createUpdatedEntity(EntityManager em) {
        Avion avion = new Avion().tipo(UPDATED_TIPO).matricula(UPDATED_MATRICULA).numeroSerie(UPDATED_NUMERO_SERIE).edad(UPDATED_EDAD);
        return avion;
    }

    @BeforeEach
    public void initTest() {
        avion = createEntity(em);
    }

    @Test
    @Transactional
    void createAvion() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();
        // Create the Avion
        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isCreated());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate + 1);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAvion.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testAvion.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAvion.getEdad()).isEqualTo(DEFAULT_EDAD);
    }

    @Test
    @Transactional
    void createAvionWithExistingId() throws Exception {
        // Create the Avion with an existing ID
        avion.setId(1L);

        int databaseSizeBeforeCreate = avionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setTipo(null);

        // Create the Avion, which fails.

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatriculaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setMatricula(null);

        // Create the Avion, which fails.

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setNumeroSerie(null);

        // Create the Avion, which fails.

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvions() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)));
    }

    @Test
    @Transactional
    void getAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get the avion
        restAvionMockMvc
            .perform(get(ENTITY_API_URL_ID, avion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avion.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD));
    }

    @Test
    @Transactional
    void getNonExistingAvion() throws Exception {
        // Get the avion
        restAvionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion
        Avion updatedAvion = avionRepository.findById(avion.getId()).get();
        // Disconnect from session so that the updates on updatedAvion are not directly saved in db
        em.detach(updatedAvion);
        updatedAvion.tipo(UPDATED_TIPO).matricula(UPDATED_MATRICULA).numeroSerie(UPDATED_NUMERO_SERIE).edad(UPDATED_EDAD);

        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvion))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAvion.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testAvion.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAvion.getEdad()).isEqualTo(UPDATED_EDAD);
    }

    @Test
    @Transactional
    void putNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvionWithPatch() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion using partial update
        Avion partialUpdatedAvion = new Avion();
        partialUpdatedAvion.setId(avion.getId());

        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvion))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAvion.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testAvion.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAvion.getEdad()).isEqualTo(DEFAULT_EDAD);
    }

    @Test
    @Transactional
    void fullUpdateAvionWithPatch() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion using partial update
        Avion partialUpdatedAvion = new Avion();
        partialUpdatedAvion.setId(avion.getId());

        partialUpdatedAvion.tipo(UPDATED_TIPO).matricula(UPDATED_MATRICULA).numeroSerie(UPDATED_NUMERO_SERIE).edad(UPDATED_EDAD);

        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvion))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAvion.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testAvion.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAvion.getEdad()).isEqualTo(UPDATED_EDAD);
    }

    @Test
    @Transactional
    void patchNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeDelete = avionRepository.findAll().size();

        // Delete the avion
        restAvionMockMvc
            .perform(delete(ENTITY_API_URL_ID, avion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
