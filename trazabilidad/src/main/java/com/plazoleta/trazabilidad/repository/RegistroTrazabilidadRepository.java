package com.plazoleta.trazabilidad.repository;

import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroTrazabilidadRepository extends JpaRepository<RegistroTrazabilidad, Long> {

    List<RegistroTrazabilidad> findByIdPedidoOrderByFechaAsc(Long idPedido);

    List<RegistroTrazabilidad> findByIdRestauranteOrderByFechaAsc(Long idRestaurante);
}