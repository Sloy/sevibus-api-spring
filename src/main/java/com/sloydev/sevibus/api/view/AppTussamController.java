package com.sloydev.sevibus.api.view;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppTussamController {


    @Autowired
    AppTussamApi api;

    @RequestMapping("/apptussam/{parada}")
    public Envelope apptussam(@PathVariable(value = "parada") String parada) {
        try {
            return api.get(parada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
