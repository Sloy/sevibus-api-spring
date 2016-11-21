package com.sloydev.sevibus.api.data.internal.apptusam.card.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class State {

    @Element(name="chipNumber")
    public Integer chipNumber;

    @Element(name="passCode")
    public Integer passCode;

    @Element(name="passName")
    public String passName;

    @Element(name="lastOpDate")
    public String lastOpDate;

    @Element(name="expiryDate")
    public String expiryDate;

    @Element(name="moneyCredit")
    public Integer moneyCredit;

    @Element(name="tripsCredit")
    public Integer tripsCredit;

    @Element(name="resultCode")
    public Integer resultCode;

    @Override
    public String toString() {
        return "State{" +
                "chipNumber=" + chipNumber +
                ", passCode=" + passCode +
                ", passName='" + passName + '\'' +
                ", lastOpDate='" + lastOpDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", moneyCredit=" + moneyCredit +
                ", tripsCredit=" + tripsCredit +
                ", resultCode=" + resultCode +
                '}';
    }
}
