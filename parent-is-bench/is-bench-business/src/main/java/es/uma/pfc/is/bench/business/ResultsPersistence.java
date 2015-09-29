package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @since @author Dora Calderón
 */
public class ResultsPersistence extends Persistence {

    /**
     * Single instance.
     */
    private static ResultsPersistence me;

    /**
     * Constructor.
     */
    private ResultsPersistence() {
    }

    /**
     * Get a single instance of ResultsPersistence.
     *
     * @return ResultsPersistence.
     */
    public static ResultsPersistence get() {
        synchronized (BenchmarksPersistence.class) {
            if (me == null) {
                me = new ResultsPersistence();
            }
        }
        return me;
    }

    /**
     * 
     * @param result Benchmark result to save.
     * @param path results.xml dir path.
     */
    public void save(BenchmarkResult result, String path) {
        if(result != null && !StringUtils.isEmpty(path)) {
            try {
                String resultsPath = Paths.get(path, "results.xml").toString();
                BenchmarkResultSet benchResultSet = read(resultsPath, BenchmarkResultSet.class);
                
                if(benchResultSet == null) {
                    benchResultSet = new BenchmarkResultSet();
                    benchResultSet.setName(result.getBenchmarkName());
                }
                
                benchResultSet.getResults().add(0, result);
                persist(benchResultSet, resultsPath, true);
            } catch (JAXBException ex) {
                Logger.getLogger(ResultsPersistence.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Returns a benchmark's saved results.
     * @param benchmark Benchmark.
     * @return Benchmark's saved results.{@code null} if the results not exists.
     */
    public BenchmarkResultSet getResults(Benchmark benchmark) {
        BenchmarkResultSet benchmarkResults = null;
        
        if(benchmark != null) {
            File benchDir = new File(benchmark.getBenchmarkPath());
            if (benchDir.exists()) {
                File resultsFile = Paths.get(benchDir.getPath(), "results.xml").toFile();
                if(resultsFile.exists()) {
                    benchmarkResults = read(resultsFile.getPath(), BenchmarkResultSet.class);
                }
            }
        }
        
        return benchmarkResults;
    }
}
