package com.sloydev.sevibus.api.data.internal.apptusam.card.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Envelope")
public class CardEnvelope {

    @Element(name="Body")
    public Body body;

    @Override
    public String toString() {
        return "Envelope{" +
          "body=" + body +
          '}';
    }
}
