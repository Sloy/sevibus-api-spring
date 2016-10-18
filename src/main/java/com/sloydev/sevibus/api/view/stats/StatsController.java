package com.sloydev.sevibus.api.view.stats;

import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStat;
import com.sloydev.sevibus.api.domain.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;


    @RequestMapping("/stats/arrivals/{month}/{day}/{hour}")
    public List<ArrivalRequestStat> allArrivalStats(
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "day") Integer day,
            @PathVariable(value = "hour") Integer hour) {
        return statsService.getByHour(month, day, hour);
    }

    @RequestMapping("/stats/arrivals/{month}/{day}/")
    public List<ArrivalRequestStat> allArrivalStats(
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "day") Integer day) {
        return statsService.getByDay(month, day);
    }

}
