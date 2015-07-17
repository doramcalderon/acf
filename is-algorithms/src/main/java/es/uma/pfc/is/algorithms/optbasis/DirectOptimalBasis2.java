

package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.algorithms.util.Sets;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.TreeSet;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class DirectOptimalBasis2 extends GenericAlgorithm {

    @Override
    public String getName() {
        return "Direct Optimal Basis II";
    }
    

    @Override
    public ImplicationalSystem execute(ImplicationalSystem system) {
        ImplicationalSystem directOptimalBasis = new ImplicationalSystem(system);
        
        // reduction
        directOptimalBasis.makeProper();
        
        // left simplification + right simplification + composition
        simplify(directOptimalBasis);
        
        strongSimplification(directOptimalBasis);
        
        composition(directOptimalBasis);
        
        optimize(directOptimalBasis);
        
        return directOptimalBasis;
    }

    public void simplify(ImplicationalSystem system) {
        TreeSet a, b, c, d;
        ImplicationalSystem simplified;
        ImplicationalSystem gamma = new ImplicationalSystem();
        ImplicationalSystem sigma = new ImplicationalSystem(system);
        
        do {
            simplified = new ImplicationalSystem(sigma);
            sigma.init();
            
            for(Rule ab : simplified.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();
                gamma.init();
                
                for(Rule cd : sigma.getRules()) {
                    c = cd.getPremise();
                    d = cd.getConclusion();
                    
                    if((a.containsAll(c) && Sets.union(c, d).containsAll(a)) || (c.containsAll(a) && Sets.union(a, b).containsAll(c))) {
                        a = Sets.intersection(a, c);
                        b = Sets.union(b, d);
                    } else {
                        if(a.containsAll(c)) {
                            a.removeAll(d);
                            b.removeAll(d);
                        } else if (c.containsAll(a)) {
                            if(!b.containsAll(d)) {
                                ImplicationalSystems.addRuleAndElements(gamma, new Rule(Sets.difference(c, b), Sets.difference(d, b)));
                            }
                        } else {
                            ImplicationalSystems.addRuleAndElements(gamma, cd);
                        }
                    }
                }
                if(!b.isEmpty()) {
                    ImplicationalSystems.addRuleAndElements(gamma, new Rule(a, b));
                }
                sigma = ImplicationalSystems.addAllRules(sigma, gamma.getRules());
            }
            
        } while(simplified.sizeRules() != sigma.sizeRules());
        system = simplified;
    }
    
    /**
     * 
     * @param system 
     * @throws NullPointerException if system is null.
     */
    public void strongSimplification(ImplicationalSystem system) {
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem save;
        
        do {
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
                    }
                }
            }
        } while (system.sizeRules() != save.sizeRules());
    }
    
    public void composition(ImplicationalSystem system) {
        ImplicationalSystem save = new ImplicationalSystem(system);
        TreeSet<Comparable> a, b, c, d;
        int size;
        do {
            size = system.sizeRules();
            for (Rule ab : save.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();

                for (Rule cd : save.getRules()) {
                    c = cd.getPremise();
                    d = cd.getConclusion();

                    if (!ab.equals(cd)) {
                        if (a.equals(c)) {
                            system.replaceRule(ab, new Rule(a, Sets.union(b, d)));
                            system.removeRule(cd);
                        }
                    }
                }
            }
        } while (system.sizeRules() != size);
    }
    
    public void optimize(ImplicationalSystem system) {
        TreeSet<Comparable> a, b, c, d;
        ImplicationalSystem save = new ImplicationalSystem(system);
        system.init();
        int initSize;
        
        do {
            initSize = system.sizeRules();
            for(Rule ab : save.getRules()) {
                a = ab.getPremise();
                b = ab.getConclusion();

                for(Rule cd : save.getRules()) {
                    if(!ab.equals(cd)) {
                        c = cd.getPremise();
                        d = cd.getConclusion();

                        if(c.equals(a)) {
                            b = Sets.union(b, d);
                        }
                        if(a.containsAll(c)) {
                            b = Sets.difference(b, d);
                        }
                    }
                }
                if(!b.isEmpty()) {
                    ImplicationalSystems.addRuleAndElements(system, new Rule(a, b));
                }
            }
        } while(system.sizeRules() != initSize);
    }
}
