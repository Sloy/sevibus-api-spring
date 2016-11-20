package com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model;

import org.simpleframework.xml.Element;

public class Body {

    @Element(name = "getTiemposNodoResponse")
    public TiemposNodoResponse tiemposNodoResponse;

    @Override
    public String toString() {
        return "Body{" +
          "tiemposNodoResponse=" + tiemposNodoResponse +
          '}';
    }
}
