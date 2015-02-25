/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Dorilla (Fakul)
 */
public class ImplicationsFactoryTest {
    
    public ImplicationsFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getImplicationalSystem method, of class ImplicationsFactory.
     */
    @Test
    public void testGetImplicationalSystem_int_int() {
        int nodesNumber = 10;
        int rulesNumber = 5;
        
        ImplicationalSystem result = ImplicationsFactory.getImplicationalSystem(nodesNumber, rulesNumber);
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);
    }

    /**
     * Comprueba que se genera un conjunto de implicaciones aleatorio, en las que dichas implicaciones no
     * supera la longitud indicada, tanto en la premisa como en la conclusión.
     */
    @Test
    public void testGetImplicationalSystem_4args() {
        
        int nodesNumber = 10;
        int rulesNumber = 5;
        int maxLongPremises = 3;
        int maxLongConclusions = 4;
        
        ImplicationalSystem result = 
                ImplicationsFactory.getImplicationalSystem(nodesNumber, rulesNumber, 
                                                                  maxLongPremises, maxLongConclusions);
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);
        
        for(Rule rule : result.getRules()) {
            System.out.println("premise.size(): " + rule.getPremise().size());
            assertTrue(rule.getPremise().size() <= maxLongPremises);
            System.out.println("conclusion.size(): " + rule.getConclusion().size());
            assertTrue(rule.getConclusion().size() <= maxLongConclusions);
        }
    }
    
    @Test
    public void testGetRandomInt() {
        int maxValue = 5;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random >= 0 && random < maxValue);
    }
    @Test
    public void testGetRandomIntMaxValueZero() {
        int maxValue = 0;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random == 0);
    }
//    @Test
    public void testGetRandomIntLessThanZero() {
        int maxValue = -2;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random <= 0 && random > maxValue);
    }
}
