package com.plazoleta.trazabilidad.dto;

import java.util.List;

public class EficienciaResponse {

    private List<PedidoTiempo> pedidos;
    private List<EmpleadoRanking> ranking;

    public EficienciaResponse(List<PedidoTiempo> pedidos, List<EmpleadoRanking> ranking) {
        this.pedidos = pedidos;
        this.ranking = ranking;
    }

    public List<PedidoTiempo> getPedidos() { return pedidos; }
    public List<EmpleadoRanking> getRanking() { return ranking; }
}