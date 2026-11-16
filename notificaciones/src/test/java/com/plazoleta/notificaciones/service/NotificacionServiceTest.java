package com.plazoleta.notificaciones.service;

import com.plazoleta.notificaciones.strategy.EnviadorSms;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificacionServiceTest {

    @Test
    void enviarSmsDelegaEnLaEstrategia() {
        EnviadorSms enviador = mock(EnviadorSms.class);
        NotificacionService servicio = new NotificacionService(enviador);

        servicio.enviarSms("+573001234567", "Tu pedido esta listo");

        verify(enviador).enviar("+573001234567", "Tu pedido esta listo");
    }
}