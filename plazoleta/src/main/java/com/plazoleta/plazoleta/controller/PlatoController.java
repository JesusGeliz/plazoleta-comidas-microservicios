package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.PlatoEstadoRequest;
import com.plazoleta.plazoleta.dto.PlatoModificarRequest;
import com.plazoleta.plazoleta.dto.PlatoRequest;
import com.plazoleta.plazoleta.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarPlato(@PathVariable Long id,
                                                 @Valid @RequestBody PlatoModificarRequest request,
                                                 Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        platoService.modificarPlato(id, request, idPropietario);
        return ResponseEntity.ok("Plato modificado correctamente");
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id,
                                                @Valid @RequestBody PlatoEstadoRequest request,
                                                Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        platoService.cambiarEstado(id, request.getActivo(), idPropietario);
        return ResponseEntity.ok("Estado del plato actualizado");
    }
}