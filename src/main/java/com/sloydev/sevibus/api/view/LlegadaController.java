package com.sloydev.sevibus.api.view;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LlegadaController {


    @RequestMapping("/llegada/{parada}/{linea}")
    public ResponseEntity<String> llegada(@PathVariable(value = "linea") String linea, @PathVariable(value = "parada") Integer parada) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
