package com.sloydev.sevibus.api;

import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.data.internal.AppTussamJsonHeadersInterceptor;
import com.sloydev.sevibus.api.data.internal.apptussamjson.AppTussamJsonApi;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class MoarConfiguration {

    private static final boolean logRequests = true;

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
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (logRequests) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
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

    @Bean
    public AppTussamJsonApi provideAppTussamJsonApi(OkHttpClient client) {
        OkHttpClient customClient = client.newBuilder()
                .addInterceptor(new AppTussamJsonHeadersInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(AppTussamJsonApi.URL_BASE)
                .client(customClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppTussamJsonApi.class);
    }
}
