package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Vuelo;
import com.mycompany.myapp.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VueloMyService {

    @Autowired
    VueloRepository vueloRepository;

    public Page<Vuelo> getPilotoDni(String dni, Pageable pageable) {
        return vueloRepository.findByPiloto_Dni(dni, pageable);
    }

    public Long tripulacionByDni(String dni) {
        return vueloRepository.countByTripulacions_Dni(dni);
    }
}
