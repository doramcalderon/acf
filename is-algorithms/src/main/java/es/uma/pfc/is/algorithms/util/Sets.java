/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms.util;

import java.util.Set;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Sets {

    /**
     * Intersección de dos conjuntos.
     * @param set1 Conjunto.
     * @param set2 Conjunto.
     * @return Intersección de dos conjuntos.<br/>
     * {@code null} si alguno de los dos conjuntos es nulo.
     */
    public static final Set intersection(Set set1, Set set2) {
        Set intersection = null;
        if (set1 != null && set2 != null) {
            intersection = set1;
            intersection.retainAll(set2);
        }
        return intersection;
    }
    /**
     * Diferencia de dos conjuntos.
     * @param <T> Tipo de conjunto.
     * @param set1 Conjunto.
     * @param set2 Conjunto.
     * @return Diferencia de dos conjuntos.<br/>
     * {@code null} si alguno de los dos conjuntos es nulo.
     */
    public static final <T extends Set>  T difference(T set1, T set2) {
        T difference = null;
        if (set1 != null && set2 != null) {
            difference = set1;
            difference.removeAll(set2);
        }
        return difference;
    }
}
