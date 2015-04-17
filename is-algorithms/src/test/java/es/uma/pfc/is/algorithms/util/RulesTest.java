/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.util;

import fr.kbertet.lattice.Rule;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class RulesTest {
    
    public RulesTest() {
    }

    /**
     * Test of getElements method, of class Rules.
     */
    @Test
    public void testGetElements() {
        Rule rule = new Rule();
        rule.addToPremise("a");
        rule.addToPremise("b");
        rule.addToConclusion("c");
        rule.addToConclusion("d");
        
        Set elements = Rules.getElements(rule);
        
        assertNotNull(elements);
        assertEquals(4, elements.size());
        assertTrue(elements.contains("a"));
        assertTrue(elements.contains("b"));
        assertTrue(elements.contains("c"));
        assertTrue(elements.contains("d"));
    }
    /**
     * Test of getElements method, of class Rules.
     */
    @Test
    public void testGetElements_ElementsDuplicated() {
        Rule rule = new Rule();
        rule.addToPremise("a");
        rule.addToPremise("b");
        rule.addToConclusion("b");
        rule.addToConclusion("d");
        
        Set elements = Rules.getElements(rule);
        
        assertNotNull(elements);
        assertEquals(3, elements.size());
        assertTrue(elements.contains("a"));
        assertTrue(elements.contains("b"));
        assertTrue(elements.contains("d"));
    }
    /**
     * Test of getElements method, of class Rules.
     */
    @Test
    public void testGetElements_Null() {
        Set elements = Rules.getElements(null);
        assertNotNull(elements);
        assertTrue(elements.isEmpty());
    }
    
}
