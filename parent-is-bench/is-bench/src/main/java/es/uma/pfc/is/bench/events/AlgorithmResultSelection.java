
package es.uma.pfc.is.bench.events;

import es.uma.pfc.is.algorithms.AlgorithmResult;

/**
 * Event published when an algorithm result is selected in Results view.
 * @author Dora Calderón
 */
public class AlgorithmResultSelection {
    /**
     * Algorithm result.
     */
    private final AlgorithmResult result;
    
    public AlgorithmResultSelection(AlgorithmResult result) {
        this.result = result;
    }

    /**
     * Algorithm result.
     * @return the result
     */
    public AlgorithmResult getResult() {
        return result;
    }
    
    

}
