package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private AlgorithmEntity selectedAlgorithm;
    /**
     * Path input file property.
     */
    private final StringProperty inputProperty;

    /**
     * Path output file property.*
     */
    private final StringProperty outputProperty;
    /**
     * If the history mode is checked.
     */
    private final BooleanProperty historyChecked;
    /**
     * If the Time mode is checked.
     */
    private final BooleanProperty timeChecked;
    /**
     * If the Statistics mode is checked.
     */
    private final BooleanProperty statisticsChecked;

    /**
     * Constructor.
     */
    public RunBenchmarkModel() {
        benchmarks = new ArrayList();
        inputProperty = new SimpleStringProperty();
        outputProperty = new SimpleStringProperty();
        historyChecked = new SimpleBooleanProperty();
        timeChecked = new SimpleBooleanProperty();
        statisticsChecked = new SimpleBooleanProperty();
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

    public void setSelectedAlgorithm(AlgorithmEntity selectedAlgorithm) {
        this.selectedAlgorithm = selectedAlgorithm;
    }

    /**
     * Selected algorithms.
     *
     * @return Algorithms list.
     */
    public AlgorithmEntity getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    /**
     * Selected algorithms.
     *
     * @return List of selected algorithms.
     */
    public List<AlgorithmEntity> getSelectedAlgorithms() {
        List<AlgorithmEntity> selectedAlgorithms = new ArrayList();

        if (selectedAlgorithm != null) {
            selectedAlgorithms.add(selectedAlgorithm);
        } else if (selectedBenchmark != null) {
            selectedAlgorithms = selectedBenchmark.getAlgorithmsEntities();
        }
        return selectedAlgorithms;
    }
    
    public Benchmark getSelectedBenchmark() {
        return selectedBenchmark;
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
     * If the history mode is checked.
     *
     * @return the historyChecked
     */
    public BooleanProperty historyCheckedProperty() {
        return historyChecked;
    }

    /**
     * If the history mode is checked.
     *
     * @return {@code true} if the history mode is checked, {@code false} otherwise.
     */
    public boolean isHistoryChecked() {
        return (historyChecked != null) ? historyChecked.get() : false;
    }

    /**
     * If the Time mode is checked.
     *
     * @return the timeChecked
     */
    public BooleanProperty timeCheckedProperty() {
        return timeChecked;
    }

    /**
     * If the Time mode is checked.
     *
     * @return {@code true} if the Time mode is checked, {@code false} otherwise.
     */
    public boolean isTimeChecked() {
        return (timeChecked != null) ? timeChecked.get() : false;
    }

    /**
     * If the Statistics mode is checked.
     *
     * @return the statisticsChecked
     */
    public BooleanProperty statisticsCheckedProperty() {
        return statisticsChecked;
    }

    /**
     * If the Statistics mode is checked.
     *
     * @return {@code true} if the statistics mode is checked, {@code false} otherwise.
     */
    public boolean isStatisticsChecked() {
        return (statisticsChecked != null) ? statisticsChecked.get() : false;
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
    public String getDefaultOutput(AlgorithmEntity alg) {
        String output;
        String defaultOutputDir = WorkspaceManager.get().getPreferenceAsFile(Preferences.DEFAULT_OUTPUT_DIR).toString();
        String workspace = (selectedBenchmark != null) ? selectedBenchmark.getOutputDir()
                : defaultOutputDir;
        if (alg != null) {
            output = Paths.get(workspace, alg.getShortName() + "_output.txt").toString();
        } else {
            output = workspace;
        }
        return output;
    }

        
    }
