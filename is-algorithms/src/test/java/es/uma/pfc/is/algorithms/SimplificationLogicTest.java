/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import fr.kbertet.lattice.Rule;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class SimplificationLogicTest {
    
    public SimplificationLogicTest() {
    }

    /**
     * Test of fragmentationEquivalency method, of class SimplificationLogic.
     */
    @Test
    public void testFragmentationEquivalency() {
    }

    /**
     * Test of compositionEquivalency method, of class SimplificationLogic.
     */
    @Test
    public void testCompositionEquivalency() {
    }

     /* dado el sistema {a->b, c->be}, no se aplica la simplificación ya que A no es subconjunto de C.
     */
    @Test
    public void testSimplificationEquivalencyNonCContainsA() {
        
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("b");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("c");
        rule2.addToConclusion("b");
        rule2.addToConclusion("e");
        
        List<Rule> rules = SimplificationLogic.simplificationEquivalency(rule1, rule2);
        
        assertNotNull(rules);
        assertEquals(new Integer(2), (Integer) rules.size());
        assertTrue(rules.contains(rule1));
        assertTrue(rules.contains(rule2));
    }

    /**
     * Test of strongSimplificationEq method, of class SimplificationLogic.
     */
    @Test
    public void testStrongSimplificationEq() {
    }
    
}
