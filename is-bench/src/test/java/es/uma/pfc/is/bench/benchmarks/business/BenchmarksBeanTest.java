
package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarksBeanTest {

    public BenchmarksBeanTest() {
    }

    /**
     * Test of getBenchmarks method, of class BenchmarksBean.
     */
    @Test
    public void testGetBenchmarks() {
    }

    /**
     * Test of create method, of class BenchmarksBean.
     */
    @Test
    public void testCreate() throws IOException {
        Benchmark benchmark = null;
        try {
            String workspace = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test";
            Algorithm alg = mock(Algorithm.class);
            when(alg.getName()).thenReturn("Alg1");

            benchmark = new Benchmark(workspace, "Bench1", Arrays.asList(alg));
            BenchmarksBean bean = new BenchmarksBean();
            bean.create(benchmark);

            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath())));
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "input")));
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "output")));
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "algorithms.xml")));
        } finally {
            if(benchmark != null) {
                Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "algorithms.xml"));
                Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "input"));
                Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath() + File.separator + "output"));
                Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath()));
            }
        }
    }

    /**
     * Test of exists method, of class BenchmarksBean.
     */
    @Test
    public void testExists() throws IOException {
        String workspace = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test";
        String name = "BENCH1";
        try {
            Files.createDirectories(Paths.get(workspace.concat(File.separator).concat(name)));

            BenchmarksBean bean = new BenchmarksBean();
            assertTrue(bean.exists(name, workspace));
        } finally {
            Files.deleteIfExists(Paths.get(workspace.concat(File.separator).concat(name)));
        }
    }
    @Test
    public void testNoExists() throws IOException {
        String workspace = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test";
        String name = "BENCH2";
        
        BenchmarksBean bean = new BenchmarksBean();
        assertFalse(bean.exists(name, workspace));

    }

}