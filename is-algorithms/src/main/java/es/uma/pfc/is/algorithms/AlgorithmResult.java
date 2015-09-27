
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Algorithm result info.
 * @author Dora Calderón
 */
public class AlgorithmResult {
     /**
     * Info of the executed algorithm.
     */
    private final AlgorithmInfo algorithmInfo;
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
     * Implicational System size.
     */
    private Integer size;
    /**
     * Implicational System cardinality.
     */
    private Integer cardinality;
    /**
     * Execution time.
     */
    private long executionTime;
    
    /**
     * Constructor.
     * @param inputFile Input system file.
     * @param outputFile Output system file.
     * @param algorithmClass Algorithm class.
     */
    public AlgorithmResult(String inputFile, String outputFile, AlgorithmInfo algorithmInfo) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.algorithmInfo = algorithmInfo;
        
    }
    
    /**
     * Constructor.
     * @param inputFile Input system file.
     * @param outputFile Output system file.
     * @param algorithmClassName Algorithm class name.
     */
    public AlgorithmResult(String inputFile, String outputFile, Algorithm algorithm) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.algorithmInfo = new AlgorithmInfo(algorithm);
    }
    
    
    
    /**
     * Implicational System size.
     * @return size.
     */
    public int getSize() {
        if(size == null) {
            try {
                ImplicationalSystem resultSystem = new ImplicationalSystem(outputFile);
                size = ImplicationalSystems.getSize(resultSystem);
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmResult.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return size;
    }
    
    /**
     * Implicational System cardinality.
     * @return cardinality.
     */
    public int getCardinality() {
        if(cardinality == null) {
            try {
                ImplicationalSystem resultSystem = new ImplicationalSystem(outputFile);
                cardinality = ImplicationalSystems.getCardinality(resultSystem);
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmResult.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cardinality;
    }


    /**
     * Info of the executed algorithm.
     * @return the algorithmClass
     */
    public AlgorithmInfo getAlgorithmInfo() {
        return algorithmInfo;
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
