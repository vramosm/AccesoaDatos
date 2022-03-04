package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.domain.Vuelo;
import com.mycompany.myapp.service.AvionMyService;
import com.mycompany.myapp.service.VueloMyService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    VueloMyService vueloMyService;
    AvionMyService avionMyService;

    public MyController(VueloMyService vueloMyService, AvionMyService avionMyService) {
        this.vueloMyService = vueloMyService;
        this.avionMyService = avionMyService;
    }

    //Metrica 1
    @GetMapping(path = "/avion")
    public ResponseEntity<Optional<Avion>> getAvionOlder() {
        return ResponseEntity.ok(avionMyService.getAvionOlder());
    }

    //Metrica 2
    @GetMapping(path = "/vuelo/pilotos")
    public ResponseEntity<Page<Vuelo>> getPilotoDni(
        @RequestParam String dni,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        if (validarDni(dni)) return ResponseEntity.ok(vueloMyService.getPilotoDni(dni, pageable));
        return new ResponseEntity("Error en la busqueda", HttpStatus.BAD_REQUEST);
    }

    //Metrica 3
    @GetMapping(path = "/vuelo/tripulantes")
    public ResponseEntity<Long> tripulacionByDni(@RequestParam String dni) {
        if (validarDni(dni)) return ResponseEntity.ok(vueloMyService.tripulacionByDni(dni));
        return new ResponseEntity("Error en la busqueda", HttpStatus.BAD_REQUEST);
    }

    public boolean validarDni(String dni) {
        if (dni.matches(("[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]"))) return true;
        return false;
    }
}
