package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgMessages;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Sets;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @since @author Dora Calderón
 */
public class DirectOptimalBasis extends GenericAlgorithm {

    public DirectOptimalBasis() {
    }

    
    /**
     * Executes the Direct Optimal Basis algorithm.
     * @param system Input system.
     * @return 
     */
    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        ImplicationalSystem directOptimalBasis = new ImplicationalSystem(system);

        history("**************************************************************************************");
        history(messages.getMessage("INPUT:"));
        history(system.toString());
        history("**************************************************************************************");

        // Stage 1 : Generation of sigma-r by reduction of sigma
        directOptimalBasis = SimplificationLogic.reduce(directOptimalBasis, getLogger());

        // Stage 2: Generation of sigma-sr by simplification (left+right) + composition of sigma-r
        directOptimalBasis = simplify(directOptimalBasis);

        // Stage 3: Generation of sigma-dsr by completion of sigma-sr
        directOptimalBasis = SimplificationLogic.strongSimplification(directOptimalBasis, getLogger());

        // Stage 4: Composition of sigma-dsr
        SimplificationLogic.composition(directOptimalBasis, getLogger());

        // Stage 5: Generation of sigma-do by optimization of sigma-dsr
        directOptimalBasis = SimplificationLogic.optimize(directOptimalBasis, getLogger());

        return directOptimalBasis;
    }
    
    public ImplicationalSystem reduce(ImplicationalSystem system) {
        history("**************************************************************************************");
        history(messages.getMessage(AlgMessages.REDUCE));
        history("**************************************************************************************");
        system.makeProper(); // TODO se puede implementar con lambda expressions
        
        
        history("\n" + String.valueOf(system));
        return system;
    }

    /**
     * Generation of IS simplificated by simplification(left+right+composition) of reduced IS
     * @param system Reduced system.
     * @return Simplified system.
     */
    public ImplicationalSystem simplify(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of IS simplificated by simplification(left+right+composition) of reduced IS");
        history("**************************************************************************************");
        
        TreeSet a, b, c, d;
        ImplicationalSystem simplified;
        ImplicationalSystem gamma = new ImplicationalSystem();
        ImplicationalSystem sigma = new ImplicationalSystem(system);
        boolean changed;
        
        history("--------------------------------------------------------------------------------------");
        history("Initial Simplificated: {}", sigma.toString());
        
        do {
            changed = false;
            simplified = new ImplicationalSystem(sigma);
            sigma.init();

            for (Rule ab : simplified.getRules()) {
                a = new TreeSet(ab.getPremise());
                b = new TreeSet(ab.getConclusion());
                gamma.init();
                Rule saveAB;
                Rule newRule;
                
                history("--------------------------------------------------------------------------------------");
                history("Sigma: {}", (sigma.sizeRules() == 0) ? "{}" : sigma.toString());
                
                for (Rule cd : sigma.getRules()) {
                    c = cd.getPremise();
                    d = cd.getConclusion();
                    saveAB = new Rule(a, b);

                    if ((a.containsAll(c) && Sets.union(c, d).containsAll(a)) || (c.containsAll(a) && Sets.union(a, b).containsAll(c))) {
                        a = Sets.intersection(a, c);
                        b = Sets.union(b, d);
                        
                        history("({}) + ({}) ==> A = A intersec C, B = B U D ==> {} -> {}", saveAB, cd, a, b);
                    } else {
                        if (a.containsAll(c)) {
                            a.removeAll(d);
                            b.removeAll(d);
                            ImplicationalSystems.addRuleAndElements(gamma, cd);
                            
                            history("({}) + ({}) ==> A = A-D, B = B-D ==> {} -> {}", saveAB, cd, a, b);
                            history("                                 add C -> D to gamma ==> add {}", cd);
                        } else if (c.containsAll(a)) {
                            if (!b.containsAll(d)) {
                                newRule = new Rule(Sets.difference(c, b), Sets.difference(d, b));
                                changed = ImplicationalSystems.addRuleAndElements(gamma, newRule) || changed;
                                
                                history("({}) + ({}) ==> C-B -> D-B ==> {}", saveAB, cd, newRule);
                            }
                        } else {
                            ImplicationalSystems.addRuleAndElements(gamma, cd);
                            
                            history("({}) + ({}) ==> add C -> D ==> {}", saveAB, cd, cd);
                        }
                    }
                }
                if (!b.isEmpty()) {
                    saveAB = new Rule(a, b);
                    ImplicationalSystems.addRuleAndElements(gamma, saveAB);
                    changed = !ab.equals(saveAB);
                    history("({}) ==> add A -> B ==> {}", saveAB, ab, saveAB);
                }
                sigma = ImplicationalSystems.addAllRules(new ImplicationalSystem(), gamma.getRules());
            }

        } while (changed);
        
        history("\n" + String.valueOf(simplified));
        return simplified;
    }

    /**
     * Generation of IS by completion of simplifacted IS --> Strong Simplification.
     * @param system Simplified system.
     * @return Strong simplified system.
     * @throws NullPointerException if system is null.
     */
    public ImplicationalSystem strongSimplification(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of IS by completion of simplifacted IS --> Strong Simplification");
        history("**************************************************************************************");
        
        history("\n" + String.valueOf(system));
        
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
                        getLogger().history("({}) + ({})  --->  ({})", ab, cd, newRule);
                    }
                }
            }
        } while(changed);
        
        history("\n" + simplified.toString());
        return simplified;
    }
    /**
     * Composition of implications of a sistem.
     * @param system Implicational System.
     * @return Implicational System with composition applied.
     */
    public ImplicationalSystem composition(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Composition of IS");
        history("**************************************************************************************");
        system.makeCompact();
        history("\n" + system.toString());
        return system;
    }

    /**
     * Generation of optimized IS.
     * @param system Simplified Implicational System.
     * @return Optimized system.
     */
    public ImplicationalSystem optimize(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of optimized IS ");
        history("**************************************************************************************");
        history("\n" + system.toString());
        
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
                        history("({}) + ({}):  C = A ==> B = B U D = {}", ab, cd, b);
                    } else if (a.containsAll(c)) {
                        b = Sets.difference(b, d);
                        history("({}) + ({}): C subcjto A ==> B = B-D = {}", ab, cd, b);
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
                history("B not empty ==> add({})", newAB);
            } else {
                history("B empty ==> not added({})", newAB);
                
            }
        }
            
            save = new ImplicationalSystem(optimized);
            history("\n" + optimized.toString());
        return optimized;
    }

    /**
     * Prints de init arguments.
     *
     * @param inputSystem Implicactional System.
     */
    protected void printInit(ImplicationalSystem inputSystem) {
        history("------------- INPUT ------------------------------------");
        history(String.valueOf(inputSystem));
        history("\n\n");
    }

    /**
     * Prints the results.
     *
     * @param resultSystem Implicational System.
     */
    protected void printResult(ImplicationalSystem resultSystem) {
        int sizeRules = 0;
        int sizeSystem = 0;
        if (resultSystem != null) {
            sizeRules = resultSystem.sizeRules();
            sizeSystem = ImplicationalSystems.getSize(resultSystem);
        }

        history("**************************************************************************************");
        history("DOBasis generated");
        history("**************************************************************************************");
        history(String.valueOf(resultSystem));
        history("------------- COMPARISONS --------------------");
        history("{} ** Implications", sizeRules);
        history("{} the size of DOBasis", sizeSystem);

    }
}
