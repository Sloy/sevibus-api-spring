package com.sloydev.sevibus.api.view;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.ArrivalsEnvelope;
import com.sloydev.sevibus.api.data.internal.apptusam.card.model.CardEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppTussamController {


    @Autowired
    AppTussamApi api;

    @RequestMapping("/apptussam/arrival/{parada}")
    public ArrivalsEnvelope apptussam(@PathVariable(value = "parada") String parada) {
        try {
            return api.getArrival(parada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/apptussam/card/{number}")
    public CardEnvelope apptussam(@PathVariable(value = "number") Long number) {
        try {
            return api.getCard(number);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
