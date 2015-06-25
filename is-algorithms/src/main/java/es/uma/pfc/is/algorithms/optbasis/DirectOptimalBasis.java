
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.algorithms.AlgMessages;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Sets;
import static es.uma.pfc.is.algorithms.util.Sets.*;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Direct Optimal Basis algortithm.
 *@author Dora Calderón
 */
public class DirectOptimalBasis extends GenericAlgorithm {

    public DirectOptimalBasis() {
        setName("Direct Optimal Basis");
    }
 

    
    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        getLogger().history(messages.getMessage(AlgMessages.EXECUTING, getName()));
        printInit(system);
        
        ImplicationalSystem directOptimalBasis = null;

        if (system != null && !isEmpty(system.getRules())) {
            /* Stage 1: Generation of reduced IS*/
            long ini = System.currentTimeMillis();
            directOptimalBasis = reduce(system);
            getLogger().history("Reduce: {} ms", System.currentTimeMillis() - ini);
            

            /* Stage 2: Generation of IS simplificated by simplification of reduced IS*/
            ini = System.currentTimeMillis();
            directOptimalBasis = simplify(directOptimalBasis);
            getLogger().history("simplify: {} ms", System.currentTimeMillis() - ini);
            
            /**
             * Stage 3: Generation of IS by completion of simplifacted IS --> Strong Simplification
             */
            ini = System.currentTimeMillis();
            directOptimalBasis = strongSimplificate(directOptimalBasis);
            getLogger().history("strong simp.: {} ms", System.currentTimeMillis() - ini);
            
            /**
             * Stage 4: Generation of optimized IS *
             */
            ini = System.currentTimeMillis();
            directOptimalBasis = optimize(directOptimalBasis);
            getLogger().history("optimize: {} ms", System.currentTimeMillis() - ini);

        }

