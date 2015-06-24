
package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Modelo de la pantalla principal.
 * @since 
 * @author Dora Calderón
 */
public class BenchModel {
    
    /**
     * Algoritmos disponibles.
     */
    private List<Algorithm> algorithms;
    /**
     * Algoritmo seleccionado.
     */
    private Algorithm selectedAlgorithm;
    /** Archivo de entrada.**/
    private String input;
    /** Directorio de salida.**/
    private String outputFile;
    
    private Map<Mode, List<OutputStream>> traceOutputs;

    public BenchModel() {
        algorithms = new ArrayList();
        traceOutputs = new HashMap();
    }
    
    /**
     * Clear the model.
     */
    public void clear() {
        algorithms.clear();
        selectedAlgorithm = null;
        input = null;
        outputFile = null;
        traceOutputs.clear();
    }
    
    /**
     * Añade un nuevo algoritmo.
     * @param alg Algoritmo.
     * @throws NullPointerException cuando el algoritmo es nulo.
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
     * Algoritmos disponibles.
     * @return Algoritmos.
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Algoritmos disponibles.
     * @param algorithms Algoritmos.
     */
    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    /**
     * @return the selectedAlgorithm
     */
    public Algorithm getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    /**
     * @param selectedAlgorithm the selectedAlgorithm to set
     */
    public void setSelectedAlgorithm(Algorithm selectedAlgorithm) {
        this.selectedAlgorithm = selectedAlgorithm;
    }

    /**
     * Archivo de entrada.
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * Archivo de entrada.
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Directorio de salida.
     * @return the outputFile
     */
    public String getOutput() {
        return outputFile;
    }

    /**
     * Directorio de salida.
     * @param output the outputFile to set
     */
    public void setOutput(String output) {
        this.outputFile = output;
    }
    
    /**
     * Add a trace output.
     * @param os Outputstream.
     */
    public void addTraceOutput(Mode mode, OutputStream os) {
        if (os != null) {
            List<OutputStream> outputs = traceOutputs.get(mode);
            if (outputs == null) {
                outputs = new ArrayList();
            }
            outputs.add(os);
            traceOutputs.put(mode, outputs);
        }
    }
    /**
     * Trace outputs.
     * @return List of OutputStream.
     */
    public Map<Mode, List<OutputStream>> getTraceOutputs() {
        return traceOutputs;
    }
}
