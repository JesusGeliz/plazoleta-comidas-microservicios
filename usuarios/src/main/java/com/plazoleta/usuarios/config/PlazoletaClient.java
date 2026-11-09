package com.plazoleta.usuarios.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class PlazoletaClient {

    private final RestClient restClient;

    public PlazoletaClient(@Value("${plazoleta.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public Long obtenerIdPropietario(Long idRestaurante, String token) {
        try {
            return restClient.get()
                    .uri("/restaurantes/{id}/propietario", idRestaurante)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(Long.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("El restaurante no existe");
        }
    }
}