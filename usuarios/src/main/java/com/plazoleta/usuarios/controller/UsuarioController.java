package com.plazoleta.usuarios.controller;

import com.plazoleta.usuarios.dto.ClienteRequest;
import com.plazoleta.usuarios.dto.EmpleadoRequest;
import com.plazoleta.usuarios.dto.LoginRequest;
import com.plazoleta.usuarios.dto.PropietarioRequest;
import com.plazoleta.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/empleados")
    public ResponseEntity<String> crearEmpleado(@Valid @RequestBody EmpleadoRequest request,
                                                Authentication autenticacion) {
        Long idPropietario = (Long) autenticacion.getPrincipal();
        String token = (String) autenticacion.getCredentials();
        Long id = usuarioService.crearEmpleado(request, idPropietario, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empleado creado con id " + id);
    }

    @PostMapping("/clientes")
    public ResponseEntity<String> crearCliente(@Valid @RequestBody ClienteRequest request) {
        Long id = usuarioService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con id " + id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{id}/rol")
    public ResponseEntity<String> obtenerRol(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerRol(id));
    }

    @GetMapping("/{id}/restaurante")
    public ResponseEntity<Long> obtenerIdRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerIdRestaurante(id));
    }

    @GetMapping("/{id}/celular")
    public ResponseEntity<String> obtenerCelular(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerCelular(id));
    }
}