        printResult(directOptimalBasis);
        return directOptimalBasis;
    }
    /**
     * Prints de init arguments.
     * @param inputSystem Implicactional System.
     */
    protected void printInit(ImplicationalSystem inputSystem) {
        getLogger().history("------------- INPUT ------------------------------------");
        getLogger().history(String.valueOf(inputSystem));
        getLogger().history("\n\n");
    }
    
    /**
     * Prints the results.
     * @param resultSystem Implicational System. 
     */
    protected void printResult(ImplicationalSystem resultSystem) {
        int sizeRules = 0;
        int sizeSystem = 0;
        if(resultSystem != null) {
            sizeRules = resultSystem.sizeRules();
            sizeSystem = ImplicationalSystems.getSize(resultSystem);
        }
        
        
        getLogger().history("**************************************************************************************");
        getLogger().history("DOBasis generated");
        getLogger().history("**************************************************************************************");
        getLogger().history(String.valueOf(resultSystem));
        getLogger().history("------------- COMPARISONS --------------------");
        getLogger().history("{} ** Implications", sizeRules);
        getLogger().history("{} the size of DOBasis", sizeSystem);
        
        
    }

     /**
     * Devuelve un sistema implicacional reducido.<br/>
     * Un sistema implicacional es reducido si para todo A -> B de sigma se cumple que la intersección de A y B es
     * vacío y B no es vacío.
     * @param system Sistema implicacional.
     * @return Sistema implicacional reducido.{@code null} si el sistema es nulo.
     */
    public ImplicationalSystem reduce(ImplicationalSystem system) {
        getLogger().history("**************************************************************************************");
        getLogger().history(messages.getMessage(AlgMessages.REDUCE));
        getLogger().history("**************************************************************************************");
//        getLogger().history(messages.getMessage(AlgMessages.START_OF));
        
        ImplicationalSystem reducedSystem = null;
        if(system != null) {
            reducedSystem = new ImplicationalSystem();
            for(Rule implication : system.getRules()) {
                Rule frEqImpl = SimplificationLogic.fragmentationEquivalency(implication);
                if(!frEqImpl.getConclusion().isEmpty()) {
                    reducedSystem = addRuleAndElements(reducedSystem, frEqImpl);
                }
            }
            getLogger().statistics("reduce", system.sizeRules(), reducedSystem.sizeRules());
            getLogger().history("\n" + String.valueOf(reducedSystem));
        }
        return reducedSystem;
    }
    
    protected ImplicationalSystem simplify(ImplicationalSystem system) {
        getLogger().history("**************************************************************************************");
        getLogger().history("Generation of IS simplificated by simplification of reduced IS");
        getLogger().history("**************************************************************************************");
        
        ImplicationalSystem simplificated;
        ImplicationalSystem gamma = new ImplicationalSystem(system);
        SortedSet<Rule> simpRules = new TreeSet();
        TreeSet a, b, c, d;
       
        do {
            simplificated = new ImplicationalSystem(gamma);
            gamma = new ImplicationalSystem();
            getLogger().history("--------------------------------------------------------------------------------------");
            getLogger().history("Initial Simplificated: {}", simplificated.toString());
            
            for(Rule ab : simplificated.getRules()) {
                simpRules.clear();
                a = ab.getPremise();
                b = ab.getConclusion();
                
                getLogger().history("--------------------------------------------------------------------------------------");
                
                
                getLogger().history("Gamma: {}", (gamma.sizeRules() == 0) ? "{}" : gamma.toString());
                
                for(Rule cd : gamma.getRules()) {
                    Rule abAux = new Rule(a, b);
                    if(!abAux.equals(cd)) {
                        c = cd.getPremise();
                        d = cd.getConclusion();

                        if((a.containsAll(c) && (Sets.union(c, d).containsAll(a)))
                                || c.containsAll(a) && Sets.union(a, b).containsAll(c)) {
                            a = Sets.intersection(a, c);
                            b = Sets.union(b, d);
                            getLogger().history("({}) + ({}) ==> A = A intersec C, B = B U D ==> {} -> {}", abAux, cd, a, b);
                        } else {
                            if(a.containsAll(c)) {
                                a = Sets.difference(a, d);
                                b = Sets.difference(b, d);
                                simpRules.add(cd);
                                getLogger().history("({}) + ({}) ==> A = A-D, B = B-D ==> {} -> {}", abAux, cd, a, b);
                                getLogger().history("                                 add C -> D to gamma ==> add {}", cd);
                            } else if(c.containsAll(a)) {
                                if(!b.containsAll(d)) {
                                    Rule newRule = new Rule(Sets.difference(c, b), Sets.difference(d, b)); 
                                    simpRules.add(newRule);
                                    getLogger().history("({}) + ({}) ==> C-B -> D-B ==> {}", abAux, cd, newRule);
                                }
                            } else {
                                simpRules.add(cd);
                                getLogger().history("({}) + ({}) ==> add C -> D ==> {}", abAux, cd, cd);
                            }
                        }
                    }
                }
                if (!b.isEmpty()) {
                    Rule newRule = new Rule(a, b);
                    simpRules.add(newRule);
                    getLogger().history("{} (B) not empty ==> add A -> B ==> {}", b, newRule);
                }
                gamma = new ImplicationalSystem(simpRules);
            }
        } while(!ImplicationalSystems.equals(simplificated, gamma));
        
        getLogger().history("");
        getLogger().history(String.valueOf(simplificated));
        getLogger().statistics("simplificate", system.sizeRules(), simplificated.sizeRules());
        
        return simplificated;
    }
     
    /**
     * Fase de simplificación.
     *
     * @param system Sistema implicacional.
     * @return Sistema simplificado.
     */
    protected ImplicationalSystem simplificate(ImplicationalSystem system) {
        getLogger().history("**************************************************************************************");
        getLogger().history("Generation of IS simplificated by simplification of reduced IS");
        getLogger().history("**************************************************************************************");

        
        ImplicationalSystem simplificatedSystem = null;
        ImplicationalSystem aux = null;
        
        if(system != null) {
            simplificatedSystem = new ImplicationalSystem(system);
            int size;

            do {
                size = simplificatedSystem.sizeRules();
                aux = new ImplicationalSystem(simplificatedSystem);
                
                for (Rule rule1 : aux.getRules()) {
                    for (Rule rule2 : aux.getRules()) {
                        if(!rule1.equals(rule2)) {
                            getLogger().history("SeQ({}, {}):", rule1, rule2);
                            List<Rule> rulesComp = SimplificationLogic.compositionEquivalency(rule1, rule2);
                            if(rulesComp.size() == 1) {
                                removeRule(simplificatedSystem, rule1);
                                removeRule(simplificatedSystem, rule2);
                                addRule(simplificatedSystem, rulesComp.get(0));
                            } else {
                                Rule newRule = SimplificationLogic.rightSimplificationEq(rule1, rule2).get(1);
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
            
            
            getLogger().history("");
            getLogger().history(String.valueOf(simplificatedSystem.toString()));
            getLogger().statistics("simplificate", system.sizeRules(), simplificatedSystem.sizeRules());
        }
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
                                    List<Rule> seq = SimplificationLogic.rightSimplificationEq(rule1, rule2);
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
        getLogger().history("**************************************************************************************");
        getLogger().history("Generation of IS by completion of simplifacted IS --> Strong Simplification");
        getLogger().history("**************************************************************************************");
//        getLogger().history(messages.getMessage(AlgMessages.START_OF));
        
        ImplicationalSystem simplSystem = null;
        
        if(system != null) {
            simplSystem= new ImplicationalSystem(system);
            ImplicationalSystem auxSystem;
            
            
            int size;
            Set implicationElements;
            do {
                auxSystem = new ImplicationalSystem(simplSystem);
                size = auxSystem.sizeRules();
                for (Rule rule1 : auxSystem.getRules()) {
                    // if rule elements contains all elemeents of system, it doesn't generate any new rule
                    implicationElements = Sets.union(rule1.getPremise(), rule1.getConclusion());
                    
                    if(!implicationElements.containsAll(auxSystem.getSet())) {
                        for (Rule rule2 : auxSystem.getRules()) {
                            if (!rule1.equals(rule2)) {
                                Rule r = SimplificationLogic.strongSimplificationEq(rule1, rule2);
                                if (r != null) {
                                    getLogger().history("({}) + ({})  --->  ({})", rule1, rule2, r);
                                    simplSystem = addRuleAndElements(simplSystem, r, false);
                                }
                            }
                        }
                    }
                }
            } while(size != simplSystem.sizeRules());


            
            getLogger().history("");
            getLogger().history(simplSystem.toString());
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
        getLogger().history("**************************************************************************************");
        getLogger().history("Generation of optimized IS ");
        getLogger().history("**************************************************************************************");
        
        ImplicationalSystem optimizedSystem = null;
        
        if (system != null) {
            optimizedSystem= new ImplicationalSystem();
            TreeSet a, b;
            for (Rule ab : system.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();
                for (Rule cd : system.getRules()) {
                    Rule abAux = new Rule(a, b);
                    
                    if(!abAux.equals(cd)) {
                        if(cd.getPremise().equals(a)) {
                            b.addAll(cd.getConclusion());
                            
                            getLogger().history("({}) + ({}):  C = A ==> B = B U D = {}", abAux, cd, b);
                        } else if (a.containsAll(cd.getPremise())) {
                            b = Sets.difference(b, cd.getConclusion());
                            
                            getLogger().history("({}) + ({}): C subcjto A ==> B = B-D = {}", abAux, cd, b);
                        }
                    }
                }
                if (!b.isEmpty()) {
                    Rule r = new Rule(a, b);
                    optimizedSystem = addRuleAndElements(optimizedSystem, r);
                }
            }
            getLogger().history("");
            getLogger().history(optimizedSystem.toString());
            getLogger().statistics("optimize", system.sizeRules(), optimizedSystem.sizeRules());
            
        }
        return optimizedSystem;
    }

  
    
    @Override
    public String toString() {
        return getName();
    }
    
    
}
