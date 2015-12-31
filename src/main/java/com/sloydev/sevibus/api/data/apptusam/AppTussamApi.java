package com.sloydev.sevibus.api.data.apptusam;

import com.sloydev.sevibus.api.data.apptusam.model.Envelope;
import com.squareup.okhttp.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AppTussamApi {

    private static final String URL_SOAP_DINAMICA = "http://www.infobustussam.com:9005/InfoTusWS/services/InfoTus?WSDL";
    private static final String BODY_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<v:Envelope xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n  <v:Header />\n  <v:Body>\n    <n0:getTiemposNodo xmlns:n0=\"http://services.infotusws.tussam.com/\" id=\"o0\" c:root=\"1\">\n      <codigo i:type=\"d:int\">%s</codigo>\n    </n0:getTiemposNodo>\n  </v:Body>\n</v:Envelope>";

    public Envelope get(String parada) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(Envelope.class, getArrivalsInputStream(parada), false);
    }

    private InputStream getArrivalsInputStream(String stopNumber) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/xml;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, String.format(BODY_CONTENT, stopNumber));
        Request request = new Request.Builder()
          .url(URL_SOAP_DINAMICA)
          .post(body)
          .addHeader("content-type", "text/xml;charset=utf-8")
          .addHeader("authorization", "Basic aW5mb3R1cy11c2VybW9iaWxlOjJpbmZvdHVzMHVzZXIxbW9iaWxlMg==")
          .addHeader("deviceid", generateRandomDeviceId())
          .addHeader("cache-control", "no-cache")
          .build();

        Response response = client.newCall(request).execute();
        return response.body().byteStream();

    }

    private String generateRandomDeviceId() {
        return UUID.randomUUID().toString();
    }
}
