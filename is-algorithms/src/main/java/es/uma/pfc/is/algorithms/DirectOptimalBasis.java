/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms;

import static es.uma.pfc.is.algorithms.util.ImplicationalSystems.*;
import es.uma.pfc.is.algorithms.util.Sets;
import static es.uma.pfc.is.algorithms.util.Sets.*;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.TreeSet;


/**
 *
 * @since 
 * @author Dora Calderón
 */
public class DirectOptimalBasis {


    public ImplicationalSystem execute(ImplicationalSystem system) {
        ImplicationalSystem directOptimalBasis = null;
        
        if (system != null && !isEmpty(system.getRules())) {
            /* Stage 1: Generation of reduced IS*/
            directOptimalBasis = reduce(system);
            
            /* Stage 2: Generation of IS simplificated by simplification of reducedIS*/
            directOptimalBasis = simplificate(directOptimalBasis);
            
            /** Stage 3: Generation of IS by completion of simplifacted IS --> Strong Simplification*/
            directOptimalBasis = strongSimplificate(directOptimalBasis);
            
            /** Stage 4: Generation of optimized IS **/
            directOptimalBasis = optimize(directOptimalBasis);
            
            
        }
        
        
        
        
        return directOptimalBasis;
    }
    
    /**
     * Fase de simplificación.
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
                    c = rule2.getPremise();
                    d = rule2.getConclusion();

                    if (c.containsAll(a)) {
                        if(union(a, b).containsAll(c)) {
                            simplificatedSystem.removeRule(rule1);
                            simplificatedSystem.removeRule(rule2);
                            simplificatedSystem.addRule(new Rule(a, new TreeSet(union(b, d))));
                        } else {
                            addAllRules(simplificatedSystem, SimplificationLogic.simplificationEquivalency(rule1, rule2));
                        }
                    }
                }
            }

        } while(size != simplificatedSystem.sizeRules());
        return simplificatedSystem;
    }
    
    /**
     * Strong simplification
     * @param system
     * @return 
     */
    protected ImplicationalSystem strongSimplificate(ImplicationalSystem system) {
        ImplicationalSystem newSystem = new ImplicationalSystem(system);
        int size;
        
        ImplicationalSystem copyOfSystem;
        ImplicationalSystem copyOfSystem2;
        do {
            size = newSystem.sizeRules();
            copyOfSystem = new ImplicationalSystem(newSystem);
            copyOfSystem2 = new ImplicationalSystem(newSystem);
            
            for(Rule rule1 : copyOfSystem.getRules()) {
                for(Rule rule2 : copyOfSystem2.getRules()) {
                    Rule r = SimplificationLogic.strongSimplificationEq(rule1, rule2);
                    if(r != null) {
                        newSystem.addRule(r);
                    }
                }
            }
        } while (size != newSystem.sizeRules());
        return newSystem;
    }
    
    protected ImplicationalSystem optimize(ImplicationalSystem system) {
        ImplicationalSystem optimizedSystem = new ImplicationalSystem();
        TreeSet<Comparable> a, b, c, d;
        
        for(Rule rule1 : system.getRules()) {
            a = rule1.getPremise();
            b = rule1.getConclusion();
            
            for(Rule rule2 : system.getRules()) {
                c = rule2.getPremise();
                d = rule2.getConclusion();
                
                if(c.equals(a)) {
                    b = Sets.union(b, d);
                }
                if(!a.containsAll(c)) {
                    b = Sets.symDifference(b, d);
                }
            }
            if(!b.isEmpty()) {
                Rule r = new Rule();
                r.addAllToPremise(a);
                r.addAllToConclusion(b);
            }
        }
        
        return optimizedSystem;
    }
}
