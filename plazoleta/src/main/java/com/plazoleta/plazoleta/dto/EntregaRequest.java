package com.plazoleta.plazoleta.dto;

import jakarta.validation.constraints.NotBlank;

public class EntregaRequest {

    @NotBlank(message = "El PIN es obligatorio")
    private String pin;

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}