package com.plazoleta.plazoleta.repository;

import com.plazoleta.plazoleta.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}