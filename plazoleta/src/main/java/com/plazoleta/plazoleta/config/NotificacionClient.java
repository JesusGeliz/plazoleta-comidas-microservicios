package com.plazoleta.plazoleta.config;

import com.plazoleta.plazoleta.dto.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NotificacionClient {

    private final RestClient restClient;

    public NotificacionClient(@Value("${notificaciones.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public void enviarSms(SmsRequest request, String token) {
        restClient.post()
                .uri("/notificaciones/sms")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}