package com.sloydev.sevibus.api.domain.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

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
                Instant.now());

        arrivalRequestStatRepository.putStat(stat);
    }

    public List<ArrivalRequestStat> getByHour(Integer month, Integer day, Integer hour) {
        return arrivalRequestStatRepository.getByMonthAndDayAndHour(month, day, hour);
    }

    public List<ArrivalRequestStat> getByDay(Integer month, Integer day) {
        return arrivalRequestStatRepository.getByMonthAndDay(month, day);
    }
}
