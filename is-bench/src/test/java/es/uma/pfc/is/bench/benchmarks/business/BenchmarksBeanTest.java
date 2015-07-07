/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
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
     * Test of insert method, of class BenchmarksBean.
     */
    @Test
    public void testInsert() {
    }

    /**
     * Test of delete method, of class BenchmarksBean.
     */
    @Test
    public void testDelete() {
    }

    /**
     * Test of setPersistence method, of class BenchmarksBean.
     */
    @Test
    public void testSetPersistence() {
    }

}
