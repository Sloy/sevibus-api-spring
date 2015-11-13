package com.sloydev.sevibus.api.data;

import com.fewlaps.quitnowcache.QNCache;
import com.sloydev.sevibus.api.RepoFactory;
import com.sloydev.sevibus.api.domain.ArrivalTimes;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CachedArrivalRepository implements ArrivalTimesRepository {

    private static final int CACHE_TTL = 10 * 1000;
    public static final String SOURCE_PREFIX = "cached_";

    private final ArrivalTimesRepository real = RepoFactory.getArrivalTimesRepository();
    private final QNCache cache;

    @Autowired
    public CachedArrivalRepository(QNCache cache) {
        this.cache = cache;
    }

    @Override
    public ArrivalTimes getArrivals(Integer busStopNumber, String lineName) {
        String key = busStopNumber + lineName;
        ArrivalTimes cached = cache.get(key);
        if (cached != null) {
            return cached;
        } else {
            ArrivalTimes updated = real.getArrivals(busStopNumber, lineName);
            updated.setDataSource(SOURCE_PREFIX + updated.getDataSource());
            cache.set(key, updated, CACHE_TTL);
            return updated;
        }
    }
}
