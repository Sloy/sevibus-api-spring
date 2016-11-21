package com.sloydev.sevibus.api.data.internal.apptusam.card.model;

import org.simpleframework.xml.Element;

public class State {

    @Element(name="chipNumber")
    public Long chipNumber;

    @Element(name="passCode")
    public Integer passCode;

    @Element(name="passName", required = false)
    public String passName;

    @Element(name="lastOpDate", required = false)
    public String lastOpDate;

    @Element(name="expiryDate", required = false)
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
