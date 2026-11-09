package com.plazoleta.plazoleta.repository;

import com.plazoleta.plazoleta.entity.PedidoPlato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoPlatoRepository extends JpaRepository<PedidoPlato, Long> {

    List<PedidoPlato> findByIdPedido(Long idPedido);
}