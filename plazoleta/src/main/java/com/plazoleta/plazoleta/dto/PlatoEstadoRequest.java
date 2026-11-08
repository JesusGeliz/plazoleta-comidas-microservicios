package com.plazoleta.plazoleta.dto;

import jakarta.validation.constraints.NotNull;

public class PlatoEstadoRequest {

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
