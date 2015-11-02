package com.sloydev.sevibus.api.view;

import com.sloydev.sevibus.api.RepoFactory;
import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LlegadaController {


    private final ArrivalTimesRepository arrivalTimesRepository = RepoFactory.getArrivalTimesRepository();

    @RequestMapping("/llegada")
    public ArrivalTimes llegada(@RequestParam(value = "linea") String linea, @RequestParam(value = "parada") Integer parada) {
        return arrivalTimesRepository.getArrivals(parada, linea);
    }
}
