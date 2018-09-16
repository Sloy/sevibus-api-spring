package com.sloydev.sevibus.api.view.healthcheck;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.ArrivalsEnvelope;
import com.sloydev.sevibus.api.data.internal.apptussamjson.AppTussamJsonApi;
import com.sloydev.sevibus.api.data.internal.apptussamjson.EstadoTarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

@RestController
public class AppTussamController {

    @Autowired
    AppTussamApi api;

    @Autowired
    AppTussamJsonApi jsonApi;

    @RequestMapping("/apptussam/arrival/{parada}")
    public ArrivalsEnvelope arrival(@PathVariable(value = "parada") String parada) {
        try {
            return api.getArrival(parada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/apptussam/card/{number}")
    public ResponseEntity<Object> card(@PathVariable(value = "number") Long number) {
        try {
            Response<EstadoTarjeta> response = jsonApi.estadoTajeta(number)
                    .execute();
            if (response.isSuccessful()) {
                return new ResponseEntity<>(response.body(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.errorBody().string(), HttpStatus.valueOf(response.code()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
