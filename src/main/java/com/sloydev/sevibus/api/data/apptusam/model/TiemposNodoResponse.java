package com.sloydev.sevibus.api.data.apptusam.model;

import org.simpleframework.xml.Element;

public class TiemposNodoResponse {

    @Element(name = "tiempoNodo")
    public TiempoNodo tiempoNodo;

    @Override
    public String toString() {
        return "TiemposNodoResponse{" +
          "tiempoNodo=" + tiempoNodo +
          '}';
    }
}
