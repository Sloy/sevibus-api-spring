package com.sloydev.sevibus.api.data.arrivals;

import com.fewlaps.quitnowcache.QNCache;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimes;
import com.sloydev.sevibus.api.domain.arrivals.ArrivalTimesRepository;

import java.util.List;

public class CachedArrivalRepository implements ArrivalTimesRepository {

    private static final int CACHE_TTL = 10 * 1000;
    public static final String SOURCE_PREFIX = "cached_";

    private final ArrivalTimesRepository real;
    private final QNCache cache;

    public CachedArrivalRepository(ArrivalTimesRepository real, QNCache cache) {
        this.real = real;
        this.cache = cache;
    }

    @Override
    public ArrivalTimes getArrival(Integer busStopNumber, String lineName) {
        String key = busStopNumber + lineName;
        ArrivalTimes cached = cache.get(key);
        if (cached != null) {
            markDatasourceAsCached(cached);
            return cached;
        } else {
            ArrivalTimes updated = real.getArrival(busStopNumber, lineName);
            cache.set(key, updated, CACHE_TTL);
            return updated;
        }
    }

    @Override
    public List<ArrivalTimes> getArrivals(Integer busStopNumber, List<String> lines) {
        String key = busStopNumber.toString();
        List<ArrivalTimes> cachedList = cache.get(key);
        if (cachedList != null) {
            cachedList.stream().forEach(this::markDatasourceAsCached);
            return cachedList;
        } else {
            List<ArrivalTimes> updated = real.getArrivals(busStopNumber, lines);
            cache.set(key, updated, CACHE_TTL);
            return updated;
        }
    }

    private void markDatasourceAsCached(ArrivalTimes cached) {
        if (!cached.getDataSource().startsWith(SOURCE_PREFIX)) {
            cached.setDataSource(SOURCE_PREFIX + cached.getDataSource());
        }
    }
}
