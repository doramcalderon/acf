
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
    
    
}
