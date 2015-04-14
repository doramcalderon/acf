/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.Sets;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class SimplificationLogic {

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
     * Implementa la regla de simplificación para una pareja de implicaciones.<br/>
     * [SiEq]: If (A intersection B is empty ) and (A subset of C) then <br/><br/>
     *          {A -> B, C -> D} == {A -> B, C-B -> D-B}
     * @param rule1 Implicación.
     * @param rule2 Implicación.
     * @return Implicaciones simplificadas.
     * @throws NullPointerException Si alguna de las dos reglas es nula.
     */
    public static final List<Rule> simplificationEquivalency(final Rule rule1, final Rule rule2) {
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        
        TreeSet<Comparable> a = rule1.getPremise();
        TreeSet<Comparable> b = rule1.getConclusion();
        TreeSet<Comparable> c = rule2.getPremise();
        TreeSet<Comparable> d = rule2.getConclusion();
        
        if (Sets.intersection(a, b).isEmpty() && c.containsAll(a)) {
            rules.add(new Rule( Sets.difference(c, b), Sets.difference(d, b)));
        } else {
            rules.add(rule2);
        }
        return rules;
    }

    /**
     * Si (B interseccion C) no es vacío y (D \ (A union B)) tampoco, se devuelve la nueva implicación 
     * AC - B -> D - (AB).
     * @param rule1 Implicación.
     * @param rule2 Implicación.
     * @return Devuelve una nueva implicación, si se puede aplicar Strong Simplification y {@code null} en otro caso.
     */
    public static final Rule strongSimplificationEq(Rule rule1, Rule rule2) {
        Rule newRule = null;
        TreeSet a = rule1.getPremise();
        TreeSet b = rule1.getConclusion();
        TreeSet c = rule2.getPremise();
        TreeSet d = rule2.getConclusion();
        
        if(!Sets.intersection(b, c).isEmpty()) {
            if(!Sets.symDifference(d, Sets.union(a, b)).isEmpty()) {
                newRule = new Rule();
                newRule.addAllToPremise(Sets.difference(Sets.union(a, c), b));
                newRule.addAllToConclusion(Sets.difference(d, Sets.union(a, b)));
            }
        }
        
        
        return newRule;
    }
}
