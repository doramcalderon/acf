
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Algorithm result info.
 * @author Dora Calderón
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmResult {
     /**
     * Info of the executed algorithm.
     */
    @XmlElement
    private AlgorithmInfo algorithmInfo;
    /**
     * Implicational system input file.
     */
    @XmlElement
    private String inputFile;
    /**
     * Implicational system output file.
     */
    @XmlElement
    private String outputFile;
    /**
     * Log file.
     */
    @XmlElement
    private String logFile;
   
    /**
     * Implicational System size.
     */
    @XmlElement
    private Integer size;
    /**
     * Implicational System cardinality.
     */
    @XmlElement
    private Integer cardinality;
    /**
     * Execution time.
     */
    @XmlElement
    private long executionTime;

    /**
     * Constructor.
     */
    public AlgorithmResult() {
    }
    
    
    /**
     * Constructor.
     * @param inputFile Input system file.
     * @param outputFile Output system file.
     * @param algorithmInfo Algorithm info.
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
     * @param algorithm Algorithm.
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
    public Integer getSize() {
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
    public Integer getCardinality() {
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
