
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
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.LoggerFactory;

/**
 * Algorithm result info.
 * @author Dora Calderón
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmResult {
     /**
     * Logger.
     */
    @XmlTransient
    private final org.slf4j.Logger logger = LoggerFactory.getLogger (AlgorithmResult.class);
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
     * Implicational system resultSystem file.
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
    private double executionTime;
    
    @XmlTransient
    private ImplicationalSystem resultSystem;
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
    
    @XmlTransient
    public ImplicationalSystem getResultSystem() {
        if(resultSystem == null) {
            try {
                resultSystem = new ImplicationalSystem(outputFile);
            } catch (IOException ex) {
                logger.error("IO Error creating the implicational system.", ex);
            }
        }
        return resultSystem;
    }
    
    
    /**
     * Implicational System size.
     * @return size.
     */
    public Integer getSize() {
        if(size == null) {
            size = ImplicationalSystems.getSize(getResultSystem());
        }
        return size;
    }
    
    /**
     * Implicational System cardinality.
     * @return cardinality.
     */
    public Integer getCardinality() {
        if(cardinality == null) {
            cardinality = ImplicationalSystems.getCardinality(getResultSystem());
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
     * Implicational system resultSystem file.
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
    public double getExecutionTime() {
        return executionTime;
    }

    /**
     * Execution time.
     * @param executionTime the executionTime to set
     */
    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    
    
    
}
