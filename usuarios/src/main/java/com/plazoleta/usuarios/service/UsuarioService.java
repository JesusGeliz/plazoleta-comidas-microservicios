package com.plazoleta.usuarios.service;

import com.plazoleta.usuarios.config.PlazoletaClient;
import com.plazoleta.usuarios.dto.EmpleadoRequest;
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
    private final PlazoletaClient plazoletaClient;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          JwtService jwtService, PlazoletaClient plazoletaClient) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.plazoletaClient = plazoletaClient;
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

    public Long crearEmpleado(EmpleadoRequest request, Long idPropietario, String token) {

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Long idDuenio = plazoletaClient.obtenerIdPropietario(request.getIdRestaurante(), token);
        if (!idDuenio.equals(idPropietario)) {
            throw new IllegalArgumentException("El restaurante no pertenece al propietario autenticado");
        }

        Usuario empleado = new Usuario();
        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setDocumento(request.getDocumento());
        empleado.setCelular(request.getCelular());
        empleado.setFechaNacimiento(request.getFechaNacimiento());
        empleado.setCorreo(request.getCorreo());
        empleado.setClave(passwordEncoder.encode(request.getClave()));
        empleado.setRol(Rol.EMPLEADO);
        empleado.setIdRestaurante(request.getIdRestaurante());

        return usuarioRepository.save(empleado).getId();
    }

    public String login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getClave(), usuario.getClave())) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }

        return jwtService.generarToken(usuario.getId(), usuario.getCorreo(), usuario.getRol().name());
    }

    public String obtenerRol(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        return usuario.getRol().name();
    }
}