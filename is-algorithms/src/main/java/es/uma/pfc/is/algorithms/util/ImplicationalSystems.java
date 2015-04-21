package es.uma.pfc.is.algorithms.util;

import es.uma.pfc.is.algorithms.SimplificationLogic;
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
     * Para cada par de implicaciones, si las premisas son iguales se puede sustituir por una implicación en la que la
     * premisa es la misma y la conclusión es la unión de las dos conclusiones. Es decir:<br/>
     * {A -> B, A -> C} => {A -> BC}
     *
     * @param system Sistema implicacional.
     * @return Sistema implicacional resultante de aplicar la regla de composición al sistema pasado como parámetro.
     */
//    public static final ImplicationalSystem simplification(ImplicationalSystem system) {
//        ImplicationalSystem systemSimplificated = null;
//
//        if (system != null) {
//            ImplicationalSystem copyOfSystem = new ImplicationalSystem(system);
//            systemSimplificated = new ImplicationalSystem(system);
//
//            int size;
//            boolean finished = false;
//            while (!finished) {
//                size = systemSimplificated.sizeRules();
//
//                for (Rule implication1 : system.getRules()) {
//                    for (Rule implication2 : copyOfSystem.getRules()) {
//                        if (!implication1.equals(implication2)) {
//                            List<Rule> rulesComposition = SimplificationLogic.compositionEquivalency(implication1, implication2);
//                            if (rulesComposition.size() == 1) {
//                                systemSimplificated.removeRule(implication1);
//                                systemSimplificated.removeRule(implication2);
//                                systemSimplificated.addRule(rulesComposition.get(0));
//                            } else if (implication2.getConclusion().containsAll(implication1.getConclusion())){
//                                systemSimplificated.removeRule(implication2);
//                            } else {
//                                systemSimplificated.removeRule(implication2);
//                                for(Rule r : SimplificationLogic.simplificationEquivalency(implication1, implication2)) {
//                                    systemSimplificated.addRule(r);
//                                }
//                                
//                            }
//                        }
//                    }
//                }
//
//                finished = size == systemSimplificated.sizeRules();
//            }
//        }
//        return systemSimplificated;
//    }


    
    /**
     * Devuelve un sistema implicacional reducido.<br/>
     * Un sistema implicacional es reducido si para todo A -> B de sigma se cumple que la intersección de A y B es
     * vacío y B no es vacío.
     * @param system Sistema implicacional.
     * @return Sistema implicacional reducido.{@code null} si el sistema es nulo.
     */
    public static final ImplicationalSystem reduce(ImplicationalSystem system) {
        ImplicationalSystem reducedSystem = null;

        if (system != null) {
            reducedSystem = new ImplicationalSystem(system);
            for (Rule r : system.getRules()) {
                Rule frEqRule = SimplificationLogic.fragmentationEquivalency(r);
                TreeSet a = frEqRule.getPremise();
                TreeSet b = frEqRule.getConclusion();

                if (a.containsAll(b)) {
                    reducedSystem.removeRule(r);
                } else {
                    reducedSystem.replaceRule(r, frEqRule);
                }
            }
        }

        return reducedSystem;
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
                    equals = system2.containsRule(iterator.next());
                }
            } 
            return equals;
        } else {
            return false;
        }
    }
}
