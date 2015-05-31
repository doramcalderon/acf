
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.algorithms.Messages;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Rules;
import es.uma.pfc.is.algorithms.util.Sets;
import static es.uma.pfc.is.algorithms.util.Sets.*;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.List;
import java.util.TreeSet;

/**
 * Direct Optimal Basis algortithm.
 *@author Dora Calderón
 */
public class DirectOptimalBasis extends GenericAlgorithm {
 

    @Override
    public String getName() {
        return "Direct Optimal Basis";
    }

    
    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        getLogger().history(Messages.get().getMessage(Messages.EXECUTING, getName()));
        
        ImplicationalSystem directOptimalBasis = null;

        if (system != null && !isEmpty(system.getRules())) {
            /* Stage 1: Generation of reduced IS*/
            
            directOptimalBasis = reduce(system);
            

            /* Stage 2: Generation of IS simplificated by simplification of reduced IS*/
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

        getLogger().history("Finish {} with return ", getName());
        getLogger().history((directOptimalBasis != null) ? directOptimalBasis.toString() : "null");
        return directOptimalBasis;
    }

     /**
     * Devuelve un sistema implicacional reducido.<br/>
     * Un sistema implicacional es reducido si para todo A -> B de sigma se cumple que la intersección de A y B es
     * vacío y B no es vacío.
     * @param system Sistema implicacional.
     * @return Sistema implicacional reducido.{@code null} si el sistema es nulo.
     */
    public ImplicationalSystem reduce(ImplicationalSystem system) {
        getLogger().history(Messages.get().getMessage(Messages.REDUCE));
        ImplicationalSystem reducedSystem = null;
        if(system != null) {
            reducedSystem = new ImplicationalSystem();
            for(Rule implication : system.getRules()) {
                Rule frEqImpl = SimplificationLogic.fragmentationEquivalency(implication);
                if(!frEqImpl.getConclusion().isEmpty()) {
                    reducedSystem = ImplicationalSystems.addRuleAndElements(reducedSystem, frEqImpl);
                }
            }
            getLogger().statistics("reduce", system.sizeRules(), reducedSystem.sizeRules());
        }
        return reducedSystem;
    }
     
    /**
     * Fase de simplificación.
     *
     * @param system Sistema implicacional.
     * @return Sistema simplificado.
     */
    protected ImplicationalSystem simplificate(ImplicationalSystem system) {
        getLogger().history("Generation of IS simplificated by simplification of reduced IS");
        ImplicationalSystem simplificatedSystem = null;
        ImplicationalSystem aux = null;
        
        if(system != null) {
            getLogger().history(system.toString());
            simplificatedSystem = new ImplicationalSystem(system);
            int size;

            do {
                size = simplificatedSystem.sizeRules();
                aux = new ImplicationalSystem(simplificatedSystem);
                
                for (Rule rule1 : aux.getRules()) {
                    for (Rule rule2 : aux.getRules()) {
                        if(!rule1.equals(rule2)) {
                            List<Rule> rulesComp = SimplificationLogic.compositionEquivalency(rule1, rule2);
                            if(rulesComp.size() == 1) {
                                removeRule(simplificatedSystem, rule1);
                                removeRule(simplificatedSystem, rule2);
                                addRule(simplificatedSystem, rulesComp.get(0));
                            } else {
                                Rule newRule = SimplificationLogic.simplificationEquivalency(rule1, rule2).get(1);
                                if(!newRule.getConclusion().isEmpty()) {
                                    replaceRule(simplificatedSystem, rule2, newRule);
                                } else {
                                    removeRule(simplificatedSystem, rule2);
                                }
                                
                            }
                            
                        }
                    }
                }
                
            } while (size != simplificatedSystem.sizeRules());
            
            
            
        }
        getLogger().statistics("simplificate", system.sizeRules(), simplificatedSystem.sizeRules());
        return simplificatedSystem;
    }
     
