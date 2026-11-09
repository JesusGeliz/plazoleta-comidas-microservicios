package com.plazoleta.plazoleta.repository;

import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<EstadoPedido> estados);

    Page<Pedido> findByIdRestauranteAndEstado(Long idRestaurante, EstadoPedido estado, Pageable pageable);
}