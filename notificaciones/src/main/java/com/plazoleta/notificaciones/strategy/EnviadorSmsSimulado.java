package com.plazoleta.notificaciones.strategy;

import org.springframework.stereotype.Component;

@Component
public class EnviadorSmsSimulado implements EnviadorSms {

    @Override
    public void enviar(String celular, String mensaje) {
        System.out.println("SMS simulado a " + celular + ": " + mensaje);
    }
}