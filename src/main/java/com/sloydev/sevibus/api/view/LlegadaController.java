package com.sloydev.sevibus.api.view;

import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LlegadaController {


    @Autowired
    private ArrivalTimesRepository arrivalTimesRepository;

    @RequestMapping("/llegada/{parada}/{linea}")
    public ArrivalTimes llegada(@PathVariable(value = "linea") String linea, @PathVariable(value = "parada") Integer parada) {
        return arrivalTimesRepository.getArrivals(parada, linea);
    }

    @RequestMapping("/llegada/{parada}/")
    public List<ArrivalTimes> llegadas(@RequestParam(value = "lineas") String[] lineas, @PathVariable(value = "parada") Integer parada) {
        List<ArrivalTimes> results = new ArrayList<>(lineas.length);
        for (String linea : lineas) {
            results.add(arrivalTimesRepository.getArrivals(parada, linea));
        }
        return results;
    }
}
