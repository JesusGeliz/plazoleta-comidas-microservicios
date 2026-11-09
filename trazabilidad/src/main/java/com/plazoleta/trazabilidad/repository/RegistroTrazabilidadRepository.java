package com.plazoleta.trazabilidad.repository;

import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroTrazabilidadRepository extends JpaRepository<RegistroTrazabilidad, Long> {
}