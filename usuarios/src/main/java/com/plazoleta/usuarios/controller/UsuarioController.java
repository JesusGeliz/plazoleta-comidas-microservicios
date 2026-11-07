package com.plazoleta.usuarios.controller;

import com.plazoleta.usuarios.dto.LoginRequest;
import com.plazoleta.usuarios.dto.PropietarioRequest;
import com.plazoleta.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/propietarios")
    public ResponseEntity<String> crearPropietario(@Valid @RequestBody PropietarioRequest request) {
        Long id = usuarioService.crearPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Propietario creado con id " + id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }
}