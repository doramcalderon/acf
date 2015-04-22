
package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;

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
    private String output;

    public BenchModel() {
        algorithms = new ArrayList();
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
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * Directorio de salida.
     * @param output the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }
    
    
}
