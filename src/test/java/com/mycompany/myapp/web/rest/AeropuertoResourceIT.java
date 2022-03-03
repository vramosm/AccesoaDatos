package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Aeropuerto;
import com.mycompany.myapp.repository.AeropuertoRepository;
import com.mycompany.myapp.service.criteria.AeropuertoCriteria;
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
 * Integration tests for the {@link AeropuertoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AeropuertoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aeropuertos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAeropuertoMockMvc;

    private Aeropuerto aeropuerto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto().nombre(DEFAULT_NOMBRE).ciudad(DEFAULT_CIUDAD);
        return aeropuerto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createUpdatedEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto().nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);
        return aeropuerto;
    }

    @BeforeEach
    public void initTest() {
        aeropuerto = createEntity(em);
    }

    @Test
    @Transactional
    void createAeropuerto() throws Exception {
        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();
        // Create the Aeropuerto
        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isCreated());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate + 1);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void createAeropuertoWithExistingId() throws Exception {
        // Create the Aeropuerto with an existing ID
        aeropuerto.setId(1L);

        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeropuertoRepository.findAll().size();
        // set the field null
        aeropuerto.setNombre(null);

        // Create the Aeropuerto, which fails.

        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isBadRequest());

        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCiudadIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeropuertoRepository.findAll().size();
        // set the field null
        aeropuerto.setCiudad(null);

        // Create the Aeropuerto, which fails.

        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isBadRequest());

        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAeropuertos() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeropuerto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)));
    }

    @Test
    @Transactional
    void getAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get the aeropuerto
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL_ID, aeropuerto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aeropuerto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD));
    }

    @Test
    @Transactional
    void getAeropuertosByIdFiltering() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        Long id = aeropuerto.getId();

        defaultAeropuertoShouldBeFound("id.equals=" + id);
        defaultAeropuertoShouldNotBeFound("id.notEquals=" + id);

        defaultAeropuertoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAeropuertoShouldNotBeFound("id.greaterThan=" + id);

        defaultAeropuertoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAeropuertoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre equals to DEFAULT_NOMBRE
        defaultAeropuertoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the aeropuertoList where nombre equals to UPDATED_NOMBRE
        defaultAeropuertoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre not equals to DEFAULT_NOMBRE
        defaultAeropuertoShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the aeropuertoList where nombre not equals to UPDATED_NOMBRE
        defaultAeropuertoShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultAeropuertoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the aeropuertoList where nombre equals to UPDATED_NOMBRE
        defaultAeropuertoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre is not null
        defaultAeropuertoShouldBeFound("nombre.specified=true");

        // Get all the aeropuertoList where nombre is null
        defaultAeropuertoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreContainsSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre contains DEFAULT_NOMBRE
        defaultAeropuertoShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the aeropuertoList where nombre contains UPDATED_NOMBRE
        defaultAeropuertoShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllAeropuertosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where nombre does not contain DEFAULT_NOMBRE
        defaultAeropuertoShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the aeropuertoList where nombre does not contain UPDATED_NOMBRE
        defaultAeropuertoShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadIsEqualToSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad equals to DEFAULT_CIUDAD
        defaultAeropuertoShouldBeFound("ciudad.equals=" + DEFAULT_CIUDAD);

        // Get all the aeropuertoList where ciudad equals to UPDATED_CIUDAD
        defaultAeropuertoShouldNotBeFound("ciudad.equals=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad not equals to DEFAULT_CIUDAD
        defaultAeropuertoShouldNotBeFound("ciudad.notEquals=" + DEFAULT_CIUDAD);

        // Get all the aeropuertoList where ciudad not equals to UPDATED_CIUDAD
        defaultAeropuertoShouldBeFound("ciudad.notEquals=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadIsInShouldWork() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad in DEFAULT_CIUDAD or UPDATED_CIUDAD
        defaultAeropuertoShouldBeFound("ciudad.in=" + DEFAULT_CIUDAD + "," + UPDATED_CIUDAD);

        // Get all the aeropuertoList where ciudad equals to UPDATED_CIUDAD
        defaultAeropuertoShouldNotBeFound("ciudad.in=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad is not null
        defaultAeropuertoShouldBeFound("ciudad.specified=true");

        // Get all the aeropuertoList where ciudad is null
        defaultAeropuertoShouldNotBeFound("ciudad.specified=false");
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadContainsSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad contains DEFAULT_CIUDAD
        defaultAeropuertoShouldBeFound("ciudad.contains=" + DEFAULT_CIUDAD);

        // Get all the aeropuertoList where ciudad contains UPDATED_CIUDAD
        defaultAeropuertoShouldNotBeFound("ciudad.contains=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void getAllAeropuertosByCiudadNotContainsSomething() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList where ciudad does not contain DEFAULT_CIUDAD
        defaultAeropuertoShouldNotBeFound("ciudad.doesNotContain=" + DEFAULT_CIUDAD);

        // Get all the aeropuertoList where ciudad does not contain UPDATED_CIUDAD
        defaultAeropuertoShouldBeFound("ciudad.doesNotContain=" + UPDATED_CIUDAD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAeropuertoShouldBeFound(String filter) throws Exception {
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeropuerto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)));

        // Check, that the count call also returns 1
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAeropuertoShouldNotBeFound(String filter) throws Exception {
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAeropuerto() throws Exception {
        // Get the aeropuerto
        restAeropuertoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto
        Aeropuerto updatedAeropuerto = aeropuertoRepository.findById(aeropuerto.getId()).get();
        // Disconnect from session so that the updates on updatedAeropuerto are not directly saved in db
        em.detach(updatedAeropuerto);
        updatedAeropuerto.nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);

        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAeropuerto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAeropuerto))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void putNonExistingAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aeropuerto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeropuerto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeropuerto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAeropuertoWithPatch() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto using partial update
        Aeropuerto partialUpdatedAeropuerto = new Aeropuerto();
        partialUpdatedAeropuerto.setId(aeropuerto.getId());

        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeropuerto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeropuerto))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void fullUpdateAeropuertoWithPatch() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto using partial update
        Aeropuerto partialUpdatedAeropuerto = new Aeropuerto();
        partialUpdatedAeropuerto.setId(aeropuerto.getId());

        partialUpdatedAeropuerto.nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);

        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeropuerto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeropuerto))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void patchNonExistingAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aeropuerto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeropuerto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeropuerto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aeropuerto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeDelete = aeropuertoRepository.findAll().size();

        // Delete the aeropuerto
        restAeropuertoMockMvc
            .perform(delete(ENTITY_API_URL_ID, aeropuerto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
