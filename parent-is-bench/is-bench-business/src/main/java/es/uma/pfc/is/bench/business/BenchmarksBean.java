package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.Benchmarks;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business logic for insert, modify and delete algorithms.
 *
 * @author Dora Calderón
 */
public class BenchmarksBean {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(BenchmarksBean.class);
    /**
     * Benchmarks persistence.
     */
    private BenchmarksPersistence persistence;

    /**
     * Constructor.
     */
    public BenchmarksBean() {
        persistence = BenchmarksPersistence.get();
    }

    /**
     * Returns a workspace registered benchmarks.
     *
     * @param workspace Workspace path.
     * @return Algorithm Entities list.
     * @throws java.lang.Exception
     */
    public List<Benchmark> getBenchmarks(String workspace) throws Exception {
        List<Benchmark> benchmarksList = null;
        try {
            Benchmarks benchmarks = persistence.getBenchmarks(workspace);
            if (benchmarks != null) {
                benchmarksList = benchmarks.getBenchmarks();
            }
        } catch (Exception ex) {
            logger.error("Error consulting benchmarks.", ex);
        }
        return benchmarksList;
    }
    /**
     * Returns a benchmark registered in a workspace. {@code null} if no exists.
     *
     * @param name Benchmark name.
     * @param workspace Workspace path.
     * @return Algorithm Entities list.
     * @throws java.lang.Exception
     */
    public Benchmark getBenchmark(String name, String workspace) throws Exception {
        Benchmark benchmark = null;
        List<Benchmark> benchmarksList;
        try {
            Benchmarks benchmarks = persistence.getBenchmarks(workspace);
            if (benchmarks != null) {
                benchmarksList = benchmarks.getBenchmarks();
                benchmark = benchmarksList.stream()
                                          .filter(b -> b.getName().equals(name))
                                          .findFirst().orElse(null);
            }
        } catch (Exception ex) {
            logger.error("Error consulting benchmarks.", ex);
        }
        return benchmark;
    }

    /**
     * Create the directory tree of benchmark.
     *
     * @param benchmark Benchmark.
     * @throws java.io.IOException
     */
    public void update(Benchmark benchmark) throws IOException {
        if (benchmark != null) {
            String inputDir = benchmark.getInputsDir();
            Files.createDirectories(Paths.get(benchmark.getBenchmarkPath(), "output"));
            FileUtils.createDirIfNoExists(inputDir);

            Path targetInputFile, inputFilePath;

            for (File inputFile : benchmark.getInputFiles()) {
                inputFilePath = inputFile.toPath();
                targetInputFile = Paths.get(inputDir, inputFile.getName());

                if (inputFile.exists()) {
                    if (!inputFilePath.toString().equals(targetInputFile.toString())) {
                        Files.copy(inputFilePath, targetInputFile,
                                StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
                        
                    }
                } else {
                    throw new RuntimeException("The input system not exists.");
                }
            }

            persistence.update(benchmark);
        }

    }

    /**
     * If exists a benchmark with the name argument in a workspace.
     *
     * @param name Benchmark name.
     * @param workspace Workspace path.
     * @return {@code true} if exists a benchmark with the name argument, {@code false} otherwise.
     */
    public boolean exists(String name, String workspace) {
        boolean exists = false;

        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(workspace)) {
            exists = Files.exists(Paths.get(workspace, name));
        }

        return exists;
    }

    /**
     * For testing usage.
     *
     * @param persistence
     */
    protected void setPersistence(BenchmarksPersistence persistence) {
        this.persistence = persistence;
    }
}
