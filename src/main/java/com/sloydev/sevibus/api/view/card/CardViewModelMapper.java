package com.sloydev.sevibus.api.view.card;

import com.sloydev.sevibus.api.data.internal.apptussamjson.CardStatusTussamModel;

public class CardViewModelMapper {

    public static CardViewModel map(CardStatusTussamModel apiModel) {
        return CardViewModel.newCardViewModel()
                .serialNumber(apiModel.getNumeroSerie())
                .typeCode(apiModel.getCodigoTitulo())
                .typeName(apiModel.getNombreTitulo())
                .lastOperationDate(apiModel.getUltimaOperacion())
                .expires(hasExpirationDate(apiModel.getCaducidad()))
                .expirationDate(apiModel.getCaducidad())
                .result(apiModel.getResultado())
                .remainingMoney(formatMoney(apiModel.getSaldoMonedero()))
                .remainingTrips(apiModel.getSaldoViajes())
                .build();
    }

    private static String formatMoney(Long moneyCode) {
        Double moneyDecimal = moneyCode / 1000.0;
        return String.format("%.2f €", moneyDecimal);
    }

    private static boolean hasExpirationDate(String expirationDate) {
        return expirationDate != null && !expirationDate.equals("Tarjeta sin caducidad");
    }
}
