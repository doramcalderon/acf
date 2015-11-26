
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarksTest {
    

    /**
     * Test of getBenchmark method, of class Benchmarks.
     */
    @Test
    public void testGetBenchmark() {
        Benchmarks benchs = new Benchmarks();
        Benchmark b = new Benchmark("Bench 1", Arrays.asList(mock(AlgorithmInfo.class), mock(AlgorithmInfo.class)));
        Benchmark b2 = new Benchmark("Bench 2", Arrays.asList(mock(AlgorithmInfo.class)));
        benchs.addBenchmark(b);
        benchs.addBenchmark(b2);
        
        Benchmark result = benchs.getBenchmark("Bench 1");
        assertEquals(b, result);
    }
    @Test
    public void testGetBenchmark_NotFound() {
        Benchmarks benchs = new Benchmarks();
        Benchmark b = new Benchmark("Bench 1", Arrays.asList(mock(AlgorithmInfo.class), mock(AlgorithmInfo.class)));
        Benchmark b2 = new Benchmark("Bench 2", Arrays.asList(mock(AlgorithmInfo.class)));
        benchs.addBenchmark(b);
        benchs.addBenchmark(b2);
        
        Benchmark result = benchs.getBenchmark("Bench 3");
        assertNull(result);
    }

    /**
     * Test of removeBenchmark method, of class Benchmarks.
     */
    @Test
    public void testRemoveBenchmark() {
        Benchmarks benchs = new Benchmarks();
        Benchmark b = new Benchmark("Bench 1", Arrays.asList(mock(AlgorithmInfo.class), mock(AlgorithmInfo.class)));
        Benchmark b2 = new Benchmark("Bench 2", Arrays.asList(mock(AlgorithmInfo.class)));
        benchs.addBenchmark(b);
        benchs.addBenchmark(b2);
        
        benchs.removeBenchmark("Bench 1");
        assertNull(benchs.getBenchmark("Bench 1"));
    }
    @Test
    public void testRemoveBenchmark_NotFound() {
        Benchmarks benchs = new Benchmarks();
        Benchmark b = new Benchmark("Bench 1", Arrays.asList(mock(AlgorithmInfo.class), mock(AlgorithmInfo.class)));
        Benchmark b2 = new Benchmark("Bench 2", Arrays.asList(mock(AlgorithmInfo.class)));
        benchs.addBenchmark(b);
        benchs.addBenchmark(b2);
        
        benchs.removeBenchmark("Bench 3");
        assertNull(benchs.getBenchmark("Bench 3"));
        assertEquals(2, benchs.size());
    }
    
}
