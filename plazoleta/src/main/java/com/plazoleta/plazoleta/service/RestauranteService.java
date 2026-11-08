package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.config.UsuarioClient;
import com.plazoleta.plazoleta.dto.RestauranteRequest;
import com.plazoleta.plazoleta.entity.Restaurante;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioClient usuarioClient;

    public RestauranteService(RestauranteRepository restauranteRepository, UsuarioClient usuarioClient) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioClient = usuarioClient;
    }

    public Long crearRestaurante(RestauranteRequest request, String token) {

        String rol = usuarioClient.obtenerRol(request.getIdPropietario(), token);
        if (!"PROPIETARIO".equals(rol)) {
            throw new IllegalArgumentException("El id asociado no corresponde a un usuario con rol Propietario");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(request.getNombre());
        restaurante.setNit(request.getNit());
        restaurante.setDireccion(request.getDireccion());
        restaurante.setTelefono(request.getTelefono());
        restaurante.setUrlLogo(request.getUrlLogo());
        restaurante.setIdPropietario(request.getIdPropietario());

        return restauranteRepository.save(restaurante).getId();
    }

    public Long obtenerIdPropietario(Long idRestaurante) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));
        return restaurante.getIdPropietario();
    }
}