package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vuelo;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VueloRepositoryWithBagRelationshipsImpl implements VueloRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Vuelo> fetchBagRelationships(Optional<Vuelo> vuelo) {
        return vuelo.map(this::fetchTripulacions);
    }

    @Override
    public Page<Vuelo> fetchBagRelationships(Page<Vuelo> vuelos) {
        return new PageImpl<>(fetchBagRelationships(vuelos.getContent()), vuelos.getPageable(), vuelos.getTotalElements());
    }

    @Override
    public List<Vuelo> fetchBagRelationships(List<Vuelo> vuelos) {
        return Optional.of(vuelos).map(this::fetchTripulacions).get();
    }

    Vuelo fetchTripulacions(Vuelo result) {
        return entityManager
            .createQuery("select vuelo from Vuelo vuelo left join fetch vuelo.tripulacions where vuelo is :vuelo", Vuelo.class)
            .setParameter("vuelo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Vuelo> fetchTripulacions(List<Vuelo> vuelos) {
        return entityManager
            .createQuery("select distinct vuelo from Vuelo vuelo left join fetch vuelo.tripulacions where vuelo in :vuelos", Vuelo.class)
            .setParameter("vuelos", vuelos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
