
package es.uma.pfc.is.bench.benchmarks;

import es.uma.pfc.is.algorithms.GenericAlgorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Dora Calderón
 */
@RunWith(MockitoJUnitRunner.class)
public class RunBenchmarkModelTest {
    
    /**
     * Test of getDefaultOutput method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetDefaultOutput() {
        String workspace = Paths.get(System.getProperty("user.dir"), "src","test").toString();
        
        GenericAlgorithm alg = mock(GenericAlgorithm.class);
        when(alg.getShortName()).thenReturn("do");
        when(alg.getDefaultOutputFileName()).thenCallRealMethod();
        Benchmark selectedBenchmark = new Benchmark(workspace, "Test Bench", Arrays.asList(alg));
        String outputExpected = Paths.get(selectedBenchmark.getOutputDir(), "do_output.txt").toString();
                
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.setSelectedBenchmark(selectedBenchmark);
        model.setSelectedAlgorithm(alg);
        String output = model.getDefaultOutput(alg);
        
        assertEquals(outputExpected, output);
    }

    /**
     * Test of getDefaultOutput method, of class RunBenchmarkModel.
     */
    @Test
    public void testGetDefaultOutput_Benchmark() {
        String workspace = Paths.get(System.getProperty("user.dir"), "src","test").toString();
        GenericAlgorithm alg = mock(GenericAlgorithm.class);
        when(alg.getDefaultOutputFileName()).thenCallRealMethod();
        
        Benchmark selectedBenchmark = new Benchmark(workspace, "Test Bench", Arrays.asList(alg));
        String outputExpected = Paths.get(workspace, "benchmarks", "Test Bench", "output").toString();
                
        RunBenchmarkModel model = new RunBenchmarkModel();
        model.setSelectedBenchmark(selectedBenchmark);
        String output = model.getDefaultOutput(null);
        
        assertEquals(outputExpected, output);
    }
    
}
