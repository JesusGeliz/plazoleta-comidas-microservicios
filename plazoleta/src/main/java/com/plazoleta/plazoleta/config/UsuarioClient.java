package com.plazoleta.plazoleta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class UsuarioClient {

    private final RestClient restClient;

    public UsuarioClient(@Value("${usuarios.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public String obtenerRol(Long idUsuario, String token) {
        try {
            return restClient.get()
                    .uri("/usuarios/{id}/rol", idUsuario)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("El usuario asociado no existe");
        }
    }

    public Long obtenerIdRestaurante(Long idUsuario, String token) {
        try {
            return restClient.get()
                    .uri("/usuarios/{id}/restaurante", idUsuario)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(Long.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("El empleado no está asociado a un restaurante");
        }
    }
}