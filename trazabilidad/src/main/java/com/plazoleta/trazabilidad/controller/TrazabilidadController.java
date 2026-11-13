package com.plazoleta.trazabilidad.controller;

import com.plazoleta.trazabilidad.dto.RegistroRequest;
import com.plazoleta.trazabilidad.service.TrazabilidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trazabilidad")
public class TrazabilidadController {

    private final TrazabilidadService trazabilidadService;

    public TrazabilidadController(TrazabilidadService trazabilidadService) {
        this.trazabilidadService = trazabilidadService;
    }

    @PostMapping
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequest request) {
        Long id = trazabilidadService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro de trazabilidad creado con id " + id);
    }
}