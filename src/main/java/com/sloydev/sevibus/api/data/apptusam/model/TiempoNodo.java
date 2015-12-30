package com.sloydev.sevibus.api.data.apptusam.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class TiempoNodo {

    @ElementList(name = "lineasCoincidentes")
    public List<TiempoLinea> tiempoLineas;

    @Element(name="codigo")
    public Integer codigo;

    @Override
    public String toString() {
        return "TiempoNodo{" +
          "tiempoLineas=" + tiempoLineas +
          '}';
    }
}
