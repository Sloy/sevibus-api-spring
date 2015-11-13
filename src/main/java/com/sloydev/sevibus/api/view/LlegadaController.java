package com.sloydev.sevibus.api.view;

import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LlegadaController {


    @Autowired
    private ArrivalTimesRepository arrivalTimesRepository;

    @RequestMapping("/llegada/{parada}/{linea}")
    public ArrivalTimes llegada(@PathVariable(value = "linea") String linea, @PathVariable(value = "parada") Integer parada) {
        return arrivalTimesRepository.getArrivals(parada, linea);
    }
}
