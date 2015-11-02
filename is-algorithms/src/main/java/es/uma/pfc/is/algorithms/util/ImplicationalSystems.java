package es.uma.pfc.is.algorithms.util;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utility for {@link ImplicationalSystem} objects.
 * @author Dora Calderón
 */
public class ImplicationalSystems {

    /**
     * Constructor.
     */
    private ImplicationalSystems() {
    }

    /**
     * Number implications of a system.
     * @param system Implicational System.
     * @return Implications number. {@code null} when the syste is null.
     */
    public static final Integer getCardinality(ImplicationalSystem system) {
        Integer cardinality = null;
        if (system != null) {
            cardinality = system.sizeRules();
        }
        return cardinality;
    }

    /**
     * Sum of the number of attributes of right and left side for all the implications.
     * @param system Implicational system.
     * @return The sum of the number of attributes of right and left side for all the implications.
     * <br/> {@code null} when the system is null.
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
     * Adds an implication and its elements to an implicational system.
     * @param system Implicational system.
     * @param implication Implication.
     * @return {@code true} if the implication is added.
     * @trhows NullPointerExcetpion If system is null.
     */
    public static final boolean addRuleAndElements(ImplicationalSystem system, final Rule implication) {
        boolean added = false;
        if(implication != null) {
            system.addAllElements(Rules.getElements(implication));
            added = system.addRule(implication);
        }
        return added;
    }

    /**
     * Adds an implications collection to a system.
     * @param system Implicational system.
     * @param rules Implications.
     * @return Implicational system with the new implications added.
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
     * If two systems are equals, i.e., contain the same implications.
     * @param system1 Implicational system.
     * @param system2 Implicational system.
     * @return {@code true} if the systems contains the same implications, {@code false} otherwise.
     */
    public static final boolean equals(final ImplicationalSystem system1, final ImplicationalSystem system2) {
        
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
