/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Dora Calderón
 */
public class BenchModelTest {
    
    public BenchModelTest() {
    }

    /**
     * Test of addAlgorithm method, of class BenchModel.
     */
    @Test
    public void testAddAlgorithm() {
        Algorithm alg = mock(Algorithm.class);
        BenchModel model = new BenchModel();
        model.addAlgorithm(alg);
        
        assertNotNull(model.getAlgorithms());
        assertEquals(1, model.getAlgorithms().size());
        assertTrue(model.getAlgorithms().contains(alg));
    }
    /**
     * Test of addAlgorithm method, of class BenchModel.
     */
    @Test(expected = NullPointerException.class)
    public void testAddNullAlgorithm() {
        Algorithm alg = null;
        BenchModel model = new BenchModel();
        model.addAlgorithm(alg);
        fail("Se esperaba NullPointerException.");
    }
    /**
     * Test of addAlgorithm method, of class BenchModel.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAlgorithmToNullList() {
        Algorithm alg = null;
        BenchModel model = new BenchModel();
        model.setAlgorithms(null);
        model.addAlgorithm(alg);
        
        assertNotNull(model.getAlgorithms());
        assertEquals(1, model.getAlgorithms().size());
        assertTrue(model.getAlgorithms().contains(alg));
    }

    /**
     * Test of getAlgorithms method, of class BenchModel.
     */
    @Test
    public void testGetAlgorithms() {
    }

    /**
     * Test of setAlgorithms method, of class BenchModel.
     */
    @Test
    public void testSetAlgorithms() {
    }
    
}
