package com.sloydev.sevibus.api.domain.arrivals;


public class ArrivalTimesException extends RuntimeException{


    private final Integer busStopNumber;
    private final String lineName;

    public ArrivalTimesException(Throwable cause, Integer busStopNumber, String lineName) {
        super(cause);
        this.busStopNumber = busStopNumber;
        this.lineName = lineName;
    }

    public Integer getBusStopNumber() {
        return busStopNumber;
    }

    public String getLineName() {
        return lineName;
    }
}
