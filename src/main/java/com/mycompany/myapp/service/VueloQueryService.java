package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Vuelo;
import com.mycompany.myapp.repository.VueloRepository;
import com.mycompany.myapp.service.criteria.VueloCriteria;
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
 * Service for executing complex queries for {@link Vuelo} entities in the database.
 * The main input is a {@link VueloCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Vuelo} or a {@link Page} of {@link Vuelo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VueloQueryService extends QueryService<Vuelo> {

    private final Logger log = LoggerFactory.getLogger(VueloQueryService.class);

    private final VueloRepository vueloRepository;

    public VueloQueryService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    /**
     * Return a {@link List} of {@link Vuelo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Vuelo> findByCriteria(VueloCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vuelo> specification = createSpecification(criteria);
        return vueloRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Vuelo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Vuelo> findByCriteria(VueloCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vuelo> specification = createSpecification(criteria);
        return vueloRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VueloCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vuelo> specification = createSpecification(criteria);
        return vueloRepository.count(specification);
    }

    /**
     * Function to convert {@link VueloCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vuelo> createSpecification(VueloCriteria criteria) {
        Specification<Vuelo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vuelo_.id));
            }
            if (criteria.getPasaporteCovid() != null) {
                specification = specification.and(buildSpecification(criteria.getPasaporteCovid(), Vuelo_.pasaporteCovid));
            }
            if (criteria.getNumeroDeVuelo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroDeVuelo(), Vuelo_.numeroDeVuelo));
            }
        }
        return specification;
    }
}
