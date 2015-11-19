/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgMessages;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Sets;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class SimplificationLogic {
    /**
     * Gets a reduced system.<br/>
     * An implicational system is reducede, if A -> B => (B not empty) AND (A intersection B is empty) for all A, B in S.
     * @param system Implicational system.
     * @param logger Logger.
     * @return Reduced IS.
     */
    public static ImplicationalSystem reduce(ImplicationalSystem system, AlgorithmLogger logger) {
        logger.history("**************************************************************************************");
        logger.history(AlgMessages.get().getMessage(AlgMessages.REDUCE));
        logger.history("**************************************************************************************");
        system.makeProper(); // TODO se puede implementar con lambda expressions
        
        
        logger.history("\n" + String.valueOf(system));
        return system;
    }
     /**
     * Generation of IS by completion of simplifacted IS --> Strong Simplification.
     * @param system Simplified system.
     * @param logger
     * @return Strong simplified system.
     * @throws NullPointerException if system is null.
     */
    public static ImplicationalSystem strongSimplification(ImplicationalSystem system, AlgorithmLogger logger) {
        logger.history("**************************************************************************************");
        logger.history("Generation of IS by completion of simplifacted IS --> Strong Simplification");
        logger.history("**************************************************************************************");
        
        logger.history("\n" + String.valueOf(system));
        
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem simplified = new ImplicationalSystem(system);
        ImplicationalSystem save;
        boolean changed;
        Rule newRule;
        Set unionAB;
        
        do {
            changed = false;
            save = new ImplicationalSystem(simplified);
            for (Rule ab : save.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();
                unionAB = Sets.union(a, b);

                for (Rule cd : save.getRules()) {
                    c = cd.getPremise();
                    d = cd.getConclusion();
                          
                    if (!Sets.intersection(b, c).isEmpty() && !Sets.difference(d, unionAB).isEmpty()) {
                        newRule = new Rule(Sets.difference(Sets.union(a, c), b), Sets.difference(d, unionAB));
                        changed = ImplicationalSystems.addRuleAndElements(simplified, newRule) || changed;
                        logger.history("({}) + ({})  --->  ({})", ab, cd, newRule);
//                        System.out.println("("+ab+") + ("+cd+")  --->  ("+newRule+")");
                    }
                }
            }
//            System.out.println("changed: " + changed);
//            System.out.println("Antiguo: " + save);
//            System.out.println("Nuevo: " + simplified);
//            System.out.println("------------------------------------------------");
        } while(changed);
        
        logger.history("\n" + simplified.toString());
        return simplified;
    }
    
      /**
     * Composition of implications of a system.
     * @param system Implicational System.
     * @return Implicational System with composition applied.
     */
    public static ImplicationalSystem composition(ImplicationalSystem system, AlgorithmLogger logger) {
        logger.history("**************************************************************************************");
        logger.history("Composition of IS");
        logger.history("**************************************************************************************");
        system.makeCompact();
        logger.history("\n" + system.toString());
        return system;
    }

    /**
     * Generation of optimized IS.
     * @param system Simplified Implicational System.
     * @return Optimized system.
     */
    public static ImplicationalSystem optimize(ImplicationalSystem system, AlgorithmLogger logger) {
        logger.history("**************************************************************************************");
        logger.history("Generation of optimized IS ");
        logger.history("**************************************************************************************");
        logger.history("\n" + system.toString());
        
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem optimized =new ImplicationalSystem();
        ImplicationalSystem save =new ImplicationalSystem(system);

        boolean changed;
        
        changed = false;
        for (Rule ab : save.getRules()) {
            a = ab.getPremise();
            b = new TreeSet<>(ab.getConclusion());

            for (Rule cd : save.getRules()) {
                if (!ab.equals(cd)) {
                    c = cd.getPremise();
                    d = cd.getConclusion();

                    if (c.equals(a)) {
                        b.addAll(d);
                        logger.history("({}) + ({}):  C = A ==> B = B U D = {}", ab, cd, b);
                    } else if (a.containsAll(c)) {
                        b = Sets.difference(b, d);
                        logger.history("({}) + ({}): C subcjto A ==> B = B-D = {}", ab, cd, b);
//                        if(b.isEmpty()) {
//                            break;
//                        }
                    }
                }
            }
            Rule newAB = new Rule(a, b);
            if (!b.isEmpty()) {
                boolean added = ImplicationalSystems.addRuleAndElements(optimized, newAB);
                changed = changed || added;
                logger.history("B not empty ==> add({})", newAB);
            } else {
                logger.history("B empty ==> not added({})", newAB);
                
            }
        }
            
            save = new ImplicationalSystem(optimized);
            logger.history("\n" + optimized.toString());
        return optimized;
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
    
    public static final ImplicationalSystem compositionEquivalency(ImplicationalSystem is, Rule rule1, Rule rule2) {
        if(is.containsRule(rule1) && is.containsRule(rule2)) {
            if (rule1.getPremise().equals(rule2.getPremise())) {
                Rule compositionRule = new Rule(rule1.getPremise(), new ComparableSet());
                compositionRule.addAllToConclusion(rule1.getConclusion());
                compositionRule.addAllToConclusion(rule2.getConclusion());

                is.removeRule(rule1);
                is.removeRule(rule2);
                is.addRule(compositionRule);
            }
        }
        return is;
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
    public static final List<Rule> rightSimplificationEq(final Rule rule1, final Rule rule2) {
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
            if(!Sets.difference(d, Sets.union(a, b)).isEmpty()) {
                TreeSet conclusion = Sets.difference(d, Sets.union(a, b));
                if (!conclusion.isEmpty()) {
                    newRule = new Rule();
                    newRule.addAllToPremise(Sets.difference(Sets.union(a, c), b));
                    newRule.addAllToConclusion(conclusion);
                }
            }
        }
        
        
        return newRule;
    }
}
