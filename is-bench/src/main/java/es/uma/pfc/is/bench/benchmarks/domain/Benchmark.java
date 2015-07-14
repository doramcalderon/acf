/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @since @author Dora Calderón
 */
public class Benchmark {

    /**
     * Workspace.
     */
    private String workspace;
    /**
     * Name.
     */
    private String name;
    /**
     * Algorithms.
     */
    private List<Algorithm> algorithms;

    /**
     * Constructor.
     *
     * @param workspace
     * @param name Name.
     * @param algorithms Algorithms.
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String workspace, String name, List<Algorithm> algorithms) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("The name is required.");
        }
        if (algorithms == null || algorithms.isEmpty()) {
            throw new IllegalArgumentException("The list algorithms is required.");
        }
        this.name = name;
        this.workspace = workspace;
        this.algorithms = setAlgorithmsOutput(algorithms);
    }

    /**
     * Constructor.
     *
     * @param name Name.
     * @param algorithms Algorithms.
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String name, List<Algorithm> algorithms) {
        this(UserConfig.get().getDefaultWorkspace(), name, algorithms);
    }

    /**
     * Sets the algorithms output to the default output file in the benchmark output dir.
     * @param algorithms Algorithms.
     * @return List of algorithms.
     */
    protected final List<Algorithm> setAlgorithmsOutput(List<Algorithm> algorithms) {
        if (algorithms != null) {
            algorithms.forEach((alg) -> {
                alg.output(Paths.get(getOutputDir(), alg.getDefaultOutputFileName()).toString());
            });
        }
        return algorithms;
    }
    
    /**
     * Sets the algorithms input.
     * @param inputFilePath Input file path.
     */
    public void setInput(String inputFilePath) {
        if(!StringUtils.isEmpty(inputFilePath) && algorithms != null) {
            algorithms.forEach((alg) -> {alg.input(inputFilePath);});
        }
    }

    /**
     * Name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Algorithms.
     *
     * @return the algorithms
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Workspace.
     *
     * @return the workspace
     */
    public String getWorkspace() {
        return workspace;
    }

    /**
     * Workspace.
     *
     * @param workspace the workspace to set
     */
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
        setAlgorithmsOutput(algorithms);
    }

    /**
     * The benchmark path.
     *
     * @return Benchmark path.
     */
    public String getBenchmarkPath() {
        if (StringUtils.isEmpty(workspace)) {
            throw new RuntimeException("The workspace isn't established.");
        }

        return Paths.get(workspace, "benchmarks", name).toString();
    }

    /**
     * Path of output dir of benchmark.
     *
     * @return Path.
     */
    public String getOutputDir() {
        return Paths.get(getBenchmarkPath(), "output").toString();
    }

    /**
     * Path of input dir of benchmark.
     *
     * @return Path.
     */
    public String getInputDir() {
        return Paths.get(getBenchmarkPath(), "input").toString();
    }

    @Override
    public String toString() {
        return getName();
    }

}
