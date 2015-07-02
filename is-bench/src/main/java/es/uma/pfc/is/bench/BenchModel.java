
package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.config.UserConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  Benchmarks execution model.
 * @author Dora Calderón
 */
public class BenchModel {
    
    /**
     * Available algorithms.
     */
    private List<Algorithm> algorithms;
    /**
     * Selected algorithms.
     */
    private List<Algorithm> selectedAlgorithms;
    /** Path input file.**/
    private String input;
    /** Path output file.**/
    private String outputFile;
    private UserConfig userConfig;
    
    /**
     * Constructor.
     */
    public BenchModel() {
        algorithms = new ArrayList();
        userConfig = UserConfig.get();
    }
    
    /**
     * Clear the model.
     */
    public void clear() {
        algorithms.clear();
        input = null;
        outputFile = null;
    }
    
    /**
     * Add a new algorithm.
     * @param alg Algorithm.
     * @throws NullPointerException when the algorithm is null.
     */
    public void addAlgorithm(Algorithm alg) {
        if(alg == null) {
            throw new NullPointerException("The element can't be null.");
        }
        if(algorithms == null) {
            algorithms = new ArrayList();
        }
        algorithms.add(alg);
    }

    /**
     * Available algorithms.
     * @return Algorithms.
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Set the available algorithms.
     * @param algorithms Algorithms.
     */
    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

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
     * Path input file.
     * @return the input.
     */
    public String getInput() {
        return input;
    }

    /**
     * Set the path input file.
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Path output file.
     * @return the outputFile
     */
    public String getOutput() {
        return outputFile;
    }

    /**
     *  Set the path output file.
     * @param output the outputFile to set
     */
    public void setOutput(String output) {
        this.outputFile = output;
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
                    alg.input(input);
                    alg.output(getDefaultOutput(alg));
                }
            } else {
                selectedAlgorithms.get(0).input(input).output(outputFile);
            }
        }
    }
}