    /**
     * Fase de simplificación.
     *
     * @param system Sistema implicacional.
     * @return Sistema simplificado.
     */
    protected ImplicationalSystem simplificate2(ImplicationalSystem system) {
        getLogger().history("Generation of IS simplificated by simplification of reduced IS");
        ImplicationalSystem simplificatedSystem = null;
        ImplicationalSystem aux = null;
        
        if(system != null) {
            getLogger().history(system.toString());
            simplificatedSystem = new ImplicationalSystem(system);
            
            int size;
            TreeSet a, b, c, d;

            do {
                size = simplificatedSystem.sizeRules();
                aux = new ImplicationalSystem(simplificatedSystem);
                for (Rule rule1 : aux.getRules()) {
                    a = rule1.getPremise();
                    b = rule1.getConclusion();
                    for (Rule rule2 : aux.getRules()) {
                        if(!rule1.equals(rule2)) {
                            c = rule2.getPremise();
                            d = rule2.getConclusion();

                            if (c.containsAll(a)) {
                                if (union(a, b).containsAll(c)) {
                                    simplificatedSystem.removeRule(rule1);
                                    simplificatedSystem.removeRule(rule2);
                                    simplificatedSystem.addRule(new Rule(a, new TreeSet(union(b, d))));
                                } else if (b.containsAll(d)) {
                                    simplificatedSystem.removeRule(rule2);
                                } else {
                                    List<Rule> seq = SimplificationLogic.simplificationEquivalency(rule1, rule2);
                                    if(seq.get(1).getConclusion().isEmpty()) {
                                        simplificatedSystem.removeRule(rule2);
                                    } else {
                                        Rule newRule = seq.get(1);
                                        if(!rule2.equals(newRule)) {
                                            simplificatedSystem.replaceRule(rule2, newRule);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } while (size != simplificatedSystem.sizeRules());
            
            getLogger().history("Simplificated System:");
            getLogger().history(simplificatedSystem.toString());
            getLogger().statistics("simplificate", system.sizeRules(), simplificatedSystem.sizeRules());
        }
        return simplificatedSystem;
    }

    /**
     * Strong simplification
     *
     * @param system
     * @return
     */
    protected ImplicationalSystem strongSimplificate(final ImplicationalSystem system) {
        getLogger().history("Generation of IS by completion of simplifacted IS --> Strong Simplification");
        ImplicationalSystem simplSystem = null;
        
        if(system != null) {
            getLogger().history(system.toString());
            simplSystem= new ImplicationalSystem(system);
            ImplicationalSystem auxSystem;
            int size;

            do {
                size = simplSystem.sizeRules();
                auxSystem = new ImplicationalSystem(simplSystem);

                for (Rule rule1 : auxSystem.getRules()) {
                    for (Rule rule2 : auxSystem.getRules()) {
                        if(!rule1.equals(rule2)) {
                            Rule r = SimplificationLogic.strongSimplificationEq(rule1, rule2);
                            if (r != null) {
                                simplSystem = ImplicationalSystems.addRuleAndElements(simplSystem, r);
                            }
                        }
                    }
                }
                
            } while (size != simplSystem.sizeRules());
            
            getLogger().history(simplSystem.toString());
            getLogger().history("End of Strong Simplification.");
            getLogger().statistics("strongSimplificate", system.sizeRules(), simplSystem.sizeRules());
        }
        
        return simplSystem;
    }

    /**
     * Devuelve un sistema de implicaciones optimizado.<br/>
     * Para cada par de implicaciones en el sistema pasado como arguemnto:
     *      if C = A then B = B union D
     *      if C es subconjunto propio de A then B = diferencia simetrica (B, D) <br/>
     * @param system Sistema implicacional.
     * @return Sistema implicacional optimizado.
     */
    protected ImplicationalSystem optimize(final ImplicationalSystem system) {
        getLogger().history("Generation of optimized IS ");
        ImplicationalSystem optimizedSystem = null;
        
        if (system != null) {
            optimizedSystem= new ImplicationalSystem();
            TreeSet conclusion;
            for (Rule rule1 : system.getRules()) {
                conclusion = rule1.getConclusion();
                for (Rule rule2 : system.getRules()) {
                    if(!rule1.equals(rule2)) {
                        if(rule2.getPremise().equals(rule1.getPremise())) {
                            conclusion.addAll(rule2.getConclusion());
                        } else if (rule1.getPremise().containsAll(rule2.getPremise())) {
                            conclusion = Sets.symDifference(conclusion, rule2.getConclusion());
                        }
                    }
                }
                if (!conclusion.isEmpty()) {
                    optimizedSystem = ImplicationalSystems.addRuleAndElements(optimizedSystem, new Rule(rule1.getPremise(), conclusion));
                }
            }
            
            getLogger().history(system.toString());
            getLogger().statistics("optimize", system.sizeRules(), optimizedSystem.sizeRules());
            
        }
        return optimizedSystem;
    }

  

    @Override
    public String toString() {
        return getName();
    }
    
    
}
