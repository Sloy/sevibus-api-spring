package com.sloydev.sevibus.api.data.apptusam.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tiempoLinea")
public class TiempoLinea {

    @Element
    public String label;

    @Element
    public int macro;

    @Element
    public Estimacion estimacion1;

    @Element
    public Estimacion estimacion2;

    @Override
    public String toString() {
        return "TiempoLinea{" +
          "label='" + label + '\'' +
          ", macro=" + macro +
          ", estimacion1=" + estimacion1 +
          ", estimacion2=" + estimacion2 +
          '}';
    }
}
