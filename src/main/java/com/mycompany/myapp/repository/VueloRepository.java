package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vuelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vuelo entity.
 */
@Repository
public interface VueloRepository extends VueloRepositoryWithBagRelationships, JpaRepository<Vuelo, Long>, JpaSpecificationExecutor<Vuelo> {
    default Optional<Vuelo> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Vuelo> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Vuelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct vuelo from Vuelo vuelo left join fetch vuelo.origen left join fetch vuelo.destino left join fetch vuelo.avion left join fetch vuelo.piloto",
        countQuery = "select count(distinct vuelo) from Vuelo vuelo"
    )
    Page<Vuelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct vuelo from Vuelo vuelo left join fetch vuelo.origen left join fetch vuelo.destino left join fetch vuelo.avion left join fetch vuelo.piloto"
    )
    List<Vuelo> findAllWithToOneRelationships();

    @Query(
        "select vuelo from Vuelo vuelo left join fetch vuelo.origen left join fetch vuelo.destino left join fetch vuelo.avion left join fetch vuelo.piloto where vuelo.id =:id"
    )
    Optional<Vuelo> findOneWithToOneRelationships(@Param("id") Long id);

    //  Métrica 2 - Query JPA para conocer los vuelos en los que ha participado un Piloto.
    Page<Vuelo> findByPiloto_Dni(String dni, Pageable pageable);

    //  Métrica 3 - Query JPA para conocer el número total de vuelos de un tripulante
    long countByTripulacions_Dni(String dni);
}
