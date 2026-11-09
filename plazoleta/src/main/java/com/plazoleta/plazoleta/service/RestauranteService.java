package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.config.UsuarioClient;
import com.plazoleta.plazoleta.dto.RestauranteRequest;
import com.plazoleta.plazoleta.dto.RestauranteResumen;
import com.plazoleta.plazoleta.entity.Restaurante;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<RestauranteResumen> listarRestaurantes(int pagina, int tamanio) {

        if (pagina < 0) {
            throw new IllegalArgumentException("La página no puede ser negativa");
        }
        if (tamanio < 1) {
            throw new IllegalArgumentException("El tamaño de la página debe ser mayor a cero");
        }

        Pageable paginacion = PageRequest.of(pagina, tamanio, Sort.by("nombre").ascending());

        return restauranteRepository.findAll(paginacion).getContent().stream()
                .map(restaurante -> new RestauranteResumen(restaurante.getNombre(), restaurante.getUrlLogo()))
                .toList();
    }
}