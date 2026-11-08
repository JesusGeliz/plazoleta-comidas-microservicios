package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.dto.PedidoPlatoRequest;
import com.plazoleta.plazoleta.dto.PedidoRequest;
import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.entity.Pedido;
import com.plazoleta.plazoleta.entity.PedidoPlato;
import com.plazoleta.plazoleta.entity.Plato;
import com.plazoleta.plazoleta.repository.PedidoPlatoRepository;
import com.plazoleta.plazoleta.repository.PedidoRepository;
import com.plazoleta.plazoleta.repository.PlatoRepository;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoService(PedidoRepository pedidoRepository, PedidoPlatoRepository pedidoPlatoRepository,
                         PlatoRepository platoRepository, RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoPlatoRepository = pedidoPlatoRepository;
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public Long crearPedido(PedidoRequest request, Long idCliente) {

        if (!restauranteRepository.existsById(request.getIdRestaurante())) {
            throw new IllegalArgumentException("El restaurante no existe");
        }

        List<EstadoPedido> estadosEnProceso =
                List.of(EstadoPedido.PENDIENTE, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO);
        if (pedidoRepository.existsByIdClienteAndEstadoIn(idCliente, estadosEnProceso)) {
            throw new IllegalArgumentException("El cliente ya tiene un pedido en proceso");
        }

        for (PedidoPlatoRequest item : request.getPlatos()) {
            Plato plato = platoRepository.findById(item.getIdPlato())
                    .orElseThrow(() -> new IllegalArgumentException("Uno de los platos no existe"));

            if (!plato.getIdRestaurante().equals(request.getIdRestaurante())) {
                throw new IllegalArgumentException("Todos los platos deben ser del restaurante indicado");
            }
            if (!plato.getActivo()) {
                throw new IllegalArgumentException("Uno de los platos no está disponible");
            }
        }

        Pedido pedido = new Pedido();
        pedido.setIdCliente(idCliente);
        pedido.setIdRestaurante(request.getIdRestaurante());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        Pedido guardado = pedidoRepository.save(pedido);

        for (PedidoPlatoRequest item : request.getPlatos()) {
            PedidoPlato pedidoPlato = new PedidoPlato();
            pedidoPlato.setIdPedido(guardado.getId());
            pedidoPlato.setIdPlato(item.getIdPlato());
            pedidoPlato.setCantidad(item.getCantidad());
            pedidoPlatoRepository.save(pedidoPlato);
        }

        return guardado.getId();
    }
}