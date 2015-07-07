
package es.uma.pfc.is.bench.benchmarks.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import java.io.File;
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
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg1");
        Benchmark b = new Benchmark("bench1", Arrays.asList(mock(Algorithm.class)));
        b.setWorkspace(workspace);
        
        String pathExpected = workspace + File.separator + b.getName();
        String benchmarkPath = b.getBenchmarkPath();
        
        assertEquals(pathExpected, benchmarkPath);
    }
    @Test(expected = RuntimeException.class)
    public void testGetBenchmarkPath_NoWorkspace() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg1");
        
        Benchmark b = new Benchmark("bench1", Arrays.asList(mock(Algorithm.class)));
        b.getBenchmarkPath();
        
        fail("Expected RuntimeExcepton.");
    }
    
}
