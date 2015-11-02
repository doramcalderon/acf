package es.uma.pfc.is.algorithms.util;

import fr.kbertet.lattice.Rule;
import java.util.TreeSet;

/**
 * Utilities for class {@link Rule}.
 * @author Dora Calderón
 */
public class Rules {
    /**
     * Devuelve los elementos de una regla.
     * Returns an elements of a Rule.
     * @param rule Rule.
     * @return Union of premise and conclusion elements of a rule.
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
