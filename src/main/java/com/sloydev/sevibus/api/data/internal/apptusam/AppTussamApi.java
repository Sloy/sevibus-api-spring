package com.sloydev.sevibus.api.data.internal.apptusam;

import com.sloydev.sevibus.api.data.internal.apptusam.arrivals.model.Envelope;
import com.squareup.okhttp.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AppTussamApi {

    private static final String URL_SOAP_DINAMICA = "http://www.infobustussam.com:9005/InfoTusWS/services/InfoTus?WSDL";
    private static final String ARRIVALS_BODY_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<v:Envelope xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n  <v:Header />\n  <v:Body>\n    <n0:getTiemposNodo xmlns:n0=\"http://services.infotusws.tussam.com/\" id=\"o0\" c:root=\"1\">\n      <codigo i:type=\"d:int\">%s</codigo>\n    </n0:getTiemposNodo>\n  </v:Body>\n</v:Envelope>";
    private static final String CARD_BODY_CONTENT = "<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\"><v:Header /><v:Body><n0:getCardState id=\"o0\" c:root=\"1\" xmlns:n0=\"http://services.infotusws.tussam.com/\"><chipNumber i:type=\"d:long\">%d</chipNumber><date i:type=\"d:string\">%s</date></n0:getCardState></v:Body></v:Envelope>";

    private final OkHttpClient client;

    @Autowired
    public AppTussamApi(OkHttpClient client) {
        this.client = client;
    }

    public Envelope getArrival(String parada) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(Envelope.class, getArrivalsInputStream(parada), false);
    }

    private InputStream getArrivalsInputStream(String stopNumber) throws IOException {
        MediaType mediaType = MediaType.parse("text/xml;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, String.format(ARRIVALS_BODY_CONTENT, stopNumber));
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
