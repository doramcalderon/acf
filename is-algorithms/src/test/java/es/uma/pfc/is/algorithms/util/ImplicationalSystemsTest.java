/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.util;

import es.uma.pfc.is.algorithms.DirectOptimalBasis;
import es.uma.pfc.is.algorithms.SimplificationLogic;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de {@link ImplicationalSystems}.
 * @author Dora Calderón
 */
public class ImplicationalSystemsTest {
    

    /**
     * Comprueba que se devuelve el número de implicaciones del sistema.
     */
    @Test
    public void testGetCardinality() {
        ImplicationalSystem system = ImplicationalSystem.random(5, 10);
        Integer expectedResult = system.getRules().size();
        Integer result = ImplicationalSystems.getCardinality(system);
        assertEquals(expectedResult, result);
    }
    

    /**
     * Comprueba que devuelve nulo cuando el sistema lo es.
     */
    @Test
    public void testGetCardinalityNullSystem() {
        ImplicationalSystem system = null;
        Integer result = ImplicationalSystems.getCardinality(system);
        assertNull(result);
    }

    /**
     * Comprueba que se devuelve la suma del número de elementos de todas las implicaciones.
     */
    @Test
    public void testGetSize() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addElement("a");
        system.addElement("b");
        system.addElement("c");
        system.addRule(new Rule(new TreeSet(Arrays.asList("a", "b")), new TreeSet(Arrays.asList("c"))));
        system.addRule(new Rule(new TreeSet(Arrays.asList("b")), new TreeSet(Arrays.asList("c"))));
        Integer expectedSize = 5;
        Integer size = ImplicationalSystems.getSize(system);
        assertEquals(expectedSize, size);
    }
    /**
     * Comprueba que se devuelve nulo cuando el sistema lo es.
     */
    @Test
    public void testGetSizeNullSystem() {
        ImplicationalSystem system = null;
        Integer size = ImplicationalSystems.getSize(system);
        assertNull(size);
    }

