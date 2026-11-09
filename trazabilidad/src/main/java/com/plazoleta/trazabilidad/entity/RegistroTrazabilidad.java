package com.plazoleta.trazabilidad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_trazabilidad")
public class RegistroTrazabilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPedido;
    private Long idCliente;
    private Long idRestaurante;
    private Long idEmpleado;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fecha;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}