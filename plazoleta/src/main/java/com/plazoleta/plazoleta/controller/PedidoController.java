package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.PedidoRequest;
import com.plazoleta.plazoleta.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Long id = pedidoService.crearPedido(request, idCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado con id " + id + ", estado PENDIENTE");
    }
}