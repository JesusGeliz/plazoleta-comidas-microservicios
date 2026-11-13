package com.plazoleta.notificaciones.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public void enviarSms(String celular, String mensaje) {
        System.out.println("SMS simulado a " + celular + ": " + mensaje);
    }
}
