package com.plazoleta.trazabilidad.algoritmo;

import com.plazoleta.trazabilidad.dto.EmpleadoRanking;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrdenamientoPorInsercionTest {

    @Test
    void ordenaDeMenorAMayorPromedio() {
        List<EmpleadoRanking> ranking = new ArrayList<>();
        ranking.add(new EmpleadoRanking(1L, 300.0, 2));
        ranking.add(new EmpleadoRanking(2L, 100.0, 5));
        ranking.add(new EmpleadoRanking(3L, 200.0, 1));

        OrdenamientoPorInsercion.ordenar(ranking);

        assertEquals(2L, ranking.get(0).getIdEmpleado());
        assertEquals(3L, ranking.get(1).getIdEmpleado());
        assertEquals(1L, ranking.get(2).getIdEmpleado());
    }

    @Test
    void conUnSoloElementoNoCambiaNada() {
        List<EmpleadoRanking> ranking = new ArrayList<>();
        ranking.add(new EmpleadoRanking(1L, 50.0, 1));

        OrdenamientoPorInsercion.ordenar(ranking);

        assertEquals(1, ranking.size());
        assertEquals(1L, ranking.get(0).getIdEmpleado());
    }

    @Test
    void conListaVaciaNoFalla() {
        List<EmpleadoRanking> ranking = new ArrayList<>();

        OrdenamientoPorInsercion.ordenar(ranking);

        assertTrue(ranking.isEmpty());
    }
}