package com.plazoleta.plazoleta.service;

import com.plazoleta.plazoleta.config.NotificacionClient;
import com.plazoleta.plazoleta.config.TrazabilidadClient;
import com.plazoleta.plazoleta.config.UsuarioClient;
import com.plazoleta.plazoleta.entity.EstadoPedido;
import com.plazoleta.plazoleta.entity.Pedido;
import com.plazoleta.plazoleta.repository.PedidoPlatoRepository;
import com.plazoleta.plazoleta.repository.PedidoRepository;
import com.plazoleta.plazoleta.repository.PlatoRepository;
import com.plazoleta.plazoleta.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoServiceTest {

    private PedidoRepository pedidoRepository;
    private TrazabilidadClient trazabilidadClient;
    private PedidoService pedidoService;

    @BeforeEach
    void preparar() {
        pedidoRepository = mock(PedidoRepository.class);
        trazabilidadClient = mock(TrazabilidadClient.class);

        pedidoService = new PedidoService(
                pedidoRepository,
                mock(PedidoPlatoRepository.class),
                mock(PlatoRepository.class),
                mock(RestauranteRepository.class),
                mock(UsuarioClient.class),
                trazabilidadClient,
                mock(NotificacionClient.class));
    }

    private Pedido crearPedido(Long id, Long idCliente, EstadoPedido estado) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setIdCliente(idCliente);
        pedido.setIdRestaurante(3L);
        pedido.setEstado(estado);
        return pedido;
    }

    @Test
    void cancelaUnPedidoPendienteYRegistraLaTrazabilidad() {
        Pedido pedido = crearPedido(1L, 5L, EstadoPedido.PENDIENTE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        pedidoService.cancelarPedido(1L, 5L, "token");

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        verify(pedidoRepository).save(pedido);
        verify(trazabilidadClient).registrar(1L, 5L, 3L, null, "PENDIENTE", "CANCELADO", "token");
    }

    @Test
    void noCancelaUnPedidoEnPreparacion() {
        Pedido pedido = crearPedido(1L, 5L, EstadoPedido.EN_PREPARACION);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.cancelarPedido(1L, 5L, "token"));

        assertEquals("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse", error.getMessage());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void noCancelaUnPedidoDeOtroCliente() {
        Pedido pedido = crearPedido(1L, 5L, EstadoPedido.PENDIENTE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.cancelarPedido(1L, 9L, "token"));

        assertEquals("El pedido no pertenece al cliente autenticado", error.getMessage());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void noCancelaUnPedidoQueNoExiste() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.cancelarPedido(99L, 5L, "token"));

        assertEquals("El pedido no existe", error.getMessage());
    }
}