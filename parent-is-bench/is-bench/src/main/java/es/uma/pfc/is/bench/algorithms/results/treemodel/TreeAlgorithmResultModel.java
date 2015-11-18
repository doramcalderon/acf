
package es.uma.pfc.is.bench.algorithms.results.treemodel;

import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.nio.file.Paths;

/**
 * Model for algorithm result nodes.
 * @author Dora Calderón
 */
public class TreeAlgorithmResultModel extends TreeResultModel {
    /**
     * Algorithm result;
     */
    private final AlgorithmResult algorithmresult;

    /**
     * Constructor.
     * @param result Algorihtm result.
     */
    public TreeAlgorithmResultModel(AlgorithmResult result) {
        super();
        algorithmresult = result;
        
        if(result != null) {
            executionTimeProperty().set(result.getExecutionTime());
            sizeProperty().set(result.getSize());
            cardinalityProperty().set(result.getCardinality());
            if(!StringUtils.isEmpty(result.getInputFile())) {
                inputProperty().set(Paths.get(result.getInputFile()).getFileName().toString());
            }
            if(!StringUtils.isEmpty(result.getOutputFile())) {
                outputProperty().set(Paths.get(result.getOutputFile()).getFileName().toString());
            }
            logProperty().set(result.getLogFile());
        }
    }

    /**
     * Algorithm result.
     * @return the algorithmresult
     */
    public AlgorithmResult getAlgorithmresult() {
        return algorithmresult;
    }
    
    
    
    
}
