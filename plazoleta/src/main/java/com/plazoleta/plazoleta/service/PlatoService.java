package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.dto.PlatoRequest;
import com.plazoleta.plazoleta.entity.Plato;
import com.plazoleta.plazoleta.entity.Restaurante;
import com.plazoleta.plazoleta.repository.PlatoRepository;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;

    public PlatoService(PlatoRepository platoRepository, RestauranteRepository restauranteRepository) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Long crearPlato(PlatoRequest request, Long idPropietario) {

        Restaurante restaurante = restauranteRepository.findById(request.getIdRestaurante())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));

        if (!restaurante.getIdPropietario().equals(idPropietario)) {
            throw new IllegalArgumentException("El restaurante no pertenece al propietario autenticado");
        }

        Plato plato = new Plato();
        plato.setNombre(request.getNombre());
        plato.setPrecio(request.getPrecio());
        plato.setDescripcion(request.getDescripcion());
        plato.setUrlImagen(request.getUrlImagen());
        plato.setCategoria(request.getCategoria());
        plato.setIdRestaurante(request.getIdRestaurante());
        plato.setActivo(true);

        return platoRepository.save(plato).getId();
    }
}