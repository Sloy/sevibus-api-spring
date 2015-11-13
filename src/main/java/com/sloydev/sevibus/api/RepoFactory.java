package com.sloydev.sevibus.api;

import com.sloydev.sevibus.api.data.mock.MockArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalsSaxHandler;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RepoFactory {

    private static final boolean mock = false;

    public static ArrivalTimesRepository getArrivalTimesRepository() {
        if (mock) {
            return new MockArrivalTimesRepository();
        } else {
            return new TussamArrivalTimesRepository(provideSaxParser(), provideArrivalsSaxHandler());
        }
    }

    private static SAXParser provideSaxParser() {
        try {
            return SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TussamArrivalsSaxHandler provideArrivalsSaxHandler() {
        return new TussamArrivalsSaxHandler();
    }

}
