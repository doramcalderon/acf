
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algorithm result info.
 * @author Dora Calderón
 */
public class AlgorithmResult {
    private final String inputFile;
    /**
     * Algorithm output system.
     */
    private final ImplicationalSystem resultSystem;
    /**
     * Algorithm type.
     */
    private final Class<? extends Algorithm> algorithmClass;
    /**
     * Implicational System size.
     */
    private int size;
    /**
     * Implicational System cardinality.
     */
    private int cardinality;
    
    /**
     * Constructor.
     * @param resultSystem
     * @param algorithmClass 
     */
    public AlgorithmResult(String inputFile, ImplicationalSystem resultSystem, Class<? extends Algorithm> algorithmClass) {
        this.inputFile = inputFile;
        this.resultSystem = resultSystem;
        this.algorithmClass = algorithmClass;
        size = 0;
        cardinality = 0;
        
        if(resultSystem != null) {
            size = ImplicationalSystems.getSize(resultSystem);
            cardinality = ImplicationalSystems.getCardinality(resultSystem);
        }
    }
    
    
    
    /**
     * Implicational System size.
     * @return size.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Implicational System cardinality.
     * @return cardinality.
     */
    public int getCardinality() {
        return cardinality;
    }

    /**
     * Algorithm output system.
     * @return the resultSystem
     */
    public ImplicationalSystem getResultSystem() {
        return resultSystem;
    }

    /**
     * Algorithm type.
     * @return the algorithmClass
     */
    public Class<? extends Algorithm> getAlgorithmClass() {
        return algorithmClass;
    }

    /**
     * @return the inputFile
     */
    public String getInputFile() {
        return inputFile;
    }
    
    
}
