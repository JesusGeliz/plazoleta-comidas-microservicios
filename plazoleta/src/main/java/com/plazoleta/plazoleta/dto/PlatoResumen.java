package com.plazoleta.plazoleta.dto;

public class PlatoResumen {

    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;

    public PlatoResumen(Long id, String nombre, Integer precio, String descripcion,
                        String urlImagen, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Integer getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public String getUrlImagen() { return urlImagen; }
    public String getCategoria() { return categoria; }
}