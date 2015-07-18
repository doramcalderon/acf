package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgMessages;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Sets;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.TreeSet;

/**
 *
 * @since @author Dora Calderón
 */
public class DirectOptimalBasis2 extends GenericAlgorithm {

    @Override
    public String getName() {
        return "Direct Optimal Basis II";
    }

    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        ImplicationalSystem directOptimalBasis = new ImplicationalSystem(system);
        history("**************************************************************************************");
        history(messages.getMessage("INPUT:"));
        history(system.toString());
        history("**************************************************************************************");
        
        // Stage 1 : Generation of sigma-r by reduction of sigma
        reduce(directOptimalBasis);

        // Stage 2: Generation of sigma-sr by simplification (left+right) + composition of sigma-r
        simplify(directOptimalBasis);

        // Stage 3: Generation of sigma-dsr by completion of sigma-sr
        strongSimplification(directOptimalBasis);

        // Stage 4: Composition of sigma-dsr
        composition(directOptimalBasis);
        

        // Stage 5: Generation of sigma-do by optimization of sigma-dsr
        optimize(directOptimalBasis);

        return directOptimalBasis;
    }
    
    public void reduce(ImplicationalSystem system) {
        history("**************************************************************************************");
        history(messages.getMessage(AlgMessages.REDUCE));
        history("**************************************************************************************");
        system.makeProper();
        
        history("\n" + String.valueOf(system));
    }

    public void simplify(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of IS simplificated by simplification(left+right+composition) of reduced IS");
        history("**************************************************************************************");
        
        TreeSet a, b, c, d;
        ImplicationalSystem simplified;
        ImplicationalSystem gamma = new ImplicationalSystem();
        ImplicationalSystem sigma = new ImplicationalSystem(system);
        
        history("--------------------------------------------------------------------------------------");
        history("Initial Simplificated: {}", sigma.toString());

        do {
            simplified = new ImplicationalSystem(sigma);
            sigma.init();

            for (Rule ab : simplified.getRules()) {
                a = new TreeSet(ab.getPremise());
                b = new TreeSet(ab.getConclusion());
                gamma.init();
                Rule saveAB;
                
                history("--------------------------------------------------------------------------------------");
                history("Sigma: {}", (sigma.sizeRules() == 0) ? "{}" : sigma.toString());
                
                for (Rule cd : sigma.getRules()) {
                    c = cd.getPremise();
                    d = cd.getConclusion();

                    if ((a.containsAll(c) && Sets.union(c, d).containsAll(a)) || (c.containsAll(a) && Sets.union(a, b).containsAll(c))) {
                        a = Sets.intersection(a, c);
                        b = Sets.union(b, d);
                        
                        history("({}) + ({}) ==> A = A intersec C, B = B U D ==> {} -> {}", new Rule(a, b), cd, a, b);
                    } else {
                        if (a.containsAll(c)) {
                            saveAB = new Rule(a, b);
                            a.removeAll(d);
                            b.removeAll(d);
                            ImplicationalSystems.addRuleAndElements(gamma, new Rule(c, d));
                            
                            history("({}) + ({}) ==> A = A-D, B = B-D ==> {} -> {}", saveAB, cd, a, b);
                            history("                                 add C -> D to gamma ==> add {}", cd);
                        } else if (c.containsAll(a)) {
                            if (!b.containsAll(d)) {
                                saveAB = new Rule(a, b);
                                Rule newRule = new Rule(Sets.difference(c, b), Sets.difference(d, b));
                                ImplicationalSystems.addRuleAndElements(gamma, newRule);
                                
                                history("({}) + ({}) ==> C-B -> D-B ==> {}", saveAB, cd, newRule);
                            }
                        } else {
                             saveAB = new Rule(a, b);
                            ImplicationalSystems.addRuleAndElements(gamma, cd);
                            
                            history("({}) + ({}) ==> add C -> D ==> {}", saveAB, cd, cd);
                        }
                    }
                }
                if (!b.isEmpty()) {
                    saveAB = new Rule(a, b);
                    ImplicationalSystems.addRuleAndElements(gamma, saveAB);
                    
                    history("({}) ==> add A -> B ==> {}", saveAB, ab, saveAB);
                }
                sigma = ImplicationalSystems.addAllRules(sigma, gamma.getRules());
            }

        } while (!ImplicationalSystems.equals(simplified, sigma));
        system = simplified;
        
        history("\n" + String.valueOf(system));
    }

    /**
     *
     * @param system
     * @throws NullPointerException if system is null.
     */
    public void strongSimplification(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of IS by completion of simplifacted IS --> Strong Simplification");
        history("**************************************************************************************");
        
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem save;

        save = new ImplicationalSystem(system);
        for (Rule ab : save.getRules()) {
            a = ab.getPremise();
            b = ab.getConclusion();

            for (Rule cd : save.getRules()) {
                c = cd.getPremise();
                d = cd.getConclusion();

                if (!Sets.intersection(b, c).isEmpty() && !Sets.difference(d, Sets.union(a, b)).isEmpty()) {
                    Rule newRule = new Rule(Sets.difference(Sets.union(a, c), b), Sets.difference(d, Sets.union(a, b)));
                    ImplicationalSystems.addRuleAndElements(system, newRule);
                    getLogger().history("({}) + ({})  --->  ({})", ab, cd, newRule);
                }
            }
        }
        
        history("\n" + system.toString());
    }

    public void composition(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Composition of IS");
        history("**************************************************************************************");
        system.makeCompact();
//        ImplicationalSystem save;
//        TreeSet<Comparable> a, b, c, d;
//
//        save = new ImplicationalSystem(system);
//
//        for (Rule ab : save.getRules()) {
//            a = ab.getPremise();
//            b = ab.getConclusion();
//
//            for (Rule cd : save.getRules()) {
//                c = cd.getPremise();
//                d = cd.getConclusion();
//
//                if (!ab.equals(cd)) {
//                    if (a.equals(c)) {
//                        Rule newRule = new Rule(a, Sets.union(b, d));
//                        system.replaceRule(ab, newRule);
//                        system.removeRule(cd);
//                        
//                         history("({}) + ({}):  C = A ==> A = BD = {}", ab, cd, newRule);
//                    }
//                }
//            }
//        }
        history("\n" + system.toString());
    }

    public void optimize(ImplicationalSystem system) {
        history("**************************************************************************************");
        history("Generation of optimized IS ");
        history("**************************************************************************************");
        
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem save;

        do {
            save = new ImplicationalSystem(system);
            system.init();

            for (Rule ab : save.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();

                for (Rule cd : save.getRules()) {
                    if (!ab.equals(cd)) {
                        c = cd.getPremise();
                        d = cd.getConclusion();

                        if (c.equals(a)) {
                            b = Sets.union(b, d);
                            history("({}) + ({}):  C = A ==> B = B U D = {}", ab, cd, b);
                        }
                        if (a.containsAll(c)) {
                            b = Sets.difference(b, d);
                            history("({}) + ({}): C subcjto A ==> B = B-D = {}", ab, cd, b);
                        }
                    }
                }
                if (!b.isEmpty()) {
                    ImplicationalSystems.addRuleAndElements(system, new Rule(a, b));
                    history("B not empty ==> add({})", new Rule(a, b));
                }
            }
        } while (!ImplicationalSystems.equals(system, save));
        history("\n" + system.toString());
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
