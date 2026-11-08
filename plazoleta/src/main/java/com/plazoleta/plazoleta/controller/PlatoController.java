package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.PlatoRequest;
import com.plazoleta.plazoleta.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @PostMapping
    public ResponseEntity<String> crearPlato(@Valid @RequestBody PlatoRequest request,
                                             Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        Long id = platoService.crearPlato(request, idPropietario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Plato creado con id " + id);
    }
}