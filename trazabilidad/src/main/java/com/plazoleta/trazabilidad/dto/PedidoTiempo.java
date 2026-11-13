package com.plazoleta.trazabilidad.dto;

public class PedidoTiempo {

    private Long idPedido;
    private Long idEmpleado;
    private long segundos;

    public PedidoTiempo(Long idPedido, Long idEmpleado, long segundos) {
        this.idPedido = idPedido;
        this.idEmpleado = idEmpleado;
        this.segundos = segundos;
    }

    public Long getIdPedido() { return idPedido; }
    public Long getIdEmpleado() { return idEmpleado; }
    public long getSegundos() { return segundos; }
}