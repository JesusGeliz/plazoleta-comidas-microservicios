package com.plazoleta.trazabilidad.controller;

import com.plazoleta.trazabilidad.dto.EficienciaResponse;
import com.plazoleta.trazabilidad.dto.RegistroRequest;
import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import com.plazoleta.trazabilidad.service.TrazabilidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/pedidos/{idPedido}")
    public ResponseEntity<List<RegistroTrazabilidad>> consultarPorPedido(@PathVariable Long idPedido,
                                                                         Authentication autenticacion) {
        Long idCliente = (Long) autenticacion.getPrincipal();
        return ResponseEntity.ok(trazabilidadService.consultarPorPedido(idPedido, idCliente));
    }

    @GetMapping("/eficiencia")
    public ResponseEntity<EficienciaResponse> consultarEficiencia(@RequestParam Long idRestaurante,
                                                                  Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        return ResponseEntity.ok(trazabilidadService.consultarEficiencia(idRestaurante, idPropietario, token));
    }
}