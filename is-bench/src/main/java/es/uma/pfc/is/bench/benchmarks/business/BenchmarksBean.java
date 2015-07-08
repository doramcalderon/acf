

package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsBean;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsPersistence;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business logic for insert, modify and delete algorithms.
 * @author Dora Calderón
 */
public class BenchmarksBean {
    private BenchmarksPersistence persistence;
    private AlgorithmsPersistence algorithmsPersistence;

    public BenchmarksBean() {
        persistence = BenchmarksPersistence.get();
        algorithmsPersistence = AlgorithmsPersistence.get();
    }
    
    /**
     * Return the algorithms defined in the algorithms file.
     * @return Algorithm Entities list.
     */
    public List<Benchmark> getBenchmarks() {
        List<Benchmark> algorithms = null;
        
        return algorithms;
    }
    
    /**
     * Create the directory tree of benchmark.
     * @param benchmark Benchmark.
     * @throws java.io.IOException
     */
    public void create(Benchmark benchmark) throws IOException {
        if(benchmark != null) {            
            Files.createDirectories(Paths.get(benchmark.getBenchmarkPath() + File.separator + "input"));
            Files.createDirectories(Paths.get(benchmark.getBenchmarkPath() + File.separator + "output"));

            List<AlgorithmEntity> algsEntities = new ArrayList<>();
            benchmark.getAlgorithms().stream().forEach((alg) -> {
                algsEntities.add(new AlgorithmEntity(alg));
            });
            Algorithms algs = new Algorithms(algsEntities);
            algorithmsPersistence.create(benchmark.getBenchmarkPath(), algs);
        }
        
    }

    /**
     * If exists a benchmark with the name argument in a workspace.
     * @param name Benchmark name.
     * @param workspace Workspace path.
     * @return {@code true} if exists a benchmark with the name argument, {@code false} otherwise.
     */
    public boolean exists(String name, String workspace) {
        boolean exists = false;
        
        if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(workspace)) {
            exists = Files.exists(Paths.get(workspace.concat(File.separator).concat(name)));
        }
        
        return exists;
    }
    
 
    
    /**
     * For testing usage.
     * @param persistence 
     */
    protected void setPersistence(BenchmarksPersistence persistence) {
        this.persistence = persistence;
    }
}
