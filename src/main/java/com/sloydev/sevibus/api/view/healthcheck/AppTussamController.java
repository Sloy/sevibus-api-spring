package com.sloydev.sevibus.api.view.healthcheck;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.ArrivalsEnvelope;
import com.sloydev.sevibus.api.data.internal.apptussamjson.AppTussamJsonApi;
import com.sloydev.sevibus.api.data.internal.apptussamjson.EstadoTarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class AppTussamController {

    @Autowired
    AppTussamApi api;

    @Autowired
    AppTussamJsonApi jsonApi;

    @RequestMapping("/apptussam/arrival/{parada}")
    public ArrivalsEnvelope apptussam(@PathVariable(value = "parada") String parada) {
        try {
            return api.getArrival(parada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/apptussam/card/{number}")
    public EstadoTarjeta apptussam(@PathVariable(value = "number") Long number) {
        try {
            return checkNotNull(jsonApi.estadoTajeta(number)
                            .execute()
                            .body(),
                    "Should not return null data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