//    /**
//     * Test of simplification method, of class ImplicationalSystems.
//     */
//    @Test
//    public void testCompositionEquivalency_ImplicationalSystem() throws IOException {
//        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
//        ImplicationalSystem system = new ImplicationalSystem(dir + "test_2.txt");
//        
//        system.getRules().stream().map((r) -> {
//            system.addAllElements(r.getPremise());
//            return r;
//        }).forEach((r) -> {
//            system.addAllElements(r.getConclusion());
//        });
//                
//        
//        ImplicationalSystem newSystem = ImplicationalSystems.simplification(system);
//        
//        assertNotNull(newSystem);
//        assertEquals(new Integer(2), (Integer) newSystem.getRules().size());
//        
//    }

    @Test
    public void testAddRuleAndElements() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addElement("a");
        system.addElement("b");
        TreeSet premise = new TreeSet();
        premise.add("a");
        TreeSet conclusion = new TreeSet();
        conclusion.add("b");
        Rule rule = new Rule(premise, conclusion);
        system.addRule(rule);
        
        premise = new TreeSet();
        premise.add("c");
        conclusion = new TreeSet();
        conclusion.add("d");
        
        Rule newRule = new Rule(premise, conclusion);
        
        ImplicationalSystem newSystem = ImplicationalSystems.addRuleAndElements(system, newRule);
        
        assertNotNull(newSystem);
        assertEquals(2, newSystem.sizeRules());
        assertTrue(newSystem.containsRule(rule));
        assertTrue(newSystem.containsRule(newRule));
        assertEquals(4, newSystem.sizeElements());
        assertTrue(newSystem.getSet().contains("a"));
        assertTrue(newSystem.getSet().contains("b"));
        assertTrue(newSystem.getSet().contains("c"));
        assertTrue(newSystem.getSet().contains("d"));
    }
    @Test
    public void testAddRuleAndElements_NullSystem() {
        ImplicationalSystem system = null;
        TreeSet premise = new TreeSet();
        premise.add("b");
        TreeSet conclusion = new TreeSet();
        conclusion.add("c");
        Rule newRule = new Rule(premise, conclusion);
        
        ImplicationalSystem newSystem = ImplicationalSystems.addRuleAndElements(system, newRule);
        
        assertNotNull(newSystem);
        assertEquals(1, newSystem.sizeRules());
        assertTrue(newSystem.containsRule(newRule));
        assertEquals(2, newSystem.sizeElements());
        assertTrue(newSystem.getSet().contains("b"));
        assertTrue(newSystem.getSet().contains("c"));
    }
   
    /**
     * Comprueba que se añaden las reglas de una colección a un sistema.
     */
    @Test
    public void testAddAllRules() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(new TreeSet(Arrays.asList("a", "b", "c", "d")));
        system.addRule(new Rule(new TreeSet(Arrays.asList("a")), new TreeSet(Arrays.asList("d"))));
        
        List <Rule> rules = Arrays.asList(new Rule(new TreeSet(Arrays.asList("a", "b")), new TreeSet(Arrays.asList("c"))),
                                          new Rule(new TreeSet(Arrays.asList("b")), new TreeSet(Arrays.asList("c"))));
        
        ImplicationalSystem newSystem = ImplicationalSystems.addAllRules(system, rules);
        
        assertNotNull(newSystem.getRules());
        assertEquals(3, newSystem.getRules().size());
        
    }
    /**
     * Comprueba que no se añade nada si la colección de reglas es vacía.
     */
    @Test
    public void testAddEmptyRules() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(new TreeSet(Arrays.asList("a", "b", "c", "d")));
        system.addRule(new Rule(new TreeSet(Arrays.asList("a")), new TreeSet(Arrays.asList("d"))));
        
        List <Rule> rules = new ArrayList();
        ImplicationalSystems.addAllRules(system, rules);
        
        assertNotNull(system.getRules());
        assertEquals(new Integer(1), new Integer(system.getRules().size()));
        
    }
    /**
     * Comprueba que no se añade nada si la colección de reglas es nula.
     */
    @Test
    public void testAddNullRules() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(new TreeSet(Arrays.asList("a", "b", "c", "d")));
        system.addRule(new Rule(new TreeSet(Arrays.asList("a")), new TreeSet(Arrays.asList("d"))));
        
        ImplicationalSystems.addAllRules(system, null);
        
        assertNotNull(system.getRules());
        assertEquals(new Integer(1), new Integer(system.getRules().size()));
        
    }

    
   
    @Test
    public void testReduce() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_1.txt");
        
        ImplicationalSystem reducedSystem = ImplicationalSystems.reduce(system);
        
        assertNotNull(reducedSystem);
        assertEquals(new Integer(4), (Integer) reducedSystem.sizeRules());
        
        Rule r = new Rule();
        r.addToPremise("d");
        r.addToConclusion("c");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("c");
        r.addToConclusion("a");
        r.addToConclusion("b");
        r.addToConclusion("d");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("c");
        r.addToPremise("e");
        r.addToConclusion("a");
        r.addToConclusion("b");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("a");
        r.addToConclusion("d");
        assertTrue(reducedSystem.containsRule(r));
    }
    
    @Test
    public void testReduceNullSystem() throws IOException {
        ImplicationalSystem reducedSystem = ImplicationalSystems.reduce(null);    
        assertNull(reducedSystem);
    }

    @Test
    public void testEquals() throws IOException {
        ImplicationalSystem system1 = getSystemFromFile("test_1.txt");
        ImplicationalSystem system2 = getSystemFromFile("test_1.txt");
        
        assertTrue(ImplicationalSystems.equals(system1, system2));
    }
    @Test
    public void testNotEquals() throws IOException {
        ImplicationalSystem system1 = getSystemFromFile("test_1.txt");
        ImplicationalSystem system2 = getSystemFromFile("test_2.txt");
        
        assertFalse(ImplicationalSystems.equals(system1, system2));
    }
    @Test
    public void testNotEqualsWithNull() throws IOException {
        ImplicationalSystem system1 = getSystemFromFile("test_1.txt");
        
        assertFalse(ImplicationalSystems.equals(system1, null));
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Utilidades">
    
    private ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
                    + File.separator + "resources" + File.separator;
        return new ImplicationalSystem(dir + file);        
    }
    
//</editor-fold>
    
}
