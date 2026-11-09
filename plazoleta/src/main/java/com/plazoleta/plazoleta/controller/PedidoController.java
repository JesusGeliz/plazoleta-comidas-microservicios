package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.PedidoRequest;
import com.plazoleta.plazoleta.dto.PedidoResumen;
import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.service.PedidoService;
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

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<String> crearPedido(@Valid @RequestBody PedidoRequest request,
                                              Authentication autenticacion) {
        Long idCliente = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        Long id = pedidoService.crearPedido(request, idCliente, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado con id " + id + ", estado PENDIENTE");
    }

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

    @PatchMapping("/{id}/asignar")
    public ResponseEntity<String> asignarPedido(@PathVariable Long id, Authentication autenticacion) {
        Long idEmpleado = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        pedidoService.asignarPedido(id, idEmpleado, token);
        return ResponseEntity.ok("Pedido asignado, estado EN_PREPARACION");
    }
}