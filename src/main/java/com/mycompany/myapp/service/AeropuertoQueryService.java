package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Aeropuerto;
import com.mycompany.myapp.repository.AeropuertoRepository;
import com.mycompany.myapp.service.criteria.AeropuertoCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Aeropuerto} entities in the database.
 * The main input is a {@link AeropuertoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Aeropuerto} or a {@link Page} of {@link Aeropuerto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AeropuertoQueryService extends QueryService<Aeropuerto> {

    private final Logger log = LoggerFactory.getLogger(AeropuertoQueryService.class);

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoQueryService(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    /**
     * Return a {@link List} of {@link Aeropuerto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Aeropuerto> findByCriteria(AeropuertoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Aeropuerto> specification = createSpecification(criteria);
        return aeropuertoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Aeropuerto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Aeropuerto> findByCriteria(AeropuertoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Aeropuerto> specification = createSpecification(criteria);
        return aeropuertoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AeropuertoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Aeropuerto> specification = createSpecification(criteria);
        return aeropuertoRepository.count(specification);
    }

    /**
     * Function to convert {@link AeropuertoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Aeropuerto> createSpecification(AeropuertoCriteria criteria) {
        Specification<Aeropuerto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Aeropuerto_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Aeropuerto_.nombre));
            }
            if (criteria.getCiudad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCiudad(), Aeropuerto_.ciudad));
            }
        }
        return specification;
    }
}
