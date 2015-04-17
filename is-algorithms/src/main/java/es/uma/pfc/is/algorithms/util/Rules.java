package es.uma.pfc.is.algorithms.util;

import fr.kbertet.lattice.Rule;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utilidades para la clase {@link Rule}.
 * @author Dora Calderón
 */
public class Rules {
    /**
     * Devuelve los elementos de una regla.
     * @param rule Regla.
     * @return Unión de los elementos de la premisa y la conlcusión de una regla.
     */
    public static TreeSet getElements(Rule rule) {
        TreeSet elements = new TreeSet();
        if(rule != null) {
            elements.addAll(rule.getConclusion());
            elements.addAll(rule.getPremise());
        }
        
        return elements;
    }
}
