/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms.util;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Sets {
    /**
     * Unión de dos conjuntos.
     * @param <T> Tipo de los conjuntos.
     * @param set1 Conjunto.
     * @param set2 Conjunto.
     * @return Conjunto con los elementos de los dos conjuntos pasados como parámetro. Si uno de los dos es nulo,
     * se devuelve el otro.
     */
    public static final TreeSet  union(final Set set1, final Set set2) {
        TreeSet union;
        if(set1 == null && set2 == null) {
            union = null;
        } else if(set1 == null) {
            union = new TreeSet(set2);
        } else if (set2 == null) {
            union = new TreeSet(set1);
        } else {
            union = new TreeSet(set1);
            union.addAll(set2);
        }
        
        return union;
    }

    /**
     * Intersección de dos conjuntos.
     * @param set1 Conjunto.
     * @param set2 Conjunto.
     * @return Intersección de dos conjuntos.<br/>
     * {@code null} si alguno de los dos conjuntos es nulo.
     */
    public static final TreeSet intersection(final Set set1, final Set set2) {
        TreeSet intersection = null;
        if (set1 != null && set2 != null) {
            intersection = new TreeSet(set1);
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
    public static final TreeSet difference(final Set set1, final Set set2) {
        TreeSet difference = null;
        if (set1 != null && set2 != null) {
            difference = new TreeSet(set1);
            difference.removeAll(set2);
        }
        return difference;
    }
    
    /**
     * A \ B = (A - B) union (B - A)
     * @param <T>
     * @param set1
     * @param set2
     * @return 
     */
//    public static final  TreeSet symDifference(final Set set1, final Set set2) {
//        return union(difference(set1, set2), difference(set2, set1));
//    }
    
    /**
     * Devuelve si un conjunto es vacío.
     * @param set Conjunto.
     * @return {@code true} si el conjunto es nulo o vacío, {@code false} en otro caso.
     */
    public static final boolean isEmpty(final Set set) {
        return (set == null || set.isEmpty());
    }
}
