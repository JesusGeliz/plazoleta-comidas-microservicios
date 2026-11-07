package com.plazoleta.usuarios.config;

import com.plazoleta.usuarios.entity.Rol;
import com.plazoleta.usuarios.entity.Usuario;
import com.plazoleta.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdministradorInicial implements CommandLineRunner {

    private static final String CORREO_ADMIN = "admin@plazoleta.com";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String claveAdmin;

    public AdministradorInicial(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                                @Value("${admin.clave}") String claveAdmin) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.claveAdmin = claveAdmin;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.existsByCorreo(CORREO_ADMIN)) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setApellido("Sistema");
        admin.setDocumento("1000000000");
        admin.setCelular("+573000000000");
        admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        admin.setCorreo(CORREO_ADMIN);
        admin.setClave(passwordEncoder.encode(claveAdmin));
        admin.setRol(Rol.ADMINISTRADOR);

        usuarioRepository.save(admin);
    }
}