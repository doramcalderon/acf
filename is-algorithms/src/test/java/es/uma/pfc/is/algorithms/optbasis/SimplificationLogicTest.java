/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.optbasis.SimplificationLogic;
import fr.kbertet.lattice.Rule;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class SimplificationLogicTest {
  

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
        
        List<Rule> rules = SimplificationLogic.rightSimplificationEq(rule1, rule2);
        
        assertNotNull(rules);
        assertEquals(new Integer(2), (Integer) rules.size());
        assertTrue(rules.contains(rule1));
        assertTrue(rules.contains(rule2));
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
        
        List<Rule> rules = SimplificationLogic.rightSimplificationEq(rule1, rule2);
        
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
        rule1.addToConclusion("a");
        rule1.addToConclusion("b");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("a");
        rule2.addToPremise("c");
        rule2.addToConclusion("b");
        rule2.addToConclusion("e");
        
        List<Rule> rules = SimplificationLogic.rightSimplificationEq(rule1, rule2);
        
        assertNotNull(rules);
        assertEquals(new Integer(2), (Integer) rules.size());
        assertTrue(rules.contains(rule1));
        assertTrue(rules.contains(rule2));
    }
  
    

    /**
     * Se comprueba que se aplica la regla:
     * <p>
     * Si (B interseccion C) no es vacío y (D \ (A union B)) tampoco, se devuelve la nueva implicación 
     * AC - B -> D - (AB).
     * </p>
     * Por lo que dado el sistema {a->ab, ac->be}, debe devolver la nueva regla a añadir c -> e.
     */
    @Test
    public void testStrongSimplificationEq() {
        Rule rule1 = new Rule();
        rule1.addToPremise("a");
        rule1.addToConclusion("a");
        rule1.addToConclusion("b");
        
        Rule rule2 = new Rule();
        rule2.addToPremise("a");
        rule2.addToPremise("c");
        rule2.addToConclusion("b");
        rule2.addToConclusion("e");
        
        Rule newRule = SimplificationLogic.strongSimplificationEq(rule1, rule2);
        
        assertNotNull(newRule);
        assertEquals(1, newRule.getPremise().size());
        assertTrue(newRule.getPremise().contains("c"));
        assertEquals(1, newRule.getConclusion().size());
        assertTrue(newRule.getConclusion().contains("e"));
    }
    
}
