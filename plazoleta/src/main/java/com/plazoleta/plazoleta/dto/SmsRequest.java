package com.plazoleta.plazoleta.dto;

public class SmsRequest {

    private String celular;
    private String mensaje;

    public SmsRequest(String celular, String mensaje) {
        this.celular = celular;
        this.mensaje = mensaje;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}