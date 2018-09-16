package com.sloydev.sevibus.api.data.internal.apptussamjson;

public class EstadoTarjeta {
    private final Long numeroSerie;//  3771898948,
    private final Long codigoTitulo;//  31,
    private final String nombreTitulo;//  "Bonobús saldo ST",
    private final String ultimaOperacion;//  "14/3/2018  18:57h",
    private final Long saldoMonedero;//  6500,
    private final Long saldoViajes;//  0,
    private final Long resultado;//  0,
    private final String caducidad;//  "Tarjeta sin caducidad"

    public EstadoTarjeta(Long numeroSerie, Long codigoTitulo, String nombreTitulo, String ultimaOperacion, Long saldoMonedero,
            Long saldoViajes, Long resultado, String caducidad) {
        this.numeroSerie = numeroSerie;
        this.codigoTitulo = codigoTitulo;
        this.nombreTitulo = nombreTitulo;
        this.ultimaOperacion = ultimaOperacion;
        this.saldoMonedero = saldoMonedero;
        this.saldoViajes = saldoViajes;
        this.resultado = resultado;
        this.caducidad = caducidad;
    }

    public Long getNumeroSerie() {
        return numeroSerie;
    }

    public Long getCodigoTitulo() {
        return codigoTitulo;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public String getUltimaOperacion() {
        return ultimaOperacion;
    }

    public Long getSaldoMonedero() {
        return saldoMonedero;
    }

    public Long getSaldoViajes() {
        return saldoViajes;
    }

    public Long getResultado() {
        return resultado;
    }

    public String getCaducidad() {
        return caducidad;
    }
}
