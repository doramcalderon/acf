
package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.Benchmarks;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

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
     * Test of creation of benchmark.
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     */
    @Test
    public void testCreate() throws IOException, JAXBException {
        String workspace = Paths.get(System.getProperty("user.dir"), "target").toString();
        Files.deleteIfExists(Paths.get(workspace, "benchmarks.xml"));
        
        Benchmark benchmark = null;
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Alg 1");


        benchmark = new Benchmark("Bench1", Arrays.asList(alg));
        benchmark.setWorkspace(workspace);
        benchmark.setInputsDir(Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString());
        benchmark.setInputFiles(Arrays.asList(Paths.get(System.getProperty("user.dir"), "src", "test", "resources","implications.txt").toFile()));
        BenchmarksBean bean = new BenchmarksBean();
        bean.update(benchmark);


        Path benchmarksPath = Paths.get(benchmark.getWorkspace(), "benchmarks.xml");
        assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath())));
        assertTrue(Files.exists(Paths.get(benchmark.getInputsDir())));
        assertTrue(Files.exists(Paths.get(benchmark.getBenchmarkPath(), "output")));
        assertTrue(Files.exists(benchmarksPath));

        Benchmarks unmarshalBenchs = JAXB.unmarshal(benchmarksPath.toString(), Benchmarks.class);
        assertNotNull(unmarshalBenchs);
        assertEquals(1, unmarshalBenchs.getBenchmarks().size());

        Benchmark unmarshalBench = unmarshalBenchs.getBenchmarks().get(0);
        assertNotNull(unmarshalBench);
        assertEquals(benchmark.getName(), unmarshalBench.getName());
        assertEquals(benchmark.getInputsDir(), unmarshalBench.getInputsDir());
        assertEquals(benchmark.getBenchmarkPath(), unmarshalBench.getBenchmarkPath());
        assertArrayEquals(benchmark.getAlgorithmsEntities().toArray(), unmarshalBench.getAlgorithmsEntities().toArray());

       
    }

    /**
     * Test of exists method, of class BenchmarksBean.
     */
    @Test
    public void testExists() throws IOException {
        String workspace = Paths.get(System.getProperty("user.dir"), "src", "test").toString();
        String name = "BENCH1";
        try {
            Files.createDirectories(Paths.get(workspace, name));

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
