package com.sloydev.sevibus.api.data;

import com.fewlaps.quitnowcache.QNCache;
import com.sloydev.sevibus.api.data.apptusam.AppTussamApi;
import com.sloydev.sevibus.api.data.apptusam.AppTussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.mock.MockArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalTimesRepository;
import com.sloydev.sevibus.api.data.tussam.TussamArrivalsSaxHandler;
import com.sloydev.sevibus.api.domain.ArrivalTimesRepository;
import com.squareup.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.SAXParser;

@Configuration
public class RepositoryConfiguration {


    @Bean(name = {"cached"})
    public ArrivalTimesRepository provideCachedRepository(@Qualifier("apptussam") ArrivalTimesRepository realRepo, QNCache qnCache) {
        return new CachedArrivalRepository(realRepo, qnCache);
    }

    @Bean(name = {"apptussam"})
    public ArrivalTimesRepository provideAppTussamRepository(AppTussamApi appTussamApi) {
        return new AppTussamArrivalTimesRepository(appTussamApi);
    }

    @Bean(name = {"legacy"})
    public ArrivalTimesRepository provideLegacyTussamRepository(SAXParser saxParser, TussamArrivalsSaxHandler tussamSaxHandler, OkHttpClient okHttpClient) {
        return new TussamArrivalTimesRepository(saxParser, tussamSaxHandler, okHttpClient);
    }

    @Bean(name = {"mock"})
    public ArrivalTimesRepository provideMockRepository() {
        return new MockArrivalTimesRepository();
    }


}
