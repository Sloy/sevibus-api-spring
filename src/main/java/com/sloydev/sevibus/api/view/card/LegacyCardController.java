package com.sloydev.sevibus.api.view.card;

import com.sloydev.sevibus.api.data.internal.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.internal.apptusam.card.model.CardEnvelope;
import com.sloydev.sevibus.api.data.internal.apptusam.card.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LegacyCardController {

    private final AppTussamApi appTussamApi;

    @Autowired
    public LegacyCardController(AppTussamApi appTussamApi) {
        this.appTussamApi = appTussamApi;
    }

    @RequestMapping("/legacy/card/{number}")
    public LegacyCardViewModel getCard(@PathVariable(value = "number") Long number) {
        try {
            return map(appTussamApi.getCard(number));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static LegacyCardViewModel map(CardEnvelope cardEnvelope) {
        State apiModel = cardEnvelope.body.cardStateResponse.state;
        return LegacyCardViewModel.create()
                .number(apiModel.chipNumber)
                .type(apiModel.passName)
                .expirationDate(apiModel.expiryDate)
                .lastOperationDate(apiModel.lastOpDate)
                .credit(apiModel.moneyCredit / 1000d)
                .build();
    }
}
