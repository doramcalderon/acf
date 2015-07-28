

package es.uma.pfc.is.bench.benchmarks.business;


import es.uma.pfc.is.bench.algorithms.business.AlgorithmsPersistence;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.benchmarks.domain.BenchmarkEntity;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmarks;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business logic for insert, modify and delete algorithms.
 * @author Dora Calderón
 */
public class BenchmarksBean {
    private BenchmarksPersistence persistence;
    private final AlgorithmsPersistence algorithmsPersistence;

    public BenchmarksBean() {
        persistence = BenchmarksPersistence.get();
        algorithmsPersistence = AlgorithmsPersistence.get();
    }
    
    /**
     * Return the algorithms defined in the algorithms file.
     * @param workspace
     * @return Algorithm Entities list.
     * @throws java.lang.Exception
     */
    public List<Benchmark> getBenchmarks(String workspace) throws Exception {
        List<Benchmark> benchmarks = null;
        try {
            benchmarks = persistence.getBenchmarks(workspace);
        } catch (Exception ex) {
            Logger.getLogger(BenchmarksBean.class.getName()).log(Level.SEVERE, "Error consulting benchmarks.", ex);
        }
        return benchmarks;
    }
    
    /**
     * Create the directory tree of benchmark.
     * @param benchmark Benchmark.
     * @throws java.io.IOException
     */
    public void create(Benchmark benchmark) throws IOException {
        if(benchmark != null) {            
            Files.createDirectories(Paths.get(benchmark.getBenchmarkPath(), "input"));
            Files.createDirectories(Paths.get(benchmark.getBenchmarkPath(), "output"));

            List<AlgorithmEntity> algsEntities = new ArrayList<>();
            benchmark.getAlgorithms().stream().forEach((alg) -> {
                algsEntities.add(new AlgorithmEntity(alg));
            });
            //Algorithms algs = new Algorithms(algsEntities);
            // algorithmsPersistence.create(benchmark.getBenchmarkPath(), algs);
            Benchmarks benchs = new Benchmarks();
            BenchmarkEntity benchEntity = new BenchmarkEntity(benchmark.getName());
            //benchEntity.setInput(benchmark.getInput());
            benchEntity.setAlgorithms(new Algorithms(algsEntities));
            benchs.setBenchmarks(Arrays.asList(benchEntity));
            
            persistence.create(benchmark.getBenchmarkPath(), benchs);
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
            exists = Files.exists(Paths.get(workspace, "benchmarks", name));
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
