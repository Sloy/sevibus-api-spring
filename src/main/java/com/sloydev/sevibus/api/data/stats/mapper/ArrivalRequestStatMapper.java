package com.sloydev.sevibus.api.data.stats.mapper;

import com.google.firebase.database.DataSnapshot;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStat;

import java.time.Instant;
import java.util.HashMap;

public class ArrivalRequestStatMapper {

    public static ArrivalRequestStat map(DataSnapshot snapshot) {
        HashMap<String, Object> statMap = (HashMap<String, Object>) snapshot.getValue();

        String parada = statMap.get("parada").toString();
        String userId = statMap.get("userId").toString();
        Long timestampLong = ((Long) statMap.get("timestamp"));
        Instant timestamp = Instant.ofEpochMilli(timestampLong);

        return new ArrivalRequestStat(parada, userId, timestamp);
    }
}
