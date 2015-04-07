package es.uma.pfc.is.algorithms.util;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
     * Añade una colección de implicaciones a un sistema.
     *
     * @param system Sistema implicacional.
     * @param rules Implicaciones.
     * @return Sistema implicacional con las nuevas implicaciones añadidas.
     */
    protected static final ImplicationalSystem addAllRules(ImplicationalSystem system, Collection<Rule> rules) {
        if (rules != null) {
            rules.stream().forEach((rule) -> {
                system.addRule(rule);
            });
        }
        return system;
    }

    /**
     * Implementa la regla Fragmentation Equivalency:<br/>
     * [FrEq]: {A -> B} = {A -> B-A}.
     *
     * @param rule Implicación.
     * @return Implicación equivalente simplificada.
     */
    public static Rule fragmentationEquivalency(Rule rule) {
        Rule eqRule = rule;
        if (rule != null) {
            eqRule.getConclusion().removeAll(eqRule.getPremise());
        }
        return eqRule;
    }

    /**
     * Para cada par de implicaciones, si las premisas son iguales se puede sustituir por una implicación en la que la
     * premisa es la misma y la conclusión es la unión de las dos conclusiones. Es decir:<br/>
     * {A -> B, A -> C} => {A -> BC}
     *
     * @param system Sistema implicacional.
     * @return Sistema implicacional resultante de aplicar la regla de composición al sistema pasado como parámetro.
     */
    public static final ImplicationalSystem compositionEquivalency(ImplicationalSystem system) {
        ImplicationalSystem compositionSystem = null;

        if (system != null) {
            ImplicationalSystem copyOfSystem = new ImplicationalSystem(system);
            compositionSystem = new ImplicationalSystem(system);

            int size;
            boolean finished = false;
            while (!finished) {
                size = compositionSystem.sizeRules();

                for (Rule implication : system.getRules()) {
                    for (Rule implication2 : copyOfSystem.getRules()) {
                        if (!implication.equals(implication2)) {
                            List<Rule> rulesComposition = compositionEquivalency(implication, implication2);
                            if (rulesComposition.size() == 1) {
                                compositionSystem.removeRule(implication);
                                compositionSystem.removeRule(implication2);
                                compositionSystem.addRule(rulesComposition.get(0));
                            }
                        }
                    }
                }

                finished = size == compositionSystem.sizeRules();
            }
        }
        return compositionSystem;
    }

    /**
     * Para dos implicaciones:<br/>
     * {A -> B, A -> C} => {A -> BC}
     *
     * @param rule1
     * @param rule2
     * @return Si se puede aplicar la regla de composición devuelve una lista con una única implicación resultante de la
     * aplicación de la regla.<br/>
     * Si no se puede aplicar la regla de composición, devuelve una lista con las dos reglas pasadas como parámetro.
     * @throws NullPointerException Si alguna de las implicaciones es nula.
     */
    public static final List<Rule> compositionEquivalency(Rule rule1, Rule rule2) {
        List<Rule> compositionRules = Arrays.asList(rule1, rule2);

        if (rule1.getPremise().equals(rule2.getPremise())) {
            compositionRules = new ArrayList();
            Rule compositionRule = new Rule(rule1.getPremise(), new ComparableSet());
            compositionRule.addAllToConclusion(rule1.getConclusion());
            compositionRule.addAllToConclusion(rule2.getConclusion());
            compositionRules.add(compositionRule);
        }

        return compositionRules;
    }

    /**
     * Implemente la regla de simplificación para una pareja de implicaciones.<br/>
     * [SiEq]: If (A intersection B is empty ) and (A subset of C) then <br/><br/>
     *          {A -> B, C -> D} == {A -> B, C-B -> D-B}
     * @param rule1 Implicación.
     * @param rule2 Implicación.
     * @return Implicaciones simplificadas.
     * @throws NullPointerException Si alguna de las dos reglas es nula.
     */
    public static final List<Rule> simplificationEquivalency(Rule rule1, Rule rule2) {
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        
        TreeSet<Comparable> a = rule1.getPremise();
        TreeSet<Comparable> b = rule1.getConclusion();
        TreeSet<Comparable> c = rule2.getPremise();
        TreeSet<Comparable> d = rule2.getConclusion();
        
        if(Sets.intersection(a, b).isEmpty() && c.containsAll(a)) {
            c = Sets.difference(c, b);
            d = Sets.difference(d, b);
            rules.add(new Rule(c, d));
        } else {
            
        }
        
        return rules;
    }
}
