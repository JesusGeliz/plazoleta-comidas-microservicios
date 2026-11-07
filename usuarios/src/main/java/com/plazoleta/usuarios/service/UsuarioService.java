package com.plazoleta.usuarios.service;

import com.plazoleta.usuarios.dto.LoginRequest;
import com.plazoleta.usuarios.dto.PropietarioRequest;
import com.plazoleta.usuarios.entity.Rol;
import com.plazoleta.usuarios.entity.Usuario;
import com.plazoleta.usuarios.exception.CredencialesInvalidasException;
import com.plazoleta.usuarios.repository.UsuarioRepository;
import com.plazoleta.usuarios.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Long crearPropietario(PropietarioRequest request) {

        int edad = Period.between(request.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException("El propietario debe ser mayor de edad");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setDocumento(request.getDocumento());
        usuario.setCelular(request.getCelular());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setCorreo(request.getCorreo());
        usuario.setClave(passwordEncoder.encode(request.getClave()));
        usuario.setRol(Rol.PROPIETARIO);

        return usuarioRepository.save(usuario).getId();
    }

    public String login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getClave(), usuario.getClave())) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }

        return jwtService.generarToken(usuario.getCorreo(), usuario.getRol().name());
    }
}