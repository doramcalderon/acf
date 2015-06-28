
package es.uma.pfc.is.bench.algorithmslist.view;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;

/**
 * Model of AlgorithmsList view.
 * @author Dora Calderón
 */
public class AlgorithmsListModel {
    /**
     * Algorithms list.
     */
    private List<Algorithm> algorithms;

    public AlgorithmsListModel() {
        algorithms = new ArrayList();
    }
    
    

    /**
     * Algorithms list.
     * @return the algorithms
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Algorithms list.
     * @param algorithms the algorithms to set
     */
    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }
    
    
    

}
