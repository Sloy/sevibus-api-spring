package com.sloydev.sevibus.api.domain.stats;

import com.google.common.base.Objects;
import com.google.firebase.database.Exclude;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ArrivalRequestStat {

    private static final ZoneId ZONE_ID_SPAIN = ZoneId.of("Europe/Madrid");

    private final String parada;
    private final String userId;
    private Instant timestamp;

    public ArrivalRequestStat(String parada, String userId, Instant timestamp) {
        this.parada = parada;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getParada() {
        return parada;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimestamp() {
        return timestamp
                .toEpochMilli();
    }

    @Exclude
    public Instant getInstant() {
        return timestamp;
    }

    @Exclude
    public ZonedDateTime getZonedDateTime() {
        return timestamp
                .atZone(ZONE_ID_SPAIN);
    }

    public String getReadableTimestamp() {
        return getZonedDateTime()
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("parada", parada)
                .add("userId", userId)
                .add("timestamp", timestamp)
                .toString();
    }
}
