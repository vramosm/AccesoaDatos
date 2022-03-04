package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvionMyService {

    @Autowired
    AvionRepository avionRepository;

    public Optional<Avion> getAvionOlder() {
        return avionRepository.findFirstByOrderByEdadDesc();
    }
}
