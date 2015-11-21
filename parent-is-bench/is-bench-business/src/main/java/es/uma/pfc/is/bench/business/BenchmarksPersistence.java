package es.uma.pfc.is.bench.business;



import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.domain.Algorithms;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.Benchmarks;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Persist the benchmarks into an XML file entities using JAXB.
 *
 * @author Dora Calderón
 */
public class BenchmarksPersistence extends Persistence {
    

    /**
     * Single instance.
     */
    private static BenchmarksPersistence me;

    /**
     * Constructor.
     */
    private BenchmarksPersistence() {
    }

    /**
     * Get a single instance of BenchmarksPersistence.
     *
     * @return BenchmarksPersistence.
     */
    public static BenchmarksPersistence get() {
        synchronized(BenchmarksPersistence.class) {
            if (me == null) {
                me = new BenchmarksPersistence();
            }
        }
        return me;
    }

    /**
     * Initialize the benchmarks file with {@code benchmarks} param.
     *
     * @param path Path of benchmarks.xml.
     * @param benchmark Algorithms.
     */
    public void update(Benchmark benchmark) {
        try {
            if (benchmark == null) {
                throw new IllegalArgumentException("benchmarks argument can't be null.");
            }
            Benchmarks benchmarks = getBenchmarks(benchmark.getWorkspace());
            if(benchmarks == null) {
                benchmarks = new Benchmarks();
            }
            benchmarks.removeBenchmark(benchmark.getName());
            benchmarks.addBenchmark(benchmark);
            
            String benchmarksFilePath = Paths.get(benchmark.getWorkspace(), "benchmarks.xml").toString();
            persist(benchmarks, benchmarksFilePath, true);
        } catch (JAXBException ex) {
            Logger.getLogger(BenchmarksPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    public Benchmarks getBenchmarks(String workspacePath) {
        Benchmarks benchmarks = null;
        Path benchmarksPath = Paths.get(workspacePath, "benchmarks.xml");
        if(Files.exists(benchmarksPath)) {
                benchmarks = JAXB.unmarshal(benchmarksPath.toFile(), Benchmarks.class);
            }
        return benchmarks;
    }


}
