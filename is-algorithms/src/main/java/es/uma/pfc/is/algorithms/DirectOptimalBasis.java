/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import static es.uma.pfc.is.algorithms.util.ImplicationalSystems.*;
import es.uma.pfc.is.algorithms.util.Rules;
import es.uma.pfc.is.algorithms.util.Sets;
import static es.uma.pfc.is.algorithms.util.Sets.*;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.TreeSet;

/**
 *
 * @since @author Dora Calderón
 */
public class DirectOptimalBasis implements Algorithm<ImplicationalSystem, ImplicationalSystem> {

    @Override
    public String getName() {
        return "Direct Optimal Basis";
    }

    
    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        ImplicationalSystem directOptimalBasis = null;

        if (system != null && !isEmpty(system.getRules())) {
            /* Stage 1: Generation of reduced IS*/
            directOptimalBasis = reduce(system);

            /* Stage 2: Generation of IS simplificated by simplification of reducedIS*/
            directOptimalBasis = simplificate(directOptimalBasis);

            /**
             * Stage 3: Generation of IS by completion of simplifacted IS --> Strong Simplification
             */
            directOptimalBasis = strongSimplificate(directOptimalBasis);

            /**
             * Stage 4: Generation of optimized IS *
             */
            directOptimalBasis = optimize(directOptimalBasis);

        }

        return directOptimalBasis;
    }

    /**
     * Fase de simplificación.
     *
     * @param system Sistema implicacional.
     * @return Sistema simplificado.
     */
    protected ImplicationalSystem simplificate(ImplicationalSystem system) {
        ImplicationalSystem simplificatedSystem = new ImplicationalSystem(system);
        int size = system.sizeRules();
        TreeSet a, b, c, d;

        do {
            for (Rule rule1 : system.getRules()) {
                a = rule1.getPremise();
                b = rule1.getConclusion();
                for (Rule rule2 : system.getRules()) {
                    if(!rule1.equals(rule2)) {
                        c = rule2.getPremise();
                        d = rule2.getConclusion();

                        if (c.containsAll(a)) {
                            if (union(a, b).containsAll(c)) {
                                simplificatedSystem.removeRule(rule1);
                                simplificatedSystem.removeRule(rule2);
                                simplificatedSystem.addRule(new Rule(a, new TreeSet(union(b, d))));
                            } else {
                                addAllRules(simplificatedSystem, SimplificationLogic.simplificationEquivalency(rule1, rule2));
                            }
                        }
                    }
                }
            }

        } while (size != simplificatedSystem.sizeRules());
        return simplificatedSystem;
    }

    /**
     * Strong simplification
     *
     * @param system
     * @return
     */
    protected ImplicationalSystem strongSimplificate(final ImplicationalSystem system) {
        ImplicationalSystem simplSystem = new ImplicationalSystem(system);
        ImplicationalSystem auxSystem = new ImplicationalSystem();
        int size;

        do {
            size = simplSystem.sizeRules();
            auxSystem.init();
            
            for (Rule rule1 : simplSystem.getRules()) {
                for (Rule rule2 : simplSystem.getRules()) {
                    if(!rule1.equals(rule2)) {
                        Rule r = SimplificationLogic.strongSimplificationEq(rule1, rule2);
                        if (r != null) {
                            auxSystem.addAllElements(Rules.getElements(r));
                            auxSystem.addRule(r);
                        }
                    }
                }
            }
            simplSystem = ImplicationalSystems.addAllRules(simplSystem, auxSystem.getRules());
        } while (size != simplSystem.sizeRules());
        return simplSystem;
    }

    /**
     * Devuelve un sistema de implicaciones optimizado.
     * @param system Sistema implicacional.
     * @return Sistema implicacional optimizado.
     */
    protected ImplicationalSystem optimize(final ImplicationalSystem system) {
        ImplicationalSystem optimizedSystem = new ImplicationalSystem();
        Rule optimizedRule = null;
        
        for (Rule rule1 : system.getRules()) {
            for (Rule rule2 : system.getRules()) {
                if(!rule1.equals(rule2)) {
                    optimizedRule = optimize(rule1, rule2);
                }
                if (optimizedRule != null && !optimizedRule.getConclusion().isEmpty()) {
                    optimizedSystem.addAllElements(Rules.getElements(optimizedRule));
                    optimizedSystem.addRule(optimizedRule);
                }
            }
        }

        return optimizedSystem;
    }

    /**
     * Devuelve la optimización de una implicación respecto a otra aplicando que:<br/>
     *      if C = A then B = B union D<br/>
     *      if C es subconjunto propio de A then B = diferencia simetrica (B, D) <br/>
     * 
     * @param implication1 Implicación.
     * @param implication2 Implicación.
     * @return Implicación optimizada.
     */
    protected Rule optimize(Rule implication1, Rule implication2) {
        Rule optimizedRule = null;
        if (implication1 != null && implication2 != null) {
            TreeSet a = implication1.getPremise();
            TreeSet b = implication1.getConclusion();
            TreeSet c = implication2.getPremise();
            TreeSet d = implication2.getConclusion();

            if (c.equals(a)) {
                optimizedRule = new Rule(implication1.getPremise(), implication1.getConclusion());
                optimizedRule.addAllToConclusion(d);
            } else if (a.containsAll(c)) {
                TreeSet symDifference = Sets.symDifference(b, d);
                optimizedRule = new Rule(implication1.getPremise(), symDifference);
                // System.out.println("symDiference(" + b + ", " + d + "): " + symDifference);
            }
        }
        System.out.println("optimize(" + implication1 + ", " + implication2 + ") ==> " + optimizedRule);
        return optimizedRule;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
}
