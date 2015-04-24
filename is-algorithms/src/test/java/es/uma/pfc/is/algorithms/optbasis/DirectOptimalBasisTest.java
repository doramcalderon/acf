/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class DirectOptimalBasisTest {
    
    public DirectOptimalBasisTest() {
    }

    /**
     * Test of execute method, of class DirectOptimalBasis.
     */
    @Test
    public void testExecute() {
    }

    /**
     * Test of simplificate method, of class DirectOptimalBasis.
     */
    @Test
    public void testSimplificate() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_strong_simp.txt");
        System.out.println(new DirectOptimalBasis().simplificate(system));
    }

    /**
     * Test of strongSimplificate method, of class DirectOptimalBasis.
     */
    @Test
    public void testStrongSimplificate() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_strong_simp.txt");
        DirectOptimalBasis alg = new DirectOptimalBasis();
        System.out.println(alg.strongSimplificate(system));
        System.out.println("Fin..");
//         System.out.println(alg.optimize(alg.strongSimplificate(system)));
    }

    /**
     * Test of optimize method, of class DirectOptimalBasis.
     */
    @Test @Ignore
    public void testOptimize_ImplicationalSystem() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_optimize.txt");
        ImplicationalSystem systemExpected = getSystemFromFile("test_optimize_result.txt");
        
        ImplicationalSystem optimizedSystem = new DirectOptimalBasis().optimize(system);
        
        assertTrue(ImplicationalSystems.equals(systemExpected, optimizedSystem));
        
    }

    /**
     * Comprueba que se obtiene una implicación simplificada a partir de dos reglas, cumpliendo
     * las condiciones necesarias.<br/>
     * Un ejemplo sería, dado {a -> bc, a -> d} => {a -> bcd}
     * @see DirectOptimalBasis#optimize(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) .
     */
    @Test
    public void testOptimize_Rule_Rule() {
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("b");
        rule1.addToConclusion("c");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("a");
        rule2.addToConclusion("b");
        rule2.addToConclusion("c");
        rule2.addToConclusion("d");
        
        Rule optimizedRule = new DirectOptimalBasis().optimize(rule1, rule2);
        
        assertNotNull(optimizedRule);
        assertEquals(1, optimizedRule.getPremise().size());
        assertTrue(optimizedRule.getPremise().contains("a"));
        assertEquals(3, optimizedRule.getConclusion().size());
        assertTrue(optimizedRule.getConclusion().contains("b"));
        assertTrue(optimizedRule.getConclusion().contains("c"));
        assertTrue(optimizedRule.getConclusion().contains("d"));
    }

    /**
     * Comprueba que se obtiene una implicación simplificada a partir de dos reglas, cumpliendo
     * las condiciones necesarias.<br/>
     * Un ejemplo sería, dado {a -> bc, f -> d} => {a -> bcd}
     * @see DirectOptimalBasis#optimize(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) .
     */
    @Test @Ignore
    public void testOptimize_Rule_Rule2() {
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("b");
        rule1.addToConclusion("c");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("f");
        rule2.addToConclusion("d");
        
        Rule optimizedRule = new DirectOptimalBasis().optimize(rule1, rule2);
        
        assertNotNull(optimizedRule);
        assertEquals(1, optimizedRule.getPremise().size());
        assertTrue(optimizedRule.getPremise().contains("a"));
        assertEquals(3, optimizedRule.getConclusion().size());
        assertTrue(optimizedRule.getConclusion().contains("b"));
        assertTrue(optimizedRule.getConclusion().contains("c"));
        assertTrue(optimizedRule.getConclusion().contains("d"));
    }
    /**
     * Comprueba que se obtiene una implicación simplificada a partir de dos reglas, cumpliendo
     * las condiciones necesarias.<br/>
     * Un ejemplo sería, dado {a -> bc, f -> d} => {a -> bc}
     * @see DirectOptimalBasis#optimize(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) .
     */
    @Test @Ignore
    public void testOptimize_Rule_Rule3() {
        Rule rule1 = new Rule();
        rule1.addToPremise("3");
        rule1.addToPremise("5");
        rule1.addToConclusion("1");
        rule1.addToConclusion("2");
        rule1.addToConclusion("4");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("1");
        rule2.addToPremise("5");
        rule2.addToConclusion("2");
        rule2.addToConclusion("4");

        
        Rule optimizedRule = new DirectOptimalBasis().optimize(rule1, rule2);
        
        assertNotNull(optimizedRule);
        assertEquals(2, optimizedRule.getPremise().size());
        assertTrue(optimizedRule.getPremise().contains("3"));
        assertTrue(optimizedRule.getPremise().contains("5"));
        assertEquals(1, optimizedRule.getConclusion().size());
        assertTrue(optimizedRule.getConclusion().contains("1"));
    }

    /**
     * Comprueba que devuelve nulo cuando una de las dos implicaciones es nula.
     */
    @Test
    public void testOptimize_Rule_Rule_WithNull() {
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("b");
        rule1.addToConclusion("c");
        
        Rule rule2 = null;
        
        Rule optimizedRule = new DirectOptimalBasis().optimize(rule1, rule2);
        assertNull(optimizedRule);
    }

    //<editor-fold defaultstate="collapsed" desc="Utilidades">
    
    private ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
                    + File.separator + "resources" + File.separator;
        return new ImplicationalSystem(dir + file);        
    }
    
//</editor-fold>
}
