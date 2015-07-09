/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
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
     * @param workspace
     * @param name Name.
     * @param algorithms Algorithms. 
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String workspace, String name, List<Algorithm> algorithms) {
        this(name, algorithms);
        this.workspace = workspace;
    }
    
    /**
     * Constructor.
     * @param name Name.
     * @param algorithms Algorithms. 
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String name, List<Algorithm> algorithms) {
        if(StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("The name is required.");
        }
        if(algorithms == null || algorithms.isEmpty()) {
            throw new IllegalArgumentException("The list algorithms is required.");
        }
        this.name = name;
        this.algorithms = algorithms;
    }

    /**
     * Name.
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Algorithms.
     * @return the algorithms
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Workspace.
     * @return the workspace
     */
    public String getWorkspace() {
        return workspace;
    }

    /**
     * Workspace.
     * @param workspace the workspace to set
     */
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    /**
     * The benchmark path.
     * @return Benchmark path.
     */
    public String getBenchmarkPath() {
        if(StringUtils.isEmpty(workspace)) {
            throw new RuntimeException("The workspace it isn't established.");
        }
        
        return new StringBuilder(workspace).append(File.separator)
                         .append("benchmarks").append(File.separator)
                         .append(name).toString();
    }
    /**
     * Path of output dir of benchmark.
     * @return Path.
     */
    public String getOutputPath() {
        return getBenchmarkPath().concat(File.separator).concat("output");
    }
    /**
     * Path of input dir of benchmark.
     * @return Path.
     */
    public String getInputPath() {
        return getBenchmarkPath().concat(File.separator).concat("input");
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
    
    
}
