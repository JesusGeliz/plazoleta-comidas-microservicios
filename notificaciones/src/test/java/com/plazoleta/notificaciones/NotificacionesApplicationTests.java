package com.plazoleta.notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jwt.secret=clave-de-prueba-solo-para-tests-0123456789")
class NotificacionesApplicationTests {

    @Test
    void contextLoads() {
    }
}