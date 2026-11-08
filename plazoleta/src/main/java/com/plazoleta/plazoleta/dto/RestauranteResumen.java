package com.plazoleta.plazoleta.dto;

public class RestauranteResumen {

    private String nombre;
    private String urlLogo;

    public RestauranteResumen(String nombre, String urlLogo) {
        this.nombre = nombre;
        this.urlLogo = urlLogo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlLogo() {
        return urlLogo;
    }
}