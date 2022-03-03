package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aeropuerto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aeropuerto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long>, JpaSpecificationExecutor<Aeropuerto> {}
