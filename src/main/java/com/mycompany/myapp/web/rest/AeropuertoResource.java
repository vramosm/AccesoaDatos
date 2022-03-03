package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Aeropuerto;
import com.mycompany.myapp.repository.AeropuertoRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Aeropuerto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AeropuertoResource {

    private final Logger log = LoggerFactory.getLogger(AeropuertoResource.class);

    private static final String ENTITY_NAME = "aeropuerto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoResource(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    /**
     * {@code POST  /aeropuertos} : Create a new aeropuerto.
     *
     * @param aeropuerto the aeropuerto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeropuerto, or with status {@code 400 (Bad Request)} if the aeropuerto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeropuertos")
    public ResponseEntity<Aeropuerto> createAeropuerto(@Valid @RequestBody Aeropuerto aeropuerto) throws URISyntaxException {
        log.debug("REST request to save Aeropuerto : {}", aeropuerto);
        if (aeropuerto.getId() != null) {
            throw new BadRequestAlertException("A new aeropuerto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aeropuerto result = aeropuertoRepository.save(aeropuerto);
        return ResponseEntity
            .created(new URI("/api/aeropuertos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeropuertos/:id} : Updates an existing aeropuerto.
     *
     * @param id the id of the aeropuerto to save.
     * @param aeropuerto the aeropuerto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuerto,
     * or with status {@code 400 (Bad Request)} if the aeropuerto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeropuerto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeropuertos/{id}")
    public ResponseEntity<Aeropuerto> updateAeropuerto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Aeropuerto aeropuerto
    ) throws URISyntaxException {
        log.debug("REST request to update Aeropuerto : {}, {}", id, aeropuerto);
        if (aeropuerto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeropuerto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeropuertoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Aeropuerto result = aeropuertoRepository.save(aeropuerto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aeropuerto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aeropuertos/:id} : Partial updates given fields of an existing aeropuerto, field will ignore if it is null
     *
     * @param id the id of the aeropuerto to save.
     * @param aeropuerto the aeropuerto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuerto,
     * or with status {@code 400 (Bad Request)} if the aeropuerto is not valid,
     * or with status {@code 404 (Not Found)} if the aeropuerto is not found,
     * or with status {@code 500 (Internal Server Error)} if the aeropuerto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aeropuertos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aeropuerto> partialUpdateAeropuerto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Aeropuerto aeropuerto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aeropuerto partially : {}, {}", id, aeropuerto);
        if (aeropuerto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeropuerto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeropuertoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aeropuerto> result = aeropuertoRepository
            .findById(aeropuerto.getId())
            .map(existingAeropuerto -> {
                if (aeropuerto.getNombre() != null) {
                    existingAeropuerto.setNombre(aeropuerto.getNombre());
                }
                if (aeropuerto.getCiudad() != null) {
                    existingAeropuerto.setCiudad(aeropuerto.getCiudad());
                }

                return existingAeropuerto;
            })
            .map(aeropuertoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aeropuerto.getId().toString())
        );
    }

    /**
     * {@code GET  /aeropuertos} : get all the aeropuertos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeropuertos in body.
     */
    @GetMapping("/aeropuertos")
    public ResponseEntity<List<Aeropuerto>> getAllAeropuertos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Aeropuertos");
        Page<Aeropuerto> page = aeropuertoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aeropuertos/:id} : get the "id" aeropuerto.
     *
     * @param id the id of the aeropuerto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeropuerto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeropuertos/{id}")
    public ResponseEntity<Aeropuerto> getAeropuerto(@PathVariable Long id) {
        log.debug("REST request to get Aeropuerto : {}", id);
        Optional<Aeropuerto> aeropuerto = aeropuertoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aeropuerto);
    }

    /**
     * {@code DELETE  /aeropuertos/:id} : delete the "id" aeropuerto.
     *
     * @param id the id of the aeropuerto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeropuertos/{id}")
    public ResponseEntity<Void> deleteAeropuerto(@PathVariable Long id) {
        log.debug("REST request to delete Aeropuerto : {}", id);
        aeropuertoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
