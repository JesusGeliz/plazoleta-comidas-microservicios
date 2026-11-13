package com.plazoleta.plazoleta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificacionClient {

    private final RestClient restClient;

    public NotificacionClient(@Value("${notificaciones.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public void enviarSms(String celular, String mensaje, String token) {

        Map<String, String> cuerpo = new HashMap<>();
        cuerpo.put("celular", celular);
        cuerpo.put("mensaje", mensaje);

        try {
            restClient.post()
                    .uri("/notificaciones/sms")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(cuerpo)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new IllegalArgumentException("No se pudo enviar la notificación al cliente");
        }
    }
}