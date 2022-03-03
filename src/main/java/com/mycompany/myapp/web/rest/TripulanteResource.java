package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Tripulante;
import com.mycompany.myapp.repository.TripulanteRepository;
import com.mycompany.myapp.service.TripulanteQueryService;
import com.mycompany.myapp.service.TripulanteService;
import com.mycompany.myapp.service.criteria.TripulanteCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Tripulante}.
 */
@RestController
@RequestMapping("/api")
public class TripulanteResource {

    private final Logger log = LoggerFactory.getLogger(TripulanteResource.class);

    private static final String ENTITY_NAME = "tripulante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TripulanteService tripulanteService;

    private final TripulanteRepository tripulanteRepository;

    private final TripulanteQueryService tripulanteQueryService;

    public TripulanteResource(
        TripulanteService tripulanteService,
        TripulanteRepository tripulanteRepository,
        TripulanteQueryService tripulanteQueryService
    ) {
        this.tripulanteService = tripulanteService;
        this.tripulanteRepository = tripulanteRepository;
        this.tripulanteQueryService = tripulanteQueryService;
    }

    /**
     * {@code POST  /tripulantes} : Create a new tripulante.
     *
     * @param tripulante the tripulante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tripulante, or with status {@code 400 (Bad Request)} if the tripulante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tripulantes")
    public ResponseEntity<Tripulante> createTripulante(@Valid @RequestBody Tripulante tripulante) throws URISyntaxException {
        log.debug("REST request to save Tripulante : {}", tripulante);
        if (tripulante.getId() != null) {
            throw new BadRequestAlertException("A new tripulante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tripulante result = tripulanteService.save(tripulante);
        return ResponseEntity
            .created(new URI("/api/tripulantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tripulantes/:id} : Updates an existing tripulante.
     *
     * @param id the id of the tripulante to save.
     * @param tripulante the tripulante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripulante,
     * or with status {@code 400 (Bad Request)} if the tripulante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tripulante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tripulantes/{id}")
    public ResponseEntity<Tripulante> updateTripulante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tripulante tripulante
    ) throws URISyntaxException {
        log.debug("REST request to update Tripulante : {}, {}", id, tripulante);
        if (tripulante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripulante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripulanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tripulante result = tripulanteService.save(tripulante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tripulante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tripulantes/:id} : Partial updates given fields of an existing tripulante, field will ignore if it is null
     *
     * @param id the id of the tripulante to save.
     * @param tripulante the tripulante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripulante,
     * or with status {@code 400 (Bad Request)} if the tripulante is not valid,
     * or with status {@code 404 (Not Found)} if the tripulante is not found,
     * or with status {@code 500 (Internal Server Error)} if the tripulante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tripulantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tripulante> partialUpdateTripulante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tripulante tripulante
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tripulante partially : {}, {}", id, tripulante);
        if (tripulante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripulante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripulanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tripulante> result = tripulanteService.partialUpdate(tripulante);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tripulante.getId().toString())
        );
    }

    /**
     * {@code GET  /tripulantes} : get all the tripulantes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tripulantes in body.
     */
    @GetMapping("/tripulantes")
    public ResponseEntity<List<Tripulante>> getAllTripulantes(
        TripulanteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Tripulantes by criteria: {}", criteria);
        Page<Tripulante> page = tripulanteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tripulantes/count} : count all the tripulantes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tripulantes/count")
    public ResponseEntity<Long> countTripulantes(TripulanteCriteria criteria) {
        log.debug("REST request to count Tripulantes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tripulanteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tripulantes/:id} : get the "id" tripulante.
     *
     * @param id the id of the tripulante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tripulante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tripulantes/{id}")
    public ResponseEntity<Tripulante> getTripulante(@PathVariable Long id) {
        log.debug("REST request to get Tripulante : {}", id);
        Optional<Tripulante> tripulante = tripulanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tripulante);
    }

    /**
     * {@code DELETE  /tripulantes/:id} : delete the "id" tripulante.
     *
     * @param id the id of the tripulante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tripulantes/{id}")
    public ResponseEntity<Void> deleteTripulante(@PathVariable Long id) {
        log.debug("REST request to delete Tripulante : {}", id);
        tripulanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
