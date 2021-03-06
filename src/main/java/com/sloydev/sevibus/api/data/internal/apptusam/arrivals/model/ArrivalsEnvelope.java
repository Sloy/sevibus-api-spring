package com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Envelope")
public class ArrivalsEnvelope {

    @Element(name="Body")
    public Body body;

    @Override
    public String toString() {
        return "Envelope{" +
          "body=" + body +
          '}';
    }
}
