package com.sloydev.sevibus.api.data.internal.apptussamjson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppTussamJsonApi {

    String URL_BASE = "http://94.198.88.152:9005/INFOTUS/API/";

    @GET("estadoTarjeta/{numeroSerie}")
    Call<EstadoTarjeta> estadoTajeta(@Path("numeroSerie") Long numeroSerie);
}
