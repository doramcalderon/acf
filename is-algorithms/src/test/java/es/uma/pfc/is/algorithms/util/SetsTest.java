
package es.uma.pfc.is.algorithms.util;

import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of sets class.
 * @author Dora Calderón
 */
public class SetsTest {
    
    public SetsTest() {
    }

    /**
     * Test of union method, of class Sets.
     */
    @Test
    public void testUnion() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        set2.add("b");
        set2.add("c");
        
        Set union = Sets.union(set1, set2);
        
        assertNotNull(union);
        assertTrue(union.size() == 3);
        assertTrue(union.contains("a"));
        assertTrue(union.contains("b"));
        assertTrue(union.contains("c"));
    }
    @Test
    public void testEmptyUnion() {
        Set set1 = new TreeSet();
        Set set2 = new TreeSet();
        
        Set union = Sets.union(set1, set2);
        
        assertNotNull(union);
        assertTrue(union.isEmpty());
    }
    @Test
    public void testUnionWithEmptySet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set set2 = new TreeSet();
        
        Set union = Sets.union(set1, set2);
        
        assertNotNull(union);
        assertTrue(union.size() == 2);
        assertTrue(union.contains("a"));
        assertTrue(union.contains("b"));
    }
    @Test
    public void testUnionWithNullSet() {
        Set set1 = new TreeSet();
        set1.add("a");
        set1.add("b");
        
        Set union = Sets.union(set1, null);
        
        assertNotNull(union);
        assertTrue(union.size() == 2);
        assertTrue(union.contains("a"));
        assertTrue(union.contains("b"));
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
    
    
//    /**
//     * Test of intersection method, of class Sets.
//     */
//    @Test
//    public void testSymDifference() {
//        Set set1 = new TreeSet();
//        set1.add("a");
//        set1.add("b");
//        
//        Set set2 = new TreeSet();
//        set2.add("b");
//        set2.add("c");
//        
//        Set symDiff = Sets.symDifference(set1, set2);
//        
//        assertNotNull(symDiff);
//        assertTrue(symDiff.size() == 2);
//        assertTrue(symDiff.contains("a"));
//        assertTrue(symDiff.contains("c"));
//    }
//    @Test
//    public void testSymEmptyDifference() {
//        Set set1 = new TreeSet();
//        set1.add("a");
//        set1.add("b");
//        
//        Set set2 = new TreeSet();
//        set2.add("a");
//        set2.add("b");
//        
//        Set symDifference = Sets.symDifference(set1, set2);
//        
//        assertNotNull(symDifference);
//        assertTrue(symDifference.isEmpty());
//    }
//    @Test
//    public void testSymDifferenceWithEmptySet() {
//        Set set1 = new TreeSet();
//        set1.add("a");
//        set1.add("b");
//        
//        Set set2 = new TreeSet();
//        
//        Set symDifference = Sets.symDifference(set1, set2);
//        
//        assertNotNull(symDifference);
//        assertEquals(set1, symDifference);
//    }
//    @Test
//    public void testSymDifferenceWithNullSet() {
//        Set set1 = new TreeSet();
//        set1.add("a");
//        set1.add("b");
//        
//        Set symDifference = Sets.symDifference(set1, null);
//        
//        assertNull(symDifference);
//    }
    
    
    
    @Test
    public void isEmpty() {
        Set set = new TreeSet();
        set.add("a");
        
        assertFalse(Sets.isEmpty(set));
    }
    
    @Test
    public void isEmptyEmptySet() {
        Set set = new TreeSet();
        
        assertTrue(Sets.isEmpty(set));
    }
    
    @Test
    public void isEmptyNullSet() {
        assertTrue(Sets.isEmpty(null));
    }
    
}
