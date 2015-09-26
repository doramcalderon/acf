
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algorithm result info.
 * @author Dora Calderón
 */
public class AlgorithmResult {
    /**
     * Implicational system input file.
     */
    private final String inputFile;
    /**
     * Implicational system output file.
     */
    private final String outputFile;
    /**
     * Log file.
     */
    private String logFile;
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
     * Execution time.
     */
    private long executionTime;
    
    /**
     * Constructor.
     * @param inputFile
     * @param outputFile
     * @param resultSystem
     * @param algorithmClass 
     */
    public AlgorithmResult(String inputFile, String outputFile, ImplicationalSystem resultSystem, Class<? extends Algorithm> algorithmClass) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
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
     * Implicational system input file.
     * @return the inputFile
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Implicational system output file.
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * Log file.
     * @return the logFile
     */
    public String getLogFile() {
        return logFile;
    }

    /**
     * Log file.
     * @param logFile the logFile to set
     */
    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    /**
     * Execution time.
     * @return the executionTime
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * Execution time.
     * @param executionTime the executionTime to set
     */
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    
    
    
}
