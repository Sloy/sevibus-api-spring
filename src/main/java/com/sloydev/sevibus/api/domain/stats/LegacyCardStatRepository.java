package com.sloydev.sevibus.api.domain.stats;

public interface LegacyCardStatRepository {

    void put(LegacyCardStat legacyCardStat);
    void put(CardStat cardStat);
}
