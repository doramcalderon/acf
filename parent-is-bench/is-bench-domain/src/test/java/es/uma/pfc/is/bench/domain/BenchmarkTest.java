
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *  Tests of {@link Benchmark} class.
 * @author Dora Calderón
 */
public class BenchmarkTest {
    
    public BenchmarkTest() {
    }

    /**
     * Test of getName method, of class Benchmark.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of getAlgorithms method, of class Benchmark.
     */
    @Test
    public void testGetAlgorithms() {
    }

    /**
     * Test of getWorkspace method, of class Benchmark.
     */
    @Test
    public void testGetWorkspace() {
    }

    /**
     * Test of setWorkspace method, of class Benchmark.
     */
    @Test
    public void testSetWorkspace() {
    }

    /**
     * Test of getBenchmarkPath method, of class Benchmark.
     */
    @Test
    public void testGetBenchmarkPath() {
        String workspace = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test";
        AlgorithmEntity alg = mock(AlgorithmEntity.class);
        when(alg.getName()).thenReturn("Alg1");
//        when(alg.getDefaultOutputFileName()).thenReturn("a1_output.txt");
        Benchmark b = new Benchmark("bench1", Arrays.asList(alg));
        b.setWorkspace(workspace);
        
        String pathExpected = Paths.get(workspace, b.getName()).toString();
        String benchmarkPath = b.getBenchmarkPath();
        
        assertEquals(pathExpected, benchmarkPath);
    }
    @Test(expected = RuntimeException.class)
    public void testGetBenchmarkPath_NoWorkspace() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg1");
        
        Benchmark b = new Benchmark("bench1", Arrays.asList(mock(AlgorithmEntity.class)));
        b.getBenchmarkPath();
        
        fail("Expected RuntimeExcepton.");
    }
    
}
