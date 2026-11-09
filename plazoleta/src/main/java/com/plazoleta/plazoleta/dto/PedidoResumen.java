package com.plazoleta.plazoleta.dto;

import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.entity.PedidoPlato;

import java.util.List;

public class PedidoResumen {

    private Long id;
    private Long idCliente;
    private EstadoPedido estado;
    private List<PedidoPlato> platos;

    public PedidoResumen(Long id, Long idCliente, EstadoPedido estado, List<PedidoPlato> platos) {
        this.id = id;
        this.idCliente = idCliente;
        this.estado = estado;
        this.platos = platos;
    }

    public Long getId() { return id; }
    public Long getIdCliente() { return idCliente; }
    public EstadoPedido getEstado() { return estado; }
    public List<PedidoPlato> getPlatos() { return platos; }
}