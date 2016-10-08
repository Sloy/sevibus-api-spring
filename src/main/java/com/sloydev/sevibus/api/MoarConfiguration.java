package com.sloydev.sevibus.api;

import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

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

    @Bean
    public FirebaseDatabase provideFirebaseDatabase(FirebaseConfiguration configuration) {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new ByteArrayInputStream(configuration.getDecodedAccountBytes()))
                .setDatabaseUrl(configuration.getUrl())
                .build();
        FirebaseApp.initializeApp(options);

        return FirebaseDatabase.getInstance();
    }
}
