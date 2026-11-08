package com.plazoleta.plazoleta.repository;

import com.plazoleta.plazoleta.entity.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Long> {

    Page<Plato> findByIdRestauranteAndActivoTrue(Long idRestaurante, Pageable pageable);

    Page<Plato> findByIdRestauranteAndActivoTrueAndCategoriaIgnoreCase(Long idRestaurante, String categoria, Pageable pageable);
}