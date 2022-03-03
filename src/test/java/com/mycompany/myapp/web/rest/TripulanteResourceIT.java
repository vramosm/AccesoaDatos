package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Tripulante;
import com.mycompany.myapp.repository.TripulanteRepository;
import com.mycompany.myapp.service.criteria.TripulanteCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TripulanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TripulanteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "53375638F";
    private static final String UPDATED_DNI = "43572234D";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "xk:@%67E_\\_-)%NY";
    private static final String UPDATED_EMAIL = "T@qY)\\fT'";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/tripulantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TripulanteRepository tripulanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTripulanteMockMvc;

    private Tripulante tripulante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tripulante createEntity(EntityManager em) {
        Tripulante tripulante = new Tripulante()
            .nombre(DEFAULT_NOMBRE)
            .apellidos(DEFAULT_APELLIDOS)
            .dni(DEFAULT_DNI)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return tripulante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tripulante createUpdatedEntity(EntityManager em) {
        Tripulante tripulante = new Tripulante()
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        return tripulante;
    }

    @BeforeEach
    public void initTest() {
        tripulante = createEntity(em);
    }

    @Test
    @Transactional
    void createTripulante() throws Exception {
        int databaseSizeBeforeCreate = tripulanteRepository.findAll().size();
        // Create the Tripulante
        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isCreated());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeCreate + 1);
        Tripulante testTripulante = tripulanteList.get(tripulanteList.size() - 1);
        assertThat(testTripulante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTripulante.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testTripulante.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testTripulante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testTripulante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTripulante.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testTripulante.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createTripulanteWithExistingId() throws Exception {
        // Create the Tripulante with an existing ID
        tripulante.setId(1L);

        int databaseSizeBeforeCreate = tripulanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripulanteRepository.findAll().size();
        // set the field null
        tripulante.setNombre(null);

        // Create the Tripulante, which fails.

        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripulanteRepository.findAll().size();
        // set the field null
        tripulante.setApellidos(null);

        // Create the Tripulante, which fails.

        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripulanteRepository.findAll().size();
        // set the field null
        tripulante.setDni(null);

        // Create the Tripulante, which fails.

        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripulanteRepository.findAll().size();
        // set the field null
        tripulante.setDireccion(null);

        // Create the Tripulante, which fails.

        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripulanteRepository.findAll().size();
        // set the field null
        tripulante.setEmail(null);

        // Create the Tripulante, which fails.

        restTripulanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isBadRequest());

        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTripulantes() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripulante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    void getTripulante() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get the tripulante
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL_ID, tripulante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tripulante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    void getTripulantesByIdFiltering() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        Long id = tripulante.getId();

        defaultTripulanteShouldBeFound("id.equals=" + id);
        defaultTripulanteShouldNotBeFound("id.notEquals=" + id);

        defaultTripulanteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTripulanteShouldNotBeFound("id.greaterThan=" + id);

        defaultTripulanteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTripulanteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre equals to DEFAULT_NOMBRE
        defaultTripulanteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tripulanteList where nombre equals to UPDATED_NOMBRE
        defaultTripulanteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre not equals to DEFAULT_NOMBRE
        defaultTripulanteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the tripulanteList where nombre not equals to UPDATED_NOMBRE
        defaultTripulanteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTripulanteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tripulanteList where nombre equals to UPDATED_NOMBRE
        defaultTripulanteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre is not null
        defaultTripulanteShouldBeFound("nombre.specified=true");

        // Get all the tripulanteList where nombre is null
        defaultTripulanteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre contains DEFAULT_NOMBRE
        defaultTripulanteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the tripulanteList where nombre contains UPDATED_NOMBRE
        defaultTripulanteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTripulantesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where nombre does not contain DEFAULT_NOMBRE
        defaultTripulanteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the tripulanteList where nombre does not contain UPDATED_NOMBRE
        defaultTripulanteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosIsEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos equals to DEFAULT_APELLIDOS
        defaultTripulanteShouldBeFound("apellidos.equals=" + DEFAULT_APELLIDOS);

        // Get all the tripulanteList where apellidos equals to UPDATED_APELLIDOS
        defaultTripulanteShouldNotBeFound("apellidos.equals=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos not equals to DEFAULT_APELLIDOS
        defaultTripulanteShouldNotBeFound("apellidos.notEquals=" + DEFAULT_APELLIDOS);

        // Get all the tripulanteList where apellidos not equals to UPDATED_APELLIDOS
        defaultTripulanteShouldBeFound("apellidos.notEquals=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosIsInShouldWork() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos in DEFAULT_APELLIDOS or UPDATED_APELLIDOS
        defaultTripulanteShouldBeFound("apellidos.in=" + DEFAULT_APELLIDOS + "," + UPDATED_APELLIDOS);

        // Get all the tripulanteList where apellidos equals to UPDATED_APELLIDOS
        defaultTripulanteShouldNotBeFound("apellidos.in=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos is not null
        defaultTripulanteShouldBeFound("apellidos.specified=true");

        // Get all the tripulanteList where apellidos is null
        defaultTripulanteShouldNotBeFound("apellidos.specified=false");
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos contains DEFAULT_APELLIDOS
        defaultTripulanteShouldBeFound("apellidos.contains=" + DEFAULT_APELLIDOS);

        // Get all the tripulanteList where apellidos contains UPDATED_APELLIDOS
        defaultTripulanteShouldNotBeFound("apellidos.contains=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    void getAllTripulantesByApellidosNotContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where apellidos does not contain DEFAULT_APELLIDOS
        defaultTripulanteShouldNotBeFound("apellidos.doesNotContain=" + DEFAULT_APELLIDOS);

        // Get all the tripulanteList where apellidos does not contain UPDATED_APELLIDOS
        defaultTripulanteShouldBeFound("apellidos.doesNotContain=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    void getAllTripulantesByDniIsEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni equals to DEFAULT_DNI
        defaultTripulanteShouldBeFound("dni.equals=" + DEFAULT_DNI);

        // Get all the tripulanteList where dni equals to UPDATED_DNI
        defaultTripulanteShouldNotBeFound("dni.equals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    void getAllTripulantesByDniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni not equals to DEFAULT_DNI
        defaultTripulanteShouldNotBeFound("dni.notEquals=" + DEFAULT_DNI);

        // Get all the tripulanteList where dni not equals to UPDATED_DNI
        defaultTripulanteShouldBeFound("dni.notEquals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    void getAllTripulantesByDniIsInShouldWork() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni in DEFAULT_DNI or UPDATED_DNI
        defaultTripulanteShouldBeFound("dni.in=" + DEFAULT_DNI + "," + UPDATED_DNI);

        // Get all the tripulanteList where dni equals to UPDATED_DNI
        defaultTripulanteShouldNotBeFound("dni.in=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    void getAllTripulantesByDniIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni is not null
        defaultTripulanteShouldBeFound("dni.specified=true");

        // Get all the tripulanteList where dni is null
        defaultTripulanteShouldNotBeFound("dni.specified=false");
    }

    @Test
    @Transactional
    void getAllTripulantesByDniContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni contains DEFAULT_DNI
        defaultTripulanteShouldBeFound("dni.contains=" + DEFAULT_DNI);

        // Get all the tripulanteList where dni contains UPDATED_DNI
        defaultTripulanteShouldNotBeFound("dni.contains=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    void getAllTripulantesByDniNotContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where dni does not contain DEFAULT_DNI
        defaultTripulanteShouldNotBeFound("dni.doesNotContain=" + DEFAULT_DNI);

        // Get all the tripulanteList where dni does not contain UPDATED_DNI
        defaultTripulanteShouldBeFound("dni.doesNotContain=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion equals to DEFAULT_DIRECCION
        defaultTripulanteShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the tripulanteList where direccion equals to UPDATED_DIRECCION
        defaultTripulanteShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion not equals to DEFAULT_DIRECCION
        defaultTripulanteShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the tripulanteList where direccion not equals to UPDATED_DIRECCION
        defaultTripulanteShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultTripulanteShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the tripulanteList where direccion equals to UPDATED_DIRECCION
        defaultTripulanteShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion is not null
        defaultTripulanteShouldBeFound("direccion.specified=true");

        // Get all the tripulanteList where direccion is null
        defaultTripulanteShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion contains DEFAULT_DIRECCION
        defaultTripulanteShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the tripulanteList where direccion contains UPDATED_DIRECCION
        defaultTripulanteShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllTripulantesByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where direccion does not contain DEFAULT_DIRECCION
        defaultTripulanteShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the tripulanteList where direccion does not contain UPDATED_DIRECCION
        defaultTripulanteShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email equals to DEFAULT_EMAIL
        defaultTripulanteShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the tripulanteList where email equals to UPDATED_EMAIL
        defaultTripulanteShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email not equals to DEFAULT_EMAIL
        defaultTripulanteShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the tripulanteList where email not equals to UPDATED_EMAIL
        defaultTripulanteShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultTripulanteShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the tripulanteList where email equals to UPDATED_EMAIL
        defaultTripulanteShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email is not null
        defaultTripulanteShouldBeFound("email.specified=true");

        // Get all the tripulanteList where email is null
        defaultTripulanteShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email contains DEFAULT_EMAIL
        defaultTripulanteShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the tripulanteList where email contains UPDATED_EMAIL
        defaultTripulanteShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTripulantesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        // Get all the tripulanteList where email does not contain DEFAULT_EMAIL
        defaultTripulanteShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the tripulanteList where email does not contain UPDATED_EMAIL
        defaultTripulanteShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTripulanteShouldBeFound(String filter) throws Exception {
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripulante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));

        // Check, that the count call also returns 1
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTripulanteShouldNotBeFound(String filter) throws Exception {
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTripulanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTripulante() throws Exception {
        // Get the tripulante
        restTripulanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTripulante() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();

        // Update the tripulante
        Tripulante updatedTripulante = tripulanteRepository.findById(tripulante.getId()).get();
        // Disconnect from session so that the updates on updatedTripulante are not directly saved in db
        em.detach(updatedTripulante);
        updatedTripulante
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restTripulanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTripulante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTripulante))
            )
            .andExpect(status().isOk());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
        Tripulante testTripulante = tripulanteList.get(tripulanteList.size() - 1);
        assertThat(testTripulante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTripulante.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testTripulante.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testTripulante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testTripulante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTripulante.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testTripulante.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tripulante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripulante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripulante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripulante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTripulanteWithPatch() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();

        // Update the tripulante using partial update
        Tripulante partialUpdatedTripulante = new Tripulante();
        partialUpdatedTripulante.setId(tripulante.getId());

        partialUpdatedTripulante
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restTripulanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTripulante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTripulante))
            )
            .andExpect(status().isOk());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
        Tripulante testTripulante = tripulanteList.get(tripulanteList.size() - 1);
        assertThat(testTripulante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTripulante.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testTripulante.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testTripulante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testTripulante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTripulante.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testTripulante.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTripulanteWithPatch() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();

        // Update the tripulante using partial update
        Tripulante partialUpdatedTripulante = new Tripulante();
        partialUpdatedTripulante.setId(tripulante.getId());

        partialUpdatedTripulante
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restTripulanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTripulante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTripulante))
            )
            .andExpect(status().isOk());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
        Tripulante testTripulante = tripulanteList.get(tripulanteList.size() - 1);
        assertThat(testTripulante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTripulante.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testTripulante.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testTripulante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testTripulante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTripulante.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testTripulante.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tripulante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripulante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripulante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTripulante() throws Exception {
        int databaseSizeBeforeUpdate = tripulanteRepository.findAll().size();
        tripulante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripulanteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tripulante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tripulante in the database
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTripulante() throws Exception {
        // Initialize the database
        tripulanteRepository.saveAndFlush(tripulante);

        int databaseSizeBeforeDelete = tripulanteRepository.findAll().size();

        // Delete the tripulante
        restTripulanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tripulante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tripulante> tripulanteList = tripulanteRepository.findAll();
        assertThat(tripulanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
