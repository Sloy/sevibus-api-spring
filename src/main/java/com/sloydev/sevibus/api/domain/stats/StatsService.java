package com.sloydev.sevibus.api.domain.stats;

import com.sloydev.sevibus.api.view.card.LegacyCardViewModel;
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
    private final LegacyCardStatRepository legacyCardStatRepository;

    @Autowired
    public StatsService(ArrivalRequestStatRepository arrivalRequestStatRepository, LegacyCardStatRepository legacyCardStatRepository) {
        this.arrivalRequestStatRepository = arrivalRequestStatRepository;
        this.legacyCardStatRepository = legacyCardStatRepository;
    }

    @Async
    public void createArrivalRequestStat(Integer parada, @Nullable String userId) {
        ArrivalRequestStat stat = new ArrivalRequestStat(String.valueOf(parada),
                ofNullable(userId).orElse("anonymous"),
                Instant.now());

        arrivalRequestStatRepository.putStat(stat);
    }

    @Async
    public void createLegacyCardStat(LegacyCardViewModel legacyCardViewModel, String userId) {
        LegacyCardStat legacyCardStat = LegacyCardStat.create()
                .number(legacyCardViewModel.number)
                .cardInfoResult(legacyCardViewModel)
                .updatedAt(Instant.now())
                .userId(ofNullable(userId).orElse("anonymous"))
                .build();
        legacyCardStatRepository.put(legacyCardStat);
    }

    @Async
    public void createLegacyCardStat(Long number, Exception error, String userId) {
        LegacyCardStat legacyCardStat = LegacyCardStat.create()
                .number(number)
                .error(error)
                .updatedAt(Instant.now())
                .userId(ofNullable(userId).orElse("anonymous"))
                .build();
        legacyCardStatRepository.put(legacyCardStat);
    }

    public List<ArrivalRequestStat> getByHour(Integer month, Integer day, Integer hour) {
        return arrivalRequestStatRepository.getByMonthAndDayAndHour(month, day, hour);
    }

    public List<ArrivalRequestStat> getByDay(Integer month, Integer day) {
        return arrivalRequestStatRepository.getByMonthAndDay(month, day);
    }
}
