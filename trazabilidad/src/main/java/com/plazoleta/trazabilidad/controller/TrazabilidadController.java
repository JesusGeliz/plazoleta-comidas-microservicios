package com.plazoleta.trazabilidad.controller;

import com.plazoleta.trazabilidad.dto.EficienciaResponse;
import com.plazoleta.trazabilidad.dto.RegistroRequest;
import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import com.plazoleta.trazabilidad.service.TrazabilidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Trazabilidad")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/trazabilidad")
public class TrazabilidadController {

    private final TrazabilidadService trazabilidadService;

    public TrazabilidadController(TrazabilidadService trazabilidadService) {
        this.trazabilidadService = trazabilidadService;
    }

    @Operation(summary = "Registrar un cambio de estado de un pedido (uso interno entre microservicios)")
    @PostMapping
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequest request) {
        Long id = trazabilidadService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro de trazabilidad creado con id " + id);
    }

    @Operation(summary = "Consultar la trazabilidad de un pedido del cliente")
    @GetMapping("/pedidos/{idPedido}")
    public ResponseEntity<List<RegistroTrazabilidad>> consultarPorPedido(@PathVariable Long idPedido,
                                                                         Authentication autenticacion) {
        Long idCliente = (Long) autenticacion.getPrincipal();
        return ResponseEntity.ok(trazabilidadService.consultarPorPedido(idPedido, idCliente));
    }

    @Operation(summary = "Consultar la eficiencia de los pedidos y el ranking de empleados")
    @GetMapping("/eficiencia")
    public ResponseEntity<EficienciaResponse> consultarEficiencia(@RequestParam Long idRestaurante,
                                                                  Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        return ResponseEntity.ok(trazabilidadService.consultarEficiencia(idRestaurante, idPropietario, token));
    }
}