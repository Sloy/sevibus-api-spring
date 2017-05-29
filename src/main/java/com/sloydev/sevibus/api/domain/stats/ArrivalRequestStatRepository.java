package com.sloydev.sevibus.api.domain.stats;

import java.util.List;

public interface ArrivalRequestStatRepository {

    void putStat(ArrivalRequestStat arrivalRequestStat);

    List<ArrivalRequestStat> getByMonthAndDayAndHour(int month, int day, int hour);

    List<ArrivalRequestStat> getByMonthAndDay(int month, int day);

    void removeByMonth(Integer month);
}
