package com.sloydev.sevibus.api;

import com.sloydev.sevibus.api.data.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.apptusam.AppTussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.mock.MockArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalsSaxHandler;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@Configuration
public class RepoFactory {

    private static final boolean mock = false;
    private static final boolean legacy = false;

    @Bean
    public static ArrivalTimesRepository getArrivalTimesRepository() {
        if (mock) {
            return new MockArrivalTimesRepository();
        } else if(legacy){
            return new TussamArrivalTimesRepository(provideSaxParser(), provideArrivalsSaxHandler());
        } else return new AppTussamArrivalTimesRepository(provideApi());
    }

    private static SAXParser provideSaxParser() {
        try {
            return SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(autowire = Autowire.BY_TYPE)
    private static AppTussamApi provideApi() {
        return new AppTussamApi();
    }

    private static TussamArrivalsSaxHandler provideArrivalsSaxHandler() {
        return new TussamArrivalsSaxHandler();
    }

}
