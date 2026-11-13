package com.plazoleta.trazabilidad.service;

import com.plazoleta.trazabilidad.config.PlazoletaClient;
import com.plazoleta.trazabilidad.dto.EficienciaResponse;
import com.plazoleta.trazabilidad.dto.EmpleadoRanking;
import com.plazoleta.trazabilidad.dto.PedidoTiempo;
import com.plazoleta.trazabilidad.dto.RegistroRequest;
import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import com.plazoleta.trazabilidad.repository.RegistroTrazabilidadRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrazabilidadService {

    private final RegistroTrazabilidadRepository registroRepository;
    private final PlazoletaClient plazoletaClient;

    public TrazabilidadService(RegistroTrazabilidadRepository registroRepository,
                               PlazoletaClient plazoletaClient) {
        this.registroRepository = registroRepository;
        this.plazoletaClient = plazoletaClient;
    }

    public Long registrar(RegistroRequest request) {
        RegistroTrazabilidad registro = new RegistroTrazabilidad();
        registro.setIdPedido(request.getIdPedido());
        registro.setIdCliente(request.getIdCliente());
        registro.setIdRestaurante(request.getIdRestaurante());
        registro.setIdEmpleado(request.getIdEmpleado());
        registro.setEstadoAnterior(request.getEstadoAnterior());
        registro.setEstadoNuevo(request.getEstadoNuevo());
        registro.setFecha(LocalDateTime.now());

        return registroRepository.save(registro).getId();
    }

    public List<RegistroTrazabilidad> consultarPorPedido(Long idPedido, Long idCliente) {

        List<RegistroTrazabilidad> registros = registroRepository.findByIdPedidoOrderByFechaAsc(idPedido);

        if (registros.isEmpty()) {
            throw new IllegalArgumentException("El pedido no existe o no tiene registros");
        }
        if (!registros.get(0).getIdCliente().equals(idCliente)) {
            throw new IllegalArgumentException("El pedido no pertenece al cliente autenticado");
        }

        return registros;
    }

    public EficienciaResponse consultarEficiencia(Long idRestaurante, Long idPropietario, String token) {

        Long idDuenio = plazoletaClient.obtenerIdPropietario(idRestaurante, token);
        if (!idDuenio.equals(idPropietario)) {
            throw new IllegalArgumentException("El restaurante no pertenece al propietario autenticado");
        }

        List<RegistroTrazabilidad> registros = registroRepository.findByIdRestauranteOrderByFechaAsc(idRestaurante);

        Map<Long, List<RegistroTrazabilidad>> registrosPorPedido = new LinkedHashMap<>();
        for (RegistroTrazabilidad registro : registros) {
            registrosPorPedido.computeIfAbsent(registro.getIdPedido(), id -> new ArrayList<>()).add(registro);
        }

        List<PedidoTiempo> pedidos = new ArrayList<>();
        Map<Long, List<Long>> tiemposPorEmpleado = new LinkedHashMap<>();

        for (Map.Entry<Long, List<RegistroTrazabilidad>> entrada : registrosPorPedido.entrySet()) {
            LocalDateTime inicio = null;
            LocalDateTime fin = null;
            Long idEmpleado = null;

            for (RegistroTrazabilidad registro : entrada.getValue()) {
                if ("PENDIENTE".equals(registro.getEstadoNuevo())) {
                    inicio = registro.getFecha();
                }
                if ("EN_PREPARACION".equals(registro.getEstadoNuevo())) {
                    idEmpleado = registro.getIdEmpleado();
                }
                if ("ENTREGADO".equals(registro.getEstadoNuevo())) {
                    fin = registro.getFecha();
                }
            }

            if (inicio != null && fin != null) {
                long segundos = Duration.between(inicio, fin).getSeconds();
                pedidos.add(new PedidoTiempo(entrada.getKey(), idEmpleado, segundos));
                if (idEmpleado != null) {
                    tiemposPorEmpleado.computeIfAbsent(idEmpleado, id -> new ArrayList<>()).add(segundos);
                }
            }
        }

        List<EmpleadoRanking> ranking = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entrada : tiemposPorEmpleado.entrySet()) {
            long suma = 0;
            for (Long tiempo : entrada.getValue()) {
                suma += tiempo;
            }
            double promedio = (double) suma / entrada.getValue().size();
            ranking.add(new EmpleadoRanking(entrada.getKey(), promedio, entrada.getValue().size()));
        }
        ranking.sort(Comparator.comparingDouble(EmpleadoRanking::getPromedioSegundos));

        return new EficienciaResponse(pedidos, ranking);
    }
}