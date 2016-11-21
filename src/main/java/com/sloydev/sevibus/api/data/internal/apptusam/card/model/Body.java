package com.sloydev.sevibus.api.data.internal.apptusam.card.model;

import org.simpleframework.xml.Element;

public class Body {

    @Element(name = "getCardStateResponse")
    public CardStateResponse cardStateResponse;

    @Override
    public String toString() {
        return "Body{" +
                "cardStateResponse=" + cardStateResponse +
                '}';
    }
}
