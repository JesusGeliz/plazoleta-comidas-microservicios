package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.EntregaRequest;
import com.plazoleta.plazoleta.dto.PedidoRequest;
import com.plazoleta.plazoleta.dto.PedidoResumen;
import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Realizar un pedido (cliente)")
    @PostMapping
    public ResponseEntity<String> crearPedido(@Valid @RequestBody PedidoRequest request,
                                              Authentication autenticacion) {
        Long idCliente = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        Long id = pedidoService.crearPedido(request, idCliente, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado con id " + id + ", estado PENDIENTE");
    }

    @Operation(summary = "Listar los pedidos del restaurante del empleado por estado")
    @GetMapping
    public ResponseEntity<List<PedidoResumen>> listarPedidos(
            @RequestParam EstadoPedido estado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            Authentication autenticacion) {
        Long idEmpleado = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        return ResponseEntity.ok(pedidoService.listarPedidos(estado, pagina, tamanio, idEmpleado, token));
    }

    @Operation(summary = "Asignar un pedido al empleado y pasarlo a En preparación")
    @PatchMapping("/{id}/asignar")
    public ResponseEntity<String> asignarPedido(@PathVariable Long id, Authentication autenticacion) {
        Long idEmpleado = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        pedidoService.asignarPedido(id, idEmpleado, token);
        return ResponseEntity.ok("Pedido asignado, estado EN_PREPARACION");
    }

    @Operation(summary = "Marcar el pedido como listo y enviar el PIN por SMS")
    @PatchMapping("/{id}/listo")
    public ResponseEntity<String> marcarListo(@PathVariable Long id, Authentication autenticacion) {
        Long idEmpleado = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        pedidoService.marcarListo(id, idEmpleado, token);
        return ResponseEntity.ok("Pedido marcado como listo, SMS enviado al cliente");
    }

    @Operation(summary = "Entregar el pedido validando el PIN")
    @PatchMapping("/{id}/entregar")
    public ResponseEntity<String> entregarPedido(@PathVariable Long id,
                                                 @Valid @RequestBody EntregaRequest request,
                                                 Authentication autenticacion) {
        Long idEmpleado = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        pedidoService.entregarPedido(id, request.getPin(), idEmpleado, token);
        return ResponseEntity.ok("Pedido entregado correctamente");
    }

    @Operation(summary = "Cancelar un pedido pendiente")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id, Authentication autenticacion) {
        Long idCliente = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        pedidoService.cancelarPedido(id, idCliente, token);
        return ResponseEntity.ok("Pedido cancelado correctamente");
    }
}