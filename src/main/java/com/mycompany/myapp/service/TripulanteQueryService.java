package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Tripulante;
import com.mycompany.myapp.repository.TripulanteRepository;
import com.mycompany.myapp.service.criteria.TripulanteCriteria;
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
 * Service for executing complex queries for {@link Tripulante} entities in the database.
 * The main input is a {@link TripulanteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tripulante} or a {@link Page} of {@link Tripulante} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TripulanteQueryService extends QueryService<Tripulante> {

    private final Logger log = LoggerFactory.getLogger(TripulanteQueryService.class);

    private final TripulanteRepository tripulanteRepository;

    public TripulanteQueryService(TripulanteRepository tripulanteRepository) {
        this.tripulanteRepository = tripulanteRepository;
    }

    /**
     * Return a {@link List} of {@link Tripulante} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tripulante> findByCriteria(TripulanteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tripulante> specification = createSpecification(criteria);
        return tripulanteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tripulante} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tripulante> findByCriteria(TripulanteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tripulante> specification = createSpecification(criteria);
        return tripulanteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TripulanteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tripulante> specification = createSpecification(criteria);
        return tripulanteRepository.count(specification);
    }

    /**
     * Function to convert {@link TripulanteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tripulante> createSpecification(TripulanteCriteria criteria) {
        Specification<Tripulante> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tripulante_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Tripulante_.nombre));
            }
            if (criteria.getApellidos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellidos(), Tripulante_.apellidos));
            }
            if (criteria.getDni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDni(), Tripulante_.dni));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), Tripulante_.direccion));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Tripulante_.email));
            }
        }
        return specification;
    }
}
