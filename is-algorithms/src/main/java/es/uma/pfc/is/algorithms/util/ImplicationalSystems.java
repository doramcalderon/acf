package es.uma.pfc.is.algorithms.util;

import es.uma.pfc.is.algorithms.optbasis.SimplificationLogic;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Clase de utilidades para objetos de tipo {@link ImplicationalSystem}.
 *
 * @since 1.0.0
 * @author Dora Calderón
 */
public class ImplicationalSystems {

    /**
     * Constructor.
     */
    private ImplicationalSystems() {
    }

    /**
     * Número de implicaciones de un sistema.
     *
     * @param system Sistema implicacional.
     * @return Número de implicaciones. {@code null} cuando el sistema es nulo.
     */
    public static final Integer getCardinality(ImplicationalSystem system) {
        Integer cardinality = null;
        if (system != null) {
            cardinality = system.sizeRules();
        }
        return cardinality;
    }

    /**
     * Suma del número de atributos que hay a la derecha más los que hay a la izquierda para todas las implicaciones.
     *
     * @param system Sistema implicacional.
     * @return la suma del número de atributos que hay a la derecha más los que hay a la izquierda para todas las
     * implicaciones.<br/> {@code null} cuando el sistema es nulo.
     */
    public static final Integer getSize(ImplicationalSystem system) {
        Integer size = null;

        if (system != null) {
            size = 0;
            for (Rule implication : system.getRules()) {
                size += implication.getPremise().size() + implication.getConclusion().size();
            }
        }
        return size;
    }

    /**
     * Añade una regla y sus elementos a un sistema.
     * @param system Sistema implicacional.
     * @param rule Regla.
     * @return Sistema implicacional con la nueva regla.
     */
    public static final ImplicationalSystem addRuleAndElements(final ImplicationalSystem system, final Rule rule) {
        ImplicationalSystem newSystem = (system == null) ? new ImplicationalSystem() : new ImplicationalSystem(system);
        
        if(rule != null) {
            newSystem.addAllElements(Rules.getElements(rule));
            newSystem.addRule(rule);
        }
        return newSystem;
    }
    /**
     * Añade una colección de implicaciones a un sistema.
     *
     * @param system Sistema implicacional.
     * @param rules Implicaciones.
     * @return Sistema implicacional con las nuevas implicaciones añadidas.
     */
    public static final ImplicationalSystem addAllRules(final ImplicationalSystem system, final Collection<Rule> rules) {
        ImplicationalSystem newSystem = (system == null) ? new ImplicationalSystem() : new ImplicationalSystem(system);
        if (rules != null) {
            rules.stream().forEach((rule) -> {
                newSystem.addAllElements(Rules.getElements(rule));
                newSystem.addRule(rule);
            });
        }
        return newSystem;
    }


    
    /**
     * Si dos sistemas son dos iguales, es decir, contienen las mismas implicaciones.
     * @param system1 Sistema implicacional.
     * @param system2 Sistema implicacional.
     * @return {@code true} si los dos sistemas contienen las mismas implicaciones, {@code false} en otro caso.
     */
    public static final boolean equals(ImplicationalSystem system1, ImplicationalSystem system2) {
        
        if (system1 == null && system2 == null) {
            return true;
        }
        if (system1 != null && system2 != null) {
            boolean equals = false;
            if (getCardinality(system1).equals(getCardinality(system2))) {
                equals = true;
                Iterator<Rule> iterator = system1.getRules().iterator();
                while(iterator.hasNext() && equals) {
                    Rule r = iterator.next();
                    equals = system2.containsRule(r);
                }
            } 
            return equals;
        } else {
            return false;
        }
    }
}
