package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;
import com.mycompany.myapp.service.criteria.AvionCriteria;
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
 * Service for executing complex queries for {@link Avion} entities in the database.
 * The main input is a {@link AvionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Avion} or a {@link Page} of {@link Avion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvionQueryService extends QueryService<Avion> {

    private final Logger log = LoggerFactory.getLogger(AvionQueryService.class);

    private final AvionRepository avionRepository;

    public AvionQueryService(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    /**
     * Return a {@link List} of {@link Avion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Avion> findByCriteria(AvionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Avion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Avion> findByCriteria(AvionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionRepository.count(specification);
    }

    /**
     * Function to convert {@link AvionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Avion> createSpecification(AvionCriteria criteria) {
        Specification<Avion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Avion_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), Avion_.tipo));
            }
            if (criteria.getMatricula() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricula(), Avion_.matricula));
            }
            if (criteria.getNumeroSerie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroSerie(), Avion_.numeroSerie));
            }
            if (criteria.getEdad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdad(), Avion_.edad));
            }
        }
        return specification;
    }
}
