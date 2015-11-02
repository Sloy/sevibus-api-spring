package com.sloydev.sevibus.api.domain;


import com.sloydev.sevibus.api.domain.ArrivalTimes;

public interface ArrivalTimesRepository {

    ArrivalTimes getArrivals(Integer busStopNumber, String lineName);
}
