
package es.uma.pfc.is.bench.benchmarks;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *  Benchmarks execution model.
 * @author Dora Calderón
 */
public class RunBenchmarkModel {
    
    /**
     * Available algorithms.
     */
    private List<Benchmark> benchmarks;
    /**
     * Selected benchmark.
     */
//    private final ObjectProperty selectedBenchmark;
    /**
     * Selected algorithms.
     */
    private List<Algorithm> selectedAlgorithms;
    /**
     * Path input file property.
     */
    private final StringProperty inputProperty;
    
    
    /** Path output file property.**/
    private final StringProperty outputProperty;
    
    private final UserConfig userConfig;
    
    /**
     * Constructor.
     */
    public RunBenchmarkModel() {
        benchmarks = new ArrayList();
        userConfig = UserConfig.get();
//        selectedBenchmark = new SimpleObjectProperty();
        inputProperty = new SimpleStringProperty();
        outputProperty = new SimpleStringProperty();
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
     * @return Algorithms.
     */
    public List<Benchmark> getBenchmarks() {
        return benchmarks;
    }

    /**
     * Set the available algorithms.
     * @param benchmarks Algorithms.
     */
    public void setBenchmarks(List<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }
//
//    public ObjectProperty selectedBenchmark() {
//        return selectedBenchmark;
//    }
    
    /**
     * Selected algorithms.
     * @return Algorithms list.
     */
    public List<Algorithm> getSelectedAlgorithms() {
        return selectedAlgorithms;
    }
    /**
     * Set the selected algorithms.
     * @param selectedAlgorithms Algorithms list.
     */
    public void setSelectedAlgorithms(List<Algorithm> selectedAlgorithms) {
        this.selectedAlgorithms = selectedAlgorithms;
    }
    
    /**
     * Path input file property.
     * @return StringProperty.
     */
    public StringProperty inputProperty() {
        return inputProperty;
    }
    
    /**
     * Path input file.
     * @return the input.
     */
    public String getInput() {
        return (inputProperty != null) ? inputProperty.get() : null;
    }


    /**
     * Path input file property.
     * @return StringProperty.
     */
    public StringProperty outputProperty() {
        return outputProperty;
    }
    
    /**
     * Path output file.
     * @return the outputFile
     */
    public String getOutput() {
        return (outputProperty != null) ? outputProperty.get() : null;
    }

 
    /**
     * Default output file for an algorithm.
     * @param alg Algorithm.
     * @return Default output file.
     */
    public String getDefaultOutput(Algorithm alg) {
        String output = null;
        if(alg != null) {
            output = userConfig.getDefaultOutputDir().toString() + File.separator + alg.getShortName() + "_output.txt";
        }
        return output;
    }
    /**
     * Set the input/output for each selected algorithm.
     */
    public void setInputOutputs() {
        if(selectedAlgorithms != null) {
            if(selectedAlgorithms.size() > 1) {
                for(Algorithm alg : selectedAlgorithms) {
                    alg.input(getInput());
                    alg.output(getDefaultOutput(alg));
                }
            } else {
                selectedAlgorithms.get(0).input(getInput()).output(getOutput());
            }
        }
    }

    
}
