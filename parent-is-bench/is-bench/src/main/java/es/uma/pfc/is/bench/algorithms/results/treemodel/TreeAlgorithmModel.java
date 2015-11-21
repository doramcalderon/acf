
package es.uma.pfc.is.bench.algorithms.results.treemodel;

import es.uma.pfc.is.algorithms.AlgorithmInfo;

/**
 * Model for algorithm node.
 * @author Dora Calderón
 */
public class TreeAlgorithmModel extends TreeResultModel {
    /**
     * Algorithm info.
     */
    private final AlgorithmInfo algorithmInfo;
    
    public TreeAlgorithmModel(AlgorithmInfo algInfo) {
        super();
        
        algorithmInfo = algInfo;
        if(algInfo != null) {
            nameProperty().set(algInfo.getShortName());
        }
    }

    /**
     * @return the algorithmInfo
     */
    public AlgorithmInfo getAlgorithmInfo() {
        return algorithmInfo;
    }
    
    

}
