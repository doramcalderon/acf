/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.benchmarks.RunBenchmarkModel;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.commons.files.FileUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Dora Calderón
 */
public class RunBenchmarkModelTest {
    
    public RunBenchmarkModelTest() {
    }

  

    /**
     * Test of getBenchmarks method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetAlgorithms() {
    }

    /**
     * Test of setBenchmarks method, of class RunBenchmarkModel.
     */
    @Test
    public void testSetAlgorithms() {
    }

    /**
     * Test of clear method, of class RunBenchmarkModel.
     */
    @Test
    public void testClear() {
    }

    /**
     * Test of getSelectedAlgorithms method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetSelectedAlgorithms() {
    }

    /**
     * Test of setSelectedAlgorithms method, of class RunBenchmarkModel.
     */
    @Test
    public void testSetSelectedAlgorithms() {
    }

    /**
     * Test of getInput method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetInput() {
    }

    /**
     * Test of setInput method, of class RunBenchmarkModel.
     */
    @Test
    public void testSetInput() {
    }

    /**
     * Test of getOutput method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetOutput() {
    }

    /**
     * Test of setOutput method, of class RunBenchmarkModel.
     */
    @Test
    public void testSetOutput() {
    }

    /**
     * Test of addTraceOutput method, of class RunBenchmarkModel.
     */
    @Test
    public void testAddTraceOutput() {
    }

    /**
     * Test of getTraceOutputs method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetTraceOutputs() {
    }

    /**
     * Test of setInputOutputs method, of class RunBenchmarkModel.
     */
    @Test
    public void testSetInputOutputs() throws IOException {
        String input = System.getProperty("user.dir") + File.separator + "test1.txt";
        String initConfig = UserConfig.get().getDefaultWorkspace();
        String testWorkspace = Paths.get(System.getProperty("user.dir") + "/src/test/resources").toString();
        UserConfig.get().setDefaultWorkspace(testWorkspace);
        
        try {
            FileUtils.createIfNoExists(input);
            
            GenericAlgorithm alg1 = new GenericAlgorithm(){

                @Override
                public ImplicationalSystem execute(ImplicationalSystem system) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getShortName() {
                    return "alg1";
                }
                
            };
            GenericAlgorithm alg2 = new GenericAlgorithm() {

                @Override
                public ImplicationalSystem execute(ImplicationalSystem system) {
                    return null;
                }
                
                @Override
                public String getShortName() {
                    return "alg2";
                }
            };

            List<Algorithm> algs = new ArrayList();
            algs.add(alg1);
            algs.add(alg2);

            RunBenchmarkModel model = new RunBenchmarkModel();
            model.inputProperty().set(input);
            model.setSelectedAlgorithms(algs);
            model.setInputOutputs();

            alg1 = (GenericAlgorithm) model.getSelectedAlgorithms().get(0);
            alg2 = (GenericAlgorithm) model.getSelectedAlgorithms().get(1);
            assertNotNull(algs);
            assertEquals(input, alg1.getInput());
            assertEquals(input, alg2.getInput());
            assertEquals(testWorkspace + File.separator +  "alg1_output.txt", alg1.getOutput());
            assertEquals(testWorkspace + File.separator +  "alg2_output.txt", alg2.getOutput());
        } finally {
            Files.deleteIfExists(Paths.get(input));
            UserConfig.get().setDefaultWorkspace(initConfig);
        }
    }
    /**
     * Test of setInputOutputs method, of class RunBenchmarkModel.
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

            RunBenchmarkModel model = new RunBenchmarkModel();
            model.inputProperty().set(input);
            model.outputProperty().set(output);
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
