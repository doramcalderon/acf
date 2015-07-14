package es.uma.pfc.is.bench.benchmarks;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
        inputProperty.addListener(new InputListener());
        outputProperty = new SimpleStringProperty();
        outputProperty.addListener(new OutputListener());
    }

    /**
     * Clear the model.
     */
    public void clear() {
        benchmarks.clear();
        inputProperty.setValue(null);
        outputProperty.setValue(null);
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
        updateOutputs();
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
        if (selectedBenchmark != null) {
            selectedAlgorithms = selectedBenchmark.getAlgorithms();
        } else if (selectedAlgorithm != null) {
            selectedAlgorithms.add(selectedAlgorithm);
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
        updateOutputs();
    }

    /**
     * Default output file for an algorithm.
     *
     * @param alg Algorithm.
     * @return Default output file.
     */
    public String getDefaultOutput(Algorithm alg) {
        String output = null;

        String workspace = (selectedBenchmark != null) ? selectedBenchmark.getOutputDir()
                : userConfig.getDefaultOutputDir().toString();
        if (alg != null) {
            output = Paths.get(workspace, alg.getShortName() + "_output.txt").toString();
        }
        return output;
    }

    protected void updateOutputs() {
        if (selectedAlgorithm != null) {
            outputProperty().setValue(getDefaultOutput(selectedAlgorithm));
        } else if (selectedBenchmark != null) {
            outputProperty().setValue(selectedBenchmark.getOutputDir());
        } else {
            outputProperty().setValue("");
        }
    }

    /**
     * Listener of inputProperty changes, wich update the inputs of all selected algorithms.
     */
    class InputListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldInput, String newInput) {
            if (!StringUtils.isEmpty(newInput)) {
                if (selectedBenchmark != null) {
                    selectedBenchmark.getAlgorithms().forEach(alg -> alg.input(newInput));
                } else {
                    selectedAlgorithm.input(newInput);
                }
            }
        }

    }

    /**
     * Listener of outputProperty changes, wich update the inputs of all selected algorithms.
     */
    class OutputListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldOutput, String newOutput) {
            if (selectedAlgorithm != null) {
                selectedAlgorithm.output(newOutput);
            } else {
                selectedBenchmark.getAlgorithms().forEach(
                        alg -> alg.output(Paths.get(newOutput, alg.getDefaultOutputFileName()).toString()));

            }
        }

    }
}
