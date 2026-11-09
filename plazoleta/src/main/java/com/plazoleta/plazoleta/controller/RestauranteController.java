package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.RestauranteRequest;
import com.plazoleta.plazoleta.dto.RestauranteResumen;
import com.plazoleta.plazoleta.service.RestauranteService;
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
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<String> crearRestaurante(@Valid @RequestBody RestauranteRequest request,
                                                   Authentication autenticacion) {
        String token = (String) autenticacion.getCredentials();
        Long id = restauranteService.crearRestaurante(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurante creado con id " + id);
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResumen>> listarRestaurantes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        return ResponseEntity.ok(restauranteService.listarRestaurantes(pagina, tamanio));
    }

    @GetMapping("/{id}/propietario")
    public ResponseEntity<Long> obtenerIdPropietario(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.obtenerIdPropietario(id));
    }
}