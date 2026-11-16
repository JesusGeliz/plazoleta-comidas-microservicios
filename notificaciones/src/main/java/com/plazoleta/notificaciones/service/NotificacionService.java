package com.plazoleta.notificaciones.service;

import com.plazoleta.notificaciones.strategy.EnviadorSms;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private final EnviadorSms enviadorSms;

    public NotificacionService(EnviadorSms enviadorSms) {
        this.enviadorSms = enviadorSms;
    }

    public void enviarSms(String celular, String mensaje) {
        enviadorSms.enviar(celular, mensaje);
    }
}