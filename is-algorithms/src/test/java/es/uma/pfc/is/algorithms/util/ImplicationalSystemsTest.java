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

    /**
     * Comprueba que cuando dos reglas tienen la misma premisa se devuelve una única regla con la premisa y como conclusión,
     * la unión de las conclusiones de ambas.<br/>
     * {A -> B, A -> C} => {A -> BC}
     * 
     * @see ImplicationalSystems#compositionEquivalency(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) 
     */
    @Test
    public void testCompositionEquivalency_Rule_Rule() {
        Rule rule1 = new Rule();
        rule1.getPremise().add("A");
        rule1.getConclusion().add("B");
        
        Rule rule2 = new Rule();
        rule2.getPremise().add("A");
        rule2.getConclusion().add("C");
        
        List<Rule> rulesComposition = SimplificationLogic.compositionEquivalency(rule1, rule2);
        
        assertNotNull(rulesComposition);
        assertEquals(new Integer(1), (Integer) rulesComposition.size());
        
        Rule rule = rulesComposition.get(0);
        assertEquals(new Integer(1), (Integer) rule.getPremise().size());
        assertEquals(new Integer(2), (Integer) rule.getConclusion().size());
        
        assertTrue(rule.getPremise().contains("A"));
        assertTrue(rule.getConclusion().contains("B"));
        assertTrue(rule.getConclusion().contains("C"));
        
    }
    /**
     * Comprueba que cuando dos reglas no tienen la misma premisa se devuelve las mismas.<br/>
     * {A -> B, C -> D} => {A -> B, C -> D}
     * 
     * @see ImplicationalSystems#compositionEquivalency(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) 
     */
    @Test
    public void testCompositionEquivalency_Rule_Rule_NoComposition() {
        Rule rule1 = new Rule();
        rule1.getPremise().add("A");
        rule1.getConclusion().add("B");
        
        Rule rule2 = new Rule();
        rule2.getPremise().add("C");
        rule2.getConclusion().add("D");
        
        List<Rule> rulesComposition = SimplificationLogic.compositionEquivalency(rule1, rule2);
        
        assertNotNull(rulesComposition);
        assertEquals(new Integer(2), (Integer) rulesComposition.size());
        assertTrue(rulesComposition.contains(rule1));
        assertTrue(rulesComposition.contains(rule2));
    }
    /**
     * Comprueba que se produce un NullPointerException si alguna de las dos reglas es nula.
     * 
     * @see ImplicationalSystems#compositionEquivalency(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) 
     */
    @Test(expected = NullPointerException.class)
    public void testCompositionEquivalency_Rule_Null() {
        Rule rule1 = new Rule();
        rule1.getPremise().add("A");
        rule1.getConclusion().add("B");
        
        Rule rule2 = null;
        
        SimplificationLogic.compositionEquivalency(rule1, rule2);
        fail("NullPointerException expected.");
        
    }
    /**
     * Comprueba que se produce un NullPointerException si alguna de las dos reglas es nula.
     * 
     * @see ImplicationalSystems#compositionEquivalency(fr.kbertet.lattice.Rule, fr.kbertet.lattice.Rule) 
     */
    @Test
    public void testCompositionEquivalency_Rule_Empty() {
        Rule rule1 = new Rule();
        rule1.getPremise().add("A");
        rule1.getConclusion().add("B");
        
        Rule rule2 = new Rule();
        
        List<Rule> rulesComposition = SimplificationLogic.compositionEquivalency(rule1, rule2);
        
        assertNotNull(rulesComposition);
        assertEquals(new Integer(2), (Integer) rulesComposition.size());
        assertTrue(rulesComposition.contains(rule1));
        assertTrue(rulesComposition.contains(rule2));
        
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
        
        ImplicationalSystems.addAllRules(system, rules);
        
        assertNotNull(system.getRules());
        assertEquals(new Integer(3), (Integer) system.getRules().size());
        
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

    /**
     * Test de {@link ImplicationalSystems#fragmentationEquivalency(fr.kbertet.lattice.Rule) }.
     * Comprueba que se simplifica la implicación pasada como parámetro aplicando que:<br/>
     * {A -> B} = {A -> B-A}
     * 
     */
    @Test
    public void testFragmentationEquivalency() {
        Rule rule = new Rule();
        rule.getPremise().add("a");
        rule.getPremise().add("b");
        
        rule.getConclusion().add("a");
        rule.getConclusion().add("b");
        rule.getConclusion().add("c");
        
        Rule eqRule = SimplificationLogic.fragmentationEquivalency(rule);
        
        assertNotNull(eqRule);
        assertEquals(new Integer(2), (Integer) eqRule.getPremise().size());
        assertTrue(eqRule.getPremise().contains("a"));
        assertTrue(eqRule.getPremise().contains("b"));
        
        assertEquals(new Integer(1), (Integer) eqRule.getConclusion().size());
        assertTrue(eqRule.getConclusion().contains("c"));
    }
    /**
     * Test de {@link ImplicationalSystems#fragmentationEquivalency(fr.kbertet.lattice.Rule) } cuando la premisa
     * y la conclusión no tienen elementos comunes.<br/>
     * 
     */
    @Test
    public void testFragmentationEquivalency_Unfragmentable() {
        Rule rule = new Rule();
        rule.getPremise().add("a");
        
        rule.getConclusion().add("b");
        rule.getConclusion().add("c");
        
        Rule eqRule = SimplificationLogic.fragmentationEquivalency(rule);
        
        assertEquals(rule, eqRule);
    }

    /**
     * Test de {@link ImplicationalSystems#fragmentationEquivalency(fr.kbertet.lattice.Rule) } cunado la implicación
     * es vacía.
     */
    @Test
    public void testFragmentationEquivalency_EmptyRule() {
        Rule rule = new Rule();
        Rule eqRule = SimplificationLogic.fragmentationEquivalency(rule);
        
        assertNotNull(eqRule);
        assertTrue(eqRule.getPremise().isEmpty());
        assertTrue(eqRule.getConclusion().isEmpty());
    }

    /**
     * Test de {@link ImplicationalSystems#fragmentationEquivalency(fr.kbertet.lattice.Rule) } cuando
     * la implicación es nula.
     * 
     */
    @Test
    public void testFragmentationEquivalency_NullRule() {
        assertNull(SimplificationLogic.fragmentationEquivalency(null));
    }
    /* dado el sistema {a->b, ac->be}, 
     * al aplicar la simplificación debe quedar {a->b, ac -> e}
     */
    @Test
    public void testSimplificationEquivalency() {
        
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("b");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("a");
        rule2.addToPremise("c");
        rule2.addToConclusion("b");
        rule2.addToConclusion("e");
        
        List<Rule> rules = SimplificationLogic.simplificationEquivalency(rule1, rule2);
        
        assertNotNull(rules);
        assertEquals(new Integer(2), (Integer) rules.size());
        assertTrue(rules.contains(rule1));
        
        Rule rulesimp = new Rule();
        rulesimp.addToPremise("a");
        rulesimp.addToPremise("c");
        rulesimp.addToConclusion("e");
        
        assertTrue(rules.contains(rulesimp));
    }
    /* dado el sistema {a->ab, ac->be}, no se aplicará la simplificación.
     */
    @Test
    public void testSimplificationEquivalencyNonEmptyAIntersectB() {
        
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("ab");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("a");
        rule2.addToPremise("c");
        rule2.addToConclusion("b");
        rule2.addToConclusion("e");
        
        List<Rule> rules = SimplificationLogic.simplificationEquivalency(rule1, rule2);
        
        assertNotNull(rules);
        assertEquals(new Integer(2), (Integer) rules.size());
        assertTrue(rules.contains(rule1));
        assertTrue(rules.contains(rule2));
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
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Utilidades">
    
    private ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
                    + File.separator + "resources" + File.separator;
        return new ImplicationalSystem(dir + file);        
    }
    
//</editor-fold>
    
}
