
package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
    public void testCreate() throws IOException, JAXBException {
        Benchmark benchmark = null;
        try {
            String workspace = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test";
            Algorithm alg = mock(Algorithm.class);
            when(alg.getName()).thenReturn("Alg1");
            when(alg.getDefaultOutputFileName()).thenReturn("a1_output.txt");

            benchmark = new Benchmark(workspace, "Bench1", Arrays.asList(alg));
            benchmark.setInput("");
            BenchmarksBean bean = new BenchmarksBean();
            bean.create(benchmark);

            
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath())));
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath(), "input")));
            assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath(), "output")));
            assertTrue(Files.exists(Paths.get(benchmark.getWorkspace(), "benchmarks.xml")));
            
            
            Benchmark unmarshalBench = (Benchmark) JAXBContext.newInstance(Benchmark.class)
                    .createUnmarshaller().unmarshal(Paths.get(benchmark.getWorkspace(), "benchmarks.xml").toFile());
            assertNotNull(unmarshalBench);
            assertEquals(benchmark.getName(), unmarshalBench.getName());
            assertEquals(benchmark.getBenchmarkPath(), unmarshalBench.getBenchmarkPath());
            assertArrayEquals(benchmark.getAlgorithmsClasses().toArray(), unmarshalBench.getAlgorithmsClasses().toArray());
            
        } finally {
            if(benchmark != null) {
                try {
                    Files.deleteIfExists(Paths.get(benchmark.getWorkspace(), "benchmarks.xml"));
                    Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath(), "input"));
                    Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath(), "output"));
                    Files.deleteIfExists(Paths.get(benchmark.getBenchmarkPath()));
                } catch (IOException ex) {
                    
                }
            }
        }
    }

    /**
     * Test of exists method, of class BenchmarksBean.
     */
    @Test
    public void testExists() throws IOException {
        String workspace = Paths.get(System.getProperty("user.dir"), "src", "test").toString();
        String name = "BENCH1";
        try {
            Files.createDirectories(Paths.get(workspace, "benchmarks", name));

            BenchmarksBean bean = new BenchmarksBean();
            assertTrue(bean.exists(name, workspace));
        } finally {
            Files.deleteIfExists(Paths.get(workspace, name));
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
