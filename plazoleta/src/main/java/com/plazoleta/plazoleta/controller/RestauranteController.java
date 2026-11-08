package com.plazoleta.plazoleta.controller;

import com.plazoleta.plazoleta.dto.RestauranteRequest;
import com.plazoleta.plazoleta.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}