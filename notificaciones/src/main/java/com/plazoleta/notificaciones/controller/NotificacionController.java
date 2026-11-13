package com.plazoleta.notificaciones.controller;

import com.plazoleta.notificaciones.dto.SmsRequest;
import com.plazoleta.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/sms")
    public ResponseEntity<String> enviarSms(@Valid @RequestBody SmsRequest request) {
        notificacionService.enviarSms(request.getCelular(), request.getMensaje());
        return ResponseEntity.ok("SMS enviado");
    }
}