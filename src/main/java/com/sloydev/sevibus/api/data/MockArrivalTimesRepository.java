package com.sloydev.sevibus.api.data;


import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;

import java.util.Random;


public class MockArrivalTimesRepository implements ArrivalTimesRepository {

    private static final float ERROR_PERCENTAGE = 0f;
//    private static final float ERROR_PERCENTAGE = 0.15f;
    private final Random random;

    public MockArrivalTimesRepository() {
        random = new Random();
    }

    public ArrivalTimes getArrivals(Integer busStopNumber, String lineName) {
        try {
            Thread.sleep((long) (random.nextFloat() * 800.0f + 200));
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
        return arrivals;
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
        busArrival.setTimeInMinutes(random.nextInt(60)+10);
        return busArrival;
    }
}
