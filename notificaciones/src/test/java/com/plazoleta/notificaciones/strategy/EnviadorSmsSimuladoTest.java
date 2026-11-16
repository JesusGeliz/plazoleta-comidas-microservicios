package com.plazoleta.notificaciones.strategy;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EnviadorSmsSimuladoTest {

    @Test
    void imprimeElSmsEnConsola() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(salida));

        try {
            new EnviadorSmsSimulado().enviar("+573001234567", "Hola");
        } finally {
            System.setOut(original);
        }

        assertTrue(salida.toString().contains("SMS simulado a +573001234567: Hola"));
    }
}