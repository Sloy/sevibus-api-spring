package com.sloydev.sevibus.api.view.arrivals;

import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimes;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesRepository;
import com.sloydev.sevibus.api.domain.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LlegadaController {


    @Autowired
    @Qualifier("cached")
    private ArrivalTimesRepository arrivalTimesRepository;

    @Autowired
    private StatsService statsService;

    @RequestMapping("/llegada/{parada}/{linea}")
    public ArrivalTimes llegada(@PathVariable(value = "linea") String linea,
                                @PathVariable(value = "parada") Integer parada,
                                @RequestHeader(value = "userId", required = false) String userId) {
        statsService.createArrivalRequestStat(parada, userId);
        return arrivalTimesRepository.getArrival(parada, linea);
    }

    @RequestMapping("/llegada/{parada}/")
    public List<ArrivalTimes> llegadas(@RequestParam(value = "lineas") List<String> lineas,
                                       @PathVariable(value = "parada") Integer parada,
                                       @RequestHeader(value = "userId", required = false) String userId) {
        statsService.createArrivalRequestStat(parada, userId);
        return arrivalTimesRepository.getArrivals(parada, lineas);
    }
}
