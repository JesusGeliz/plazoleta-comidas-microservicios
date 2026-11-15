package com.plazoleta.trazabilidad.dto;

public class EmpleadoRanking {

    private Long idEmpleado;
    private double promedioSegundos;
    private int pedidosAtendidos;

    public EmpleadoRanking(Long idEmpleado, double promedioSegundos, int pedidosAtendidos) {
        this.idEmpleado = idEmpleado;
        this.promedioSegundos = promedioSegundos;
        this.pedidosAtendidos = pedidosAtendidos;
    }

    public Long getIdEmpleado() { return idEmpleado; }
    public double getPromedioSegundos() { return promedioSegundos; }
    public int getPedidosAtendidos() { return pedidosAtendidos; }
}