package com.sloydev.sevibus.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@ConfigurationProperties(locations = "classpath:application.yml", ignoreUnknownFields = false, prefix = "firebase")
public class FirebaseConfiguration {

    private String url;
    private String accountBase64;

    public byte[] getDecodedAccountBytes() {
        return Base64.getDecoder().decode(getAccountBase64());
    }

    public String getUrl() {
        return url;
    }

    public String getAccountBase64() {
        return accountBase64;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAccountBase64(String accountBase64) {
        this.accountBase64 = accountBase64;
    }
}
