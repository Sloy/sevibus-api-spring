package com.sloydev.sevibus.api.data.apptusam;


import com.sloydev.sevibus.api.data.apptusam.model.Envelope;
import com.sloydev.sevibus.api.data.apptusam.model.Estimacion;
import com.sloydev.sevibus.api.data.apptusam.model.TiempoLinea;
import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesException;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AppTussamArrivalTimesRepository implements ArrivalTimesRepository {

    private final AppTussamApi appTussamApi;

    public AppTussamArrivalTimesRepository(AppTussamApi appTussamApi) {
        this.appTussamApi = appTussamApi;
    }

    @Override
    public ArrivalTimes getArrival(Integer busStopNumber, String lineName) {
        return null;
    }

    @Override
    public List<ArrivalTimes> getArrivals(Integer busStopNumber, List<String> lines) {
        try {
            Envelope envelope = appTussamApi.get(busStopNumber.toString());
            List<TiempoLinea> tiempos = envelope.body.tiemposNodoResponse.tiempoNodo.tiempoLineas;
            return tiempos.stream()
              .map(this::arrivalFromTiempo)
              .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ArrivalTimesException(e, busStopNumber, "all");
        }
    }

    private ArrivalTimes arrivalFromTiempo(TiempoLinea tiempoLinea) {
        ArrivalTimes arrivalTimes = new ArrivalTimes();
        arrivalTimes.setDataSource("apptussam");
        arrivalTimes.setBusLineName(tiempoLinea.label);
        //TODO usar número de parada en la request
        arrivalTimes.setBusStopNumber(99);
        arrivalTimes.setNextBus(busFromEstimacion(tiempoLinea.estimacion1));
        arrivalTimes.setSecondBus(busFromEstimacion(tiempoLinea.estimacion2));
        return arrivalTimes;
    }

    private ArrivalTimes.BusArrival busFromEstimacion(Estimacion estimacion) {
        ArrivalTimes.BusArrival arrival = new ArrivalTimes.BusArrival(statusForTiempo(estimacion));
        arrival.setTimeInMinutes(estimacion.minutos);
        arrival.setDistanceInMeters(estimacion.metros);
        return arrival;
    }

    private ArrivalTimes.Status statusForTiempo(Estimacion estimacion) {
        if (estimacion == null) {
            return ArrivalTimes.Status.NOT_AVAILABLE;
        } else {
            if (estimacion.minutos > 0) {
                return ArrivalTimes.Status.ESTIMATE;
            } else if (estimacion.minutos == 0) {
                return ArrivalTimes.Status.IMMINENT;
            } else if (estimacion.minutos == -30) {
                return ArrivalTimes.Status.BEYOND_HALF_HOUR;
            } else {
                return ArrivalTimes.Status.NO_ESTIMATION;
            }
        }
    }
}
