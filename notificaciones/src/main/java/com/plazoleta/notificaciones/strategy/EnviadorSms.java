package com.plazoleta.notificaciones.strategy;

public interface EnviadorSms {

    void enviar(String celular, String mensaje);
}