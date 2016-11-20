package com.sloydev.sevibus.api.data.arrivals;


import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimes;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MockArrivalTimesRepository implements ArrivalTimesRepository {

    private static final String SOURCE_NAME = "mock";
    private static final float ERROR_PERCENTAGE = 0f;
    // private static final float ERROR_PERCENTAGE = 0.15f;
    private final Random random;

    public MockArrivalTimesRepository() {
        random = new Random();
    }

    @Override
    public ArrivalTimes getArrival(Integer busStopNumber, String lineName) {
        try {
            Thread.sleep((long) (random.nextFloat() * 800.0f + 1200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextFloat() <= ERROR_PERCENTAGE) {
            throw new RuntimeException();
        }

        if (lineName.startsWith("A")) {
            return nightArrival(busStopNumber, lineName);
        }

        ArrivalTimes arrivals = new ArrivalTimes();
        arrivals.setBusStopNumber(busStopNumber);
        arrivals.setBusLineName(lineName);
        arrivals.setNextBus(getNextBus());
        arrivals.setSecondBus(getSecondBus());
        arrivals.setDataSource(SOURCE_NAME);
        return arrivals;
    }

    @Override
    public List<ArrivalTimes> getArrivals(Integer busStopNumber, List<String> lines) {
        return lines.stream()
          .map(linea -> getArrival(busStopNumber, linea))
          .collect(Collectors.toList());
    }

    private ArrivalTimes nightArrival(Integer busStopNumber, String lineName) {
        ArrivalTimes arrivals = new ArrivalTimes();
        arrivals.setBusLineName(lineName);
        arrivals.setBusStopNumber(busStopNumber);
        return arrivals;
    }

    private ArrivalTimes.BusArrival getNextBus() {
        ArrivalTimes.BusArrival busArrival = new ArrivalTimes.BusArrival(ArrivalTimes.Status.ESTIMATE);
        busArrival.setDistanceInMeters(100);
        busArrival.setTimeInMinutes(random.nextInt(15));
        return busArrival;
    }

    private ArrivalTimes.BusArrival getSecondBus() {
        ArrivalTimes.BusArrival busArrival = new ArrivalTimes.BusArrival(ArrivalTimes.Status.ESTIMATE);
        busArrival.setDistanceInMeters(1428);
        busArrival.setTimeInMinutes(random.nextInt(60) + 10);
        return busArrival;
    }
}
