package com.plazoleta.plazoleta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_platos")
public class PedidoPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPedido;
    private Long idPlato;
    private Integer cantidad;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    public Long getIdPlato() { return idPlato; }
    public void setIdPlato(Long idPlato) { this.idPlato = idPlato; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}