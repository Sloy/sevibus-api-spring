package com.sloydev.sevibus.api.view.card;

import com.sloydev.sevibus.api.data.internal.apptussamjson.AppTussamJsonApi;
import com.sloydev.sevibus.api.data.internal.apptussamjson.CardStatusTussamModel;
import com.sloydev.sevibus.api.domain.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

@RestController
public class CardController {

    private final AppTussamJsonApi api;
    private final StatsService statsService;

    @Autowired
    public CardController(AppTussamJsonApi api, StatsService statsService) {
        this.api = api;
        this.statsService = statsService;
    }

    @RequestMapping("/card/{number}")
    public CardStatusTussamModel getCard(
            @PathVariable(value = "number") Long number,
            @RequestHeader(value = "userId", required = false) String userId) {
        try {
            Response<CardStatusTussamModel> response = api.estadoTajeta(number).execute();
            if (response.isSuccessful()) {
                CardStatusTussamModel cardModel = response.body();
                statsService.createCardStat(cardModel, userId);
                return cardModel;
            } else {
                String message = String.format("AppTussam API response error! HttpCode=%d; Body=%s",
                        response.code(),
                        response.errorBody().string());
                throw new RuntimeException(message);
            }
        } catch (Exception error) {
            statsService.createLegacyCardStat(number, error, userId);
            throw new RuntimeException(error);
        }
    }
}
