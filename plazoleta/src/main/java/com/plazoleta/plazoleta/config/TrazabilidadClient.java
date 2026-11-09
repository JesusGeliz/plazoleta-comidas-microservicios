package com.plazoleta.plazoleta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Component
public class TrazabilidadClient {

    private final RestClient restClient;

    public TrazabilidadClient(@Value("${trazabilidad.url}") String url) {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public void registrar(Long idPedido, Long idCliente, Long idRestaurante, Long idEmpleado,
                          String estadoAnterior, String estadoNuevo, String token) {

        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("idPedido", idPedido);
        cuerpo.put("idCliente", idCliente);
        cuerpo.put("idRestaurante", idRestaurante);
        cuerpo.put("idEmpleado", idEmpleado);
        cuerpo.put("estadoAnterior", estadoAnterior);
        cuerpo.put("estadoNuevo", estadoNuevo);

        try {
            restClient.post()
                    .uri("/trazabilidad")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(cuerpo)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new IllegalArgumentException("No se pudo registrar la trazabilidad del pedido");
        }
    }
}