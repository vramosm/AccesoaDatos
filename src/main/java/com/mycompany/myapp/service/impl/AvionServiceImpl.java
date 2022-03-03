package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;
import com.mycompany.myapp.service.AvionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Avion}.
 */
@Service
@Transactional
public class AvionServiceImpl implements AvionService {

    private final Logger log = LoggerFactory.getLogger(AvionServiceImpl.class);

    private final AvionRepository avionRepository;

    public AvionServiceImpl(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    @Override
    public Avion save(Avion avion) {
        log.debug("Request to save Avion : {}", avion);
        return avionRepository.save(avion);
    }

    @Override
    public Optional<Avion> partialUpdate(Avion avion) {
        log.debug("Request to partially update Avion : {}", avion);

        return avionRepository
            .findById(avion.getId())
            .map(existingAvion -> {
                if (avion.getTipo() != null) {
                    existingAvion.setTipo(avion.getTipo());
                }
                if (avion.getMatricula() != null) {
                    existingAvion.setMatricula(avion.getMatricula());
                }
                if (avion.getNumeroSerie() != null) {
                    existingAvion.setNumeroSerie(avion.getNumeroSerie());
                }
                if (avion.getEdad() != null) {
                    existingAvion.setEdad(avion.getEdad());
                }

                return existingAvion;
            })
            .map(avionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Avion> findAll(Pageable pageable) {
        log.debug("Request to get all Avions");
        return avionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Avion> findOne(Long id) {
        log.debug("Request to get Avion : {}", id);
        return avionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avion : {}", id);
        avionRepository.deleteById(id);
    }
}
