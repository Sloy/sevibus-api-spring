package com.sloydev.sevibus.api.domain.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static java.util.Optional.ofNullable;


@Component
public class StatsService {

    private final ArrivalRequestStatRepository arrivalRequestStatRepository;

    @Autowired
    public StatsService(ArrivalRequestStatRepository arrivalRequestStatRepository) {
        this.arrivalRequestStatRepository = arrivalRequestStatRepository;
    }

    @Async
    public void createArrivalRequestStat(Integer parada, @Nullable String userId) {
        ArrivalRequestStat stat = new ArrivalRequestStat(String.valueOf(parada),
                ofNullable(userId).orElse("anonymous"),
                System.currentTimeMillis());
        arrivalRequestStatRepository.putStat(stat);
    }
}
