package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.dto.PlatoModificarRequest;
import com.plazoleta.plazoleta.dto.PlatoRequest;
import com.plazoleta.plazoleta.dto.PlatoResumen;
import com.plazoleta.plazoleta.entity.Plato;
import com.plazoleta.plazoleta.entity.Restaurante;
import com.plazoleta.plazoleta.repository.PlatoRepository;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void modificarPlato(Long idPlato, PlatoModificarRequest request, Long idPropietario) {
        Plato plato = buscarPlatoDelPropietario(idPlato, idPropietario);
        plato.setPrecio(request.getPrecio());
        plato.setDescripcion(request.getDescripcion());
        platoRepository.save(plato);
    }

    public void cambiarEstado(Long idPlato, Boolean activo, Long idPropietario) {
        Plato plato = buscarPlatoDelPropietario(idPlato, idPropietario);
        plato.setActivo(activo);
        platoRepository.save(plato);
    }

    public List<PlatoResumen> listarPlatos(Long idRestaurante, String categoria, int pagina, int tamanio) {

        if (!restauranteRepository.existsById(idRestaurante)) {
            throw new IllegalArgumentException("El restaurante no existe");
        }
        if (pagina < 0) {
            throw new IllegalArgumentException("La página no puede ser negativa");
        }
        if (tamanio < 1) {
            throw new IllegalArgumentException("El tamaño de la página debe ser mayor a cero");
        }

        Pageable paginacion = PageRequest.of(pagina, tamanio, Sort.by("nombre").ascending());

        Page<Plato> platos;
        if (categoria == null || categoria.isBlank()) {
            platos = platoRepository.findByIdRestauranteAndActivoTrue(idRestaurante, paginacion);
        } else {
            platos = platoRepository.findByIdRestauranteAndActivoTrueAndCategoriaIgnoreCase(
                    idRestaurante, categoria, paginacion);
        }

        return platos.getContent().stream()
                .map(plato -> new PlatoResumen(plato.getId(), plato.getNombre(), plato.getPrecio(),
                        plato.getDescripcion(), plato.getUrlImagen(), plato.getCategoria()))
                .toList();
    }

    private Plato buscarPlatoDelPropietario(Long idPlato, Long idPropietario) {
        Plato plato = platoRepository.findById(idPlato)
                .orElseThrow(() -> new IllegalArgumentException("El plato no existe"));

        Restaurante restaurante = restauranteRepository.findById(plato.getIdRestaurante())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));

        if (!restaurante.getIdPropietario().equals(idPropietario)) {
            throw new IllegalArgumentException("El plato no pertenece a un restaurante del propietario autenticado");
        }

        return plato;
    }
}