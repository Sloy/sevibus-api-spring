package com.sloydev.sevibus.api.domain;


public interface ArrivalTimesRepository {

    ArrivalTimes getArrivals(Integer busStopNumber, String lineName);
}
