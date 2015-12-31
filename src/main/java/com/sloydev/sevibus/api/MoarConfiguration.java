package com.sloydev.sevibus.api;

import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import com.sloydev.sevibus.api.data.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.apptusam.AppTussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.mock.MockArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalsSaxHandler;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@Configuration
public class MoarConfiguration {

    private static final boolean logRequests = false;

    @Bean
    public QNCache cacheManager() {
        return new QNCacheBuilder().createQNCache();
    }

    @Bean
    public SAXParser provideSaxParser() {
        try {
            return SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        if (logRequests) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(logging);
        }
        return client;
    }
}
