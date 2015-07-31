package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Benchmarks execution model.
 *
 * @author Dora Calderón
 */
public class RunBenchmarkModel {

    /**
     * Available benchmarks.
     */
    private List<Benchmark> benchmarks;
    /**
     * Selected benchmark.
     */
    private Benchmark selectedBenchmark;
    /**
     * When the selection is an algorithm.
     */
    private Algorithm selectedAlgorithm;
    /**
     * Path input file property.
     */
    private final StringProperty inputProperty;

    /**
     * Path output file property.*
     */
    private final StringProperty outputProperty;

    private final UserConfig userConfig;

    /**
     * Constructor.
     */
    public RunBenchmarkModel() {
        benchmarks = new ArrayList();
        userConfig = UserConfig.get();
        inputProperty = new SimpleStringProperty();
        outputProperty = new SimpleStringProperty();
    }


    /**
     * Available algorithms.
     *
     * @return Algorithms.
     */
    public List<Benchmark> getBenchmarks() {
        return benchmarks;
    }

    /**
     * Set the available algorithms.
     *
     * @param benchmarks Algorithms.
     */
    public void setBenchmarks(List<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public void setSelectedAlgorithm(Algorithm selectedAlgorithm) {
        this.selectedAlgorithm = selectedAlgorithm;
    }

    /**
     * Selected algorithms.
     *
     * @return Algorithms list.
     */
    public Algorithm getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    /**
     * Selected algorithms.
     *
     * @return List of selected algorithms.
     */
    public List<Algorithm> getSelectedAlgorithms() {
        List<Algorithm> selectedAlgorithms = new ArrayList();
        
        if (selectedAlgorithm != null) {
            selectedAlgorithms.add(selectedAlgorithm);
        } else if (selectedBenchmark != null) {
            selectedAlgorithms = selectedBenchmark.getAlgorithms();    
        }
        return selectedAlgorithms;
    }

    /**
     * Path input file property.
     *
     * @return StringProperty.
     */
    public StringProperty inputProperty() {
        return inputProperty;
    }

    /**
     * Path input file.
     *
     * @return the input.
     */
    public String getInput() {
        return (inputProperty != null) ? inputProperty.get() : null;
    }

    /**
     * Path input file property.
     *
     * @return StringProperty.
     */
    public StringProperty outputProperty() {
        return outputProperty;
    }

    /**
     * Path output file.
     *
     * @return the outputFile
     */
    public String getOutput() {
        return (outputProperty != null) ? outputProperty.get() : null;
    }

    /**
     * Sets the selected benchmark.
     *
     * @param benchmark Benchmark.
     */
    public void setSelectedBenchmark(Benchmark benchmark) {
        this.selectedBenchmark = benchmark;
    }

    /**
     * Default output file for an algorithm.
     *
     * @param alg Algorithm.
     * @return Default output file.
     */
    public String getDefaultOutput(Algorithm alg) {
        String output;

        String workspace = (selectedBenchmark != null) ? selectedBenchmark.getOutputDir()
                : userConfig.getDefaultOutputDir().toString();
        if (alg != null) {
            output = Paths.get(workspace, alg.getShortName() + "_output.txt").toString();
        } else {
            output = workspace;
        }
        return output;
    }

}
