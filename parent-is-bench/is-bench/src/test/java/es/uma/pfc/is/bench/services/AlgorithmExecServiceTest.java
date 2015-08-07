/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import fr.kbertet.lattice.ImplicationalSystem;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmExecServiceTest {
    
    public AlgorithmExecServiceTest() {
    }

    /**
     * Test of instanceAlgorithms method, of class AlgorithmExecService.
     */
    @Test
    public void testInstanceAlgorithms() {
    }

    /**
     * Test of instanceAlgorithm method, of class AlgorithmExecService.
     */
    @Test
    public void testInstanceAlgorithm() {
    }

    /**
     * Test of setOutput method, of class AlgorithmExecService.
     */
    @Test
    public void testSetOutput() {
        String outputExpected = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "result.txt").toString();
        GenericAlgorithm alg = new GenericAlgorithm() {

            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.setSelectedAlgorithm(new AlgorithmEntity());
        model.outputProperty().setValue(outputExpected);
        
        AlgorithmExecService service = new AlgorithmExecService(model);
        String output = service.getOutput(alg);
        
        assertEquals(outputExpected, output);
    }
    @Test
    public void testSetOutput_InBenchmark() {
        GenericAlgorithm alg = new GenericAlgorithm() {

            @Override
            public String getShortName() {
                return "short";
            }

            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        String outputExpected = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();
        
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.outputProperty().setValue(outputExpected);
        
        AlgorithmExecService service = new AlgorithmExecService(model);
        String output = service.getOutput(alg);
        
        String expectedOutput = Paths.get(model.getOutput(), alg.getDefaultOutputFileName()).toString();
        assertEquals(expectedOutput, output);
    }

    /**
     * Test of getOptions method, of class AlgorithmExecService.
     */
    @Test
    public void testGetOptions_History() {
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.historyCheckedProperty().setValue(Boolean.TRUE);
        
        GenericAlgorithm alg = new GenericAlgorithm() {
            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        AlgorithmExecService service = new AlgorithmExecService(model);
        AlgorithmOptions options = service.getOptions();
        
        assertNotNull(options);
        assertTrue(options.getOption(Mode.HISTORY.toString()));
        assertNull(options.getOption(Mode.PERFORMANCE.toString()));
        assertNull(options.getOption(Mode.STATISTICS.toString()));
    }
    @Test
    public void testGetOptions_Time() {
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.timeCheckedProperty().setValue(Boolean.TRUE);
        
        GenericAlgorithm alg = new GenericAlgorithm() {
            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        AlgorithmExecService service = new AlgorithmExecService(model);
        AlgorithmOptions options = service.getOptions();
        
        assertNotNull(options);
        assertNull(options.getOption(Mode.HISTORY.toString()));
        assertTrue(options.getOption(Mode.PERFORMANCE.toString()));
        assertNull(options.getOption(Mode.STATISTICS.toString()));
    }
    @Test
    public void testGetOptions_Statistics() {
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.statisticsCheckedProperty().setValue(Boolean.TRUE);
        
        GenericAlgorithm alg = new GenericAlgorithm() {
            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        AlgorithmExecService service = new AlgorithmExecService(model);
        AlgorithmOptions options = service.getOptions();
        
        assertNotNull(options);
        assertNull(options.getOption(Mode.HISTORY.toString()));
        assertNull(options.getOption(Mode.PERFORMANCE.toString()));
        assertTrue(options.getOption(Mode.STATISTICS.toString()));
    }

    /**
     * Test of createTask method, of class AlgorithmExecService.
     */
    @Test
    public void testCreateTask() {
    }

    /**
     * Test of setOnFinished method, of class AlgorithmExecService.
     */
    @Test
    public void testSetOnFinished() {
    }

    /**
     * Test of failed method, of class AlgorithmExecService.
     */
    @Test
    public void testFailed() {
    }

    /**
     * Test of succeeded method, of class AlgorithmExecService.
     */
    @Test
    public void testSucceeded() {
    }
    
}