/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.commons.files.FileUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Test of clear method, of class BenchModel.
     */
    @Test
    public void testClear() {
    }

    /**
     * Test of getSelectedAlgorithms method, of class BenchModel.
     */
    @Test
    public void testGetSelectedAlgorithms() {
    }

    /**
     * Test of setSelectedAlgorithms method, of class BenchModel.
     */
    @Test
    public void testSetSelectedAlgorithms() {
    }

    /**
     * Test of getInput method, of class BenchModel.
     */
    @Test
    public void testGetInput() {
    }

    /**
     * Test of setInput method, of class BenchModel.
     */
    @Test
    public void testSetInput() {
    }

    /**
     * Test of getOutput method, of class BenchModel.
     */
    @Test
    public void testGetOutput() {
    }

    /**
     * Test of setOutput method, of class BenchModel.
     */
    @Test
    public void testSetOutput() {
    }

    /**
     * Test of addTraceOutput method, of class BenchModel.
     */
    @Test
    public void testAddTraceOutput() {
    }

    /**
     * Test of getTraceOutputs method, of class BenchModel.
     */
    @Test
    public void testGetTraceOutputs() {
    }

    /**
     * Test of setInputOutputs method, of class BenchModel.
     */
    @Test
    public void testSetInputOutputs() throws IOException {
         String input = System.getProperty("user.dir") + File.separator + "test1.txt";
        String output = System.getProperty("user.dir") + File.separator +  "test_output.txt";
        try {
            FileUtils.createIfNoExists(input);
            FileUtils.createIfNoExists(output);

            GenericAlgorithm alg1 = new GenericAlgorithm(){

                @Override
                public ImplicationalSystem execute(ImplicationalSystem system) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            };
            GenericAlgorithm alg2 = new GenericAlgorithm() {

                @Override
                public ImplicationalSystem execute(ImplicationalSystem system) {
                    return null;
                }
            };

            List<Algorithm> algs = new ArrayList();
            algs.add(alg1);
            algs.add(alg2);

            BenchModel model = new BenchModel();
            model.setInput(input);
            model.setOutput(output);
            model.setSelectedAlgorithms(algs);
            model.setInputOutputs();

            alg1 = (GenericAlgorithm) model.getSelectedAlgorithms().get(0);
            alg2 = (GenericAlgorithm) model.getSelectedAlgorithms().get(1);
            assertNotNull(algs);
            assertEquals(input, alg1.getInput());
            assertEquals(input, alg2.getInput());
            assertEquals(System.getProperty("user.dir") + File.separator +  "test_output_1.txt", alg1.getOutput());
            assertEquals(System.getProperty("user.dir") + File.separator +  "test_output_2.txt", alg2.getOutput());
        } finally {
            Files.deleteIfExists(Paths.get(input));
            Files.deleteIfExists(Paths.get(output));
        }
    }
    /**
     * Test of setInputOutputs method, of class BenchModel.
     */
    @Test
    public void testSetInputOutputs_OneAlg() throws IOException {
         String input = System.getProperty("user.dir") + File.separator + "test1.txt";
        String output = System.getProperty("user.dir") + File.separator +  "test_output.txt";
        try {
            FileUtils.createIfNoExists(input);
            FileUtils.createIfNoExists(output);

            GenericAlgorithm alg1 = new GenericAlgorithm(){

                @Override
                public ImplicationalSystem execute(ImplicationalSystem system) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            };

            List<Algorithm> algs = new ArrayList();
            algs.add(alg1);

            BenchModel model = new BenchModel();
            model.setInput(input);
            model.setOutput(output);
            model.setSelectedAlgorithms(algs);
            model.setInputOutputs();

            alg1 = (GenericAlgorithm) model.getSelectedAlgorithms().get(0);
            
            assertNotNull(algs);
            assertEquals(input, alg1.getInput());
            assertEquals(output, alg1.getOutput());
            
        } finally {
            Files.deleteIfExists(Paths.get(input));
            Files.deleteIfExists(Paths.get(output));
        }
    }
    
}
