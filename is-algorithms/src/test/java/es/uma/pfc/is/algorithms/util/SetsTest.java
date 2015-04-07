/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.util;

import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class SetsTest {
    
    public SetsTest() {
    }

    /**
     * Test of intersection method, of class Sets.
     */
    @Test
    public void testIntersection() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        set2.add("b");
        set2.add("c");
        
        Set intersection = Sets.intersection(set1, set2);
        
        assertNotNull(intersection);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.contains("b"));
    }
    @Test
    public void testEmptyIntersection() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        set2.add("c");
        set2.add("d");
        
        Set intersection = Sets.intersection(set1, set2);
        
        assertNotNull(intersection);
        assertTrue(intersection.isEmpty());
    }
    @Test
    public void testIntersectionWithEmptySet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        
        Set intersection = Sets.intersection(set1, set2);
        
        assertNotNull(intersection);
        assertTrue(intersection.isEmpty());
    }
    @Test
    public void testIntersectionWithNullSet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set intersection = Sets.intersection(set1, null);
        
        assertNull(intersection);
    }
    /**
     * Test of intersection method, of class Sets.
     */
    @Test
    public void testDifference() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        set2.add("b");
        set2.add("c");
        
        Set difference = Sets.difference(set1, set2);
        
        assertNotNull(difference);
        assertTrue(difference.size() == 1);
        assertTrue(difference.contains("a"));
    }
    @Test
    public void testEmptyDifference() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        set2.add("a");
        set2.add("b");
        
        Set intersection = Sets.difference(set1, set2);
        
        assertNotNull(intersection);
        assertTrue(intersection.isEmpty());
    }
    @Test
    public void testDifferenceWithEmptySet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        
        Set intersection = Sets.difference(set1, set2);
        
        assertNotNull(intersection);
        assertEquals(set1, intersection);
    }
    @Test
    public void testDifferenceWithNullSet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set intersection = Sets.difference(set1, null);
        
        assertNull(intersection);
    }
    
}
