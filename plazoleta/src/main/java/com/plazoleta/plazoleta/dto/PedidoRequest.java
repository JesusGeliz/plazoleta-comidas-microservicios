package com.plazoleta.plazoleta.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequest {

    @NotNull(message = "El id del restaurante es obligatorio")
    private Long idRestaurante;

    @NotEmpty(message = "El pedido debe tener al menos un plato")
    @Valid
    private List<PedidoPlatoRequest> platos;

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
    public List<PedidoPlatoRequest> getPlatos() { return platos; }
    public void setPlatos(List<PedidoPlatoRequest> platos) { this.platos = platos; }
}