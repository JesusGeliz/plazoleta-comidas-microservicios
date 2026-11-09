package com.plazoleta.plazoleta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UsuarioClient {

    private final RestClient restClient;

    public UsuarioClient(@Value("${usuarios.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public String obtenerRol(Long id, String token) {
        return restClient.get()
                .uri("/usuarios/{id}/rol", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(String.class);
    }

    public Long obtenerIdRestaurante(Long id, String token) {
        return restClient.get()
                .uri("/usuarios/{id}/restaurante", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(Long.class);
    }

    public String obtenerCelular(Long id, String token) {
        return restClient.get()
                .uri("/usuarios/{id}/celular", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(String.class);
    }
}