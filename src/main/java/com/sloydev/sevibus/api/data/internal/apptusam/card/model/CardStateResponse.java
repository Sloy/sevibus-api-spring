package com.sloydev.sevibus.api.data.internal.apptusam.card.model;

import org.simpleframework.xml.Element;

public class CardStateResponse {

    @Element(name = "scState")
    public State state;

    @Override
    public String toString() {
        return "CardStateResponse{" +
                "state=" + state +
                '}';
    }
}
