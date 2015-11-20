package com.sloydev.sevibus.api.domain;


import java.util.List;

public interface ArrivalTimesRepository {

    ArrivalTimes getArrival(Integer busStopNumber, String lineName);

    List<ArrivalTimes> getArrivals(Integer busStopNumber, List<String> lines);
}
