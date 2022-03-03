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
}
