package com.sloydev.sevibus.api.view.card;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.card.model.CardEnvelope;
import com.sloydev.sevibus.api.data.internal.apptusam.card.model.State;
import com.sloydev.sevibus.api.domain.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LegacyCardController {

    private final AppTussamApi appTussamApi;
    private final StatsService statsService;

    @Autowired
    public LegacyCardController(AppTussamApi appTussamApi, StatsService statsService) {
        this.appTussamApi = appTussamApi;
        this.statsService = statsService;
    }

    @RequestMapping("/legacy/card/{number}")
    public LegacyCardViewModel getCard(
            @PathVariable(value = "number") Long number,
            @RequestHeader(value = "userId", required = false) String userId) {
        try {
            LegacyCardViewModel responseViewModel = map(appTussamApi.getCard(number));
            statsService.createLegacyCardStat(responseViewModel, userId);
            return responseViewModel;
        } catch (Exception error) {
            statsService.createLegacyCardStat(number, error, userId);
            throw new RuntimeException(error);
        }
    }

    private static LegacyCardViewModel map(CardEnvelope cardEnvelope) {
        State apiModel = cardEnvelope.body.cardStateResponse.state;
        if (apiModel.resultCode != 0) {
            throw new IllegalStateException("Result code not successful");
        }
        return LegacyCardViewModel.create()
                .number(apiModel.chipNumber)
                .type(compatType(apiModel))
                .expirationDate(apiModel.expiryDate)
                .lastOperationDate(apiModel.lastOpDate)
                .credit(apiModel.moneyCredit / 1000d)
                .build();
    }

    private static String compatType(State apiModel) {
        return apiModel.passName
                .replaceAll("Bonobús saldo CT", "saldo con transbordo")
                .replaceAll("Bonobús saldo ST", "saldo sin transbordo")
                ;
    }
}
