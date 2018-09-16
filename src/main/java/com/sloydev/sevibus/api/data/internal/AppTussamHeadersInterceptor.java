package com.sloydev.sevibus.api.data.internal;

import java.io.IOException;
import java.util.UUID;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppTussamHeadersInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Basic aW5mb3R1cy11c2VybW9iaWxlOjJpbmZvdHVzMHVzZXIxbW9iaWxlMg==")
                .addHeader("deviceid", generateRandomDeviceId())
                .addHeader("cache-control", "no-cache")
                .build();
        return chain.proceed(newRequest);
    }

    private String generateRandomDeviceId() {
        return UUID.randomUUID().toString();
    }
}
