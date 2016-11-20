package com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model;

import org.simpleframework.xml.Element;

public class Estimacion {

    @Element
    public int minutos;

    @Element
    public int metros;

    @Override
    public String toString() {
        return "Estimacion{" +
          "minutos=" + minutos +
          ", metros=" + metros +
          '}';
    }
}
