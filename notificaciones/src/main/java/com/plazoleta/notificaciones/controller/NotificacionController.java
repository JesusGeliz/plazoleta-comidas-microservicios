package com.plazoleta.notificaciones.controller;

import com.plazoleta.notificaciones.dto.SmsRequest;
import com.plazoleta.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notificaciones")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Enviar un SMS (simulado) al cliente")
    @PostMapping("/sms")
    public ResponseEntity<String> enviarSms(@Valid @RequestBody SmsRequest request) {
        notificacionService.enviarSms(request.getCelular(), request.getMensaje());
        return ResponseEntity.ok("SMS enviado");
    }
}