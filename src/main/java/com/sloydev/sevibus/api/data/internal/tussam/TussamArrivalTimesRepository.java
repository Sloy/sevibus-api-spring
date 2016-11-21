package com.sloydev.sevibus.api.data.internal.tussam;


import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimes;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesException;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesRepository;
import com.squareup.okhttp.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TussamArrivalTimesRepository implements ArrivalTimesRepository {

    private static final String URL_SOAP_DINAMICA = "http://www.infobustussam.com:9001/services/dinamica.asmx";
    private static final String BODY_SOAP_TIEMPOS = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetPasoParada xmlns=\"http://tempuri.org/\"><linea>%1s</linea><parada>%2s</parada><status>1</status></GetPasoParada></soap:Body></soap:Envelope>"; // 2.parada
    private static final String SOURCE_NAME = "tussam";

    private final SAXParser saxParser;
    private final TussamArrivalsSaxHandler tussamSaxHandler;
    private final OkHttpClient client;

    public TussamArrivalTimesRepository(SAXParser saxParser, TussamArrivalsSaxHandler tussamSaxHandler, OkHttpClient client) {
        this.saxParser = saxParser;
        this.tussamSaxHandler = tussamSaxHandler;
        this.client = client;
    }

    @Override public ArrivalTimes getArrival(Integer busStopNumber, String lineName) {
        try {
            InputStream arrivalsInputStream = getArrivalsInputStream(lineName, String.valueOf(busStopNumber));
            ArrivalTimes arrivals = createEmptyArrival(busStopNumber, lineName);
            populateArrivalTimes(arrivals, arrivalsInputStream);
            arrivals.setDataSource(SOURCE_NAME);
            return arrivals;
        } catch (Exception e) {
            throw new ArrivalTimesException(e, busStopNumber, lineName);
        }
    }

    @Override
    public List<ArrivalTimes> getArrivals(Integer busStopNumber, List<String> lines) {
        return lines.stream()
          .map(linea -> getArrival(busStopNumber, linea))
          .collect(Collectors.toList());
    }

    private void populateArrivalTimes(ArrivalTimes arrivals, InputStream arrivalsInputStream) throws ParserConfigurationException, SAXException, IOException {
        saxParser.parse(arrivalsInputStream, tussamSaxHandler);

        TussamArrivalsSaxHandler.TwoBuses buses = tussamSaxHandler.getResult();
        tussamSaxHandler.clear();

        arrivals.setNextBus(busArrivalFromBus(buses.bus1));
        arrivals.setSecondBus(busArrivalFromBus(buses.bus2));
    }

    private ArrivalTimes.BusArrival busArrivalFromBus(TussamArrivalsSaxHandler.Bus bus) {
        ArrivalTimes.Status status = statusForBus(bus);
        ArrivalTimes.BusArrival busArrival = new ArrivalTimes.BusArrival(status);
        if (bus != null) {
            busArrival.setTimeInMinutes(bus.time);
            busArrival.setDistanceInMeters(bus.distance);
        }
        return busArrival;
    }

    private ArrivalTimes.Status statusForBus(TussamArrivalsSaxHandler.Bus bus) {
        if (bus == null) {
            return ArrivalTimes.Status.NOT_AVAILABLE;
        } else {
            if (bus.time > 0) {
                return ArrivalTimes.Status.ESTIMATE;
            } else if (bus.time == 0) {
                return ArrivalTimes.Status.IMMINENT;
            } else {
                return ArrivalTimes.Status.NO_ESTIMATION;
            }
        }
    }

    private ArrivalTimes createEmptyArrival(Integer busStopNumber, String lineName) {
        ArrivalTimes arrivalTimes = new ArrivalTimes();
        arrivalTimes.setBusStopNumber(busStopNumber);
        arrivalTimes.setBusLineName(lineName);
        return arrivalTimes;
    }

    private InputStream getArrivalsInputStream(String lineName, String stopNumber) throws IOException {
        MediaType mediaType = MediaType.parse("text/xml");
        RequestBody body = RequestBody.create(mediaType, String.format(BODY_SOAP_TIEMPOS, lineName, stopNumber));
        Request request = new Request.Builder()
                .url(URL_SOAP_DINAMICA)
                .post(body)
                .addHeader("content-type", "text/xml")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }
}
