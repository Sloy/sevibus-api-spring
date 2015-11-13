package com.sloydev.sevibus.api.data.tussam;


import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesException;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TussamArrivalTimesRepository implements ArrivalTimesRepository {

    private static final String URL_SOAP_DINAMICA = "http://www.infobustussam.com:9001/services/dinamica.asmx";
    private static final String BODY_SOAP_TIEMPOS = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetPasoParada xmlns=\"http://tempuri.org/\"><linea>%1s</linea><parada>%2s</parada><status>1</status></GetPasoParada></soap:Body></soap:Envelope>"; // 2.parada
    private static final String SOURCE_NAME = "tussam";

    private final SAXParser saxParser;
    private final TussamArrivalsSaxHandler tussamSaxHandler;

    public TussamArrivalTimesRepository(SAXParser saxParser, TussamArrivalsSaxHandler tussamSaxHandler) {
        this.saxParser = saxParser;
        this.tussamSaxHandler = tussamSaxHandler;
    }

    @Override public ArrivalTimes getArrivals(Integer busStopNumber, String lineName) {
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
        //TODO mejor alternativa? OkHttp?
        URL url = new URL(URL_SOAP_DINAMICA);
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("POST");
        c.setReadTimeout(15 * 1000);
        c.setDoOutput(true);
        // c.setFixedLengthStreamingMode(contentLength)
        c.setUseCaches(false);
        c.setRequestProperty("Content-Type", "text/xml");
        c.connect();

        OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
        String data = String.format(BODY_SOAP_TIEMPOS, lineName, stopNumber);
        wr.write(data);
        wr.flush();

        return c.getInputStream();

    }

}
