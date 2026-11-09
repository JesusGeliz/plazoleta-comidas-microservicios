package com.plazoleta.notificaciones.controller;

import com.plazoleta.notificaciones.dto.SmsRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @PostMapping("/sms")
    public ResponseEntity<String> enviarSms(@Valid @RequestBody SmsRequest request) {
        // Decisión propia del equipo: El SMS es simulado y se imprime en consola
        System.out.println("========================================");
        System.out.println("Enviando SMS simulado...");
        System.out.println("Destino: " + request.getCelular());
        System.out.println("Mensaje: " + request.getMensaje());
        System.out.println("========================================");
        return ResponseEntity.ok("SMS enviado correctamente");
    }
}