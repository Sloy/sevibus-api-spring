package com.sloydev.sevibus.api.data.arrivals;


import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.Envelope;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.Estimacion;
import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.TiempoLinea;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimes;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesException;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AppTussamArrivalTimesRepository implements ArrivalTimesRepository {

    private static final String SOURCE_NAME = "apptussam";

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
              .map(tiempoLinea -> arrivalFromTiempo(tiempoLinea, busStopNumber))
              .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ArrivalTimesException(e, busStopNumber, "all");
        }
    }

    private ArrivalTimes arrivalFromTiempo(TiempoLinea tiempoLinea, int numeroParada) {
        ArrivalTimes arrivalTimes = new ArrivalTimes();
        arrivalTimes.setDataSource(SOURCE_NAME);
        arrivalTimes.setBusLineName(tiempoLinea.label);
        arrivalTimes.setBusStopNumber(numeroParada);
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
