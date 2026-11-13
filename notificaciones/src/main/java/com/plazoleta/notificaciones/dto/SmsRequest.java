package com.plazoleta.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;

public class SmsRequest {

    @NotBlank(message = "El celular es obligatorio")
    private String celular;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}