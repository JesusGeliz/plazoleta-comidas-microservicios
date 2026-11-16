package com.plazoleta.trazabilidad.algoritmo;

import com.plazoleta.trazabilidad.dto.EmpleadoRanking;

import java.util.List;

public class OrdenamientoPorInsercion {

    public static void ordenar(List<EmpleadoRanking> ranking) {
        for (int i = 1; i < ranking.size(); i++) {
            EmpleadoRanking actual = ranking.get(i);
            int j = i - 1;

            while (j >= 0 && ranking.get(j).getPromedioSegundos() > actual.getPromedioSegundos()) {
                ranking.set(j + 1, ranking.get(j));
                j--;
            }

            ranking.set(j + 1, actual);
        }
    }
}