package com.plazoleta.trazabilidad.service;

import com.plazoleta.trazabilidad.dto.RegistroRequest;
import com.plazoleta.trazabilidad.entity.RegistroTrazabilidad;
import com.plazoleta.trazabilidad.repository.RegistroTrazabilidadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrazabilidadService {

    private final RegistroTrazabilidadRepository registroRepository;

    public TrazabilidadService(RegistroTrazabilidadRepository registroRepository) {
        this.registroRepository = registroRepository;
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
}
