package com.sloydev.sevibus.api.domain.stats;

import com.google.common.base.Objects;

import java.util.Date;

public class ArrivalRequestStat {

    private final String parada;
    private final String userId;
    private final long timestamp;
    private final String readableTimestamp;

    public ArrivalRequestStat(String parada, String userId, long timestamp) {
        this.parada = parada;
        this.userId = userId;
        this.timestamp = timestamp;
        this.readableTimestamp = new Date(timestamp).toString();
    }

    public String getParada() {
        return parada;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getReadableTimestamp() {
        return readableTimestamp;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("parada", parada)
                .add("userId", userId)
                .add("timestamp", timestamp)
                .add("readableTimestamp", readableTimestamp)
                .toString();
    }
}
