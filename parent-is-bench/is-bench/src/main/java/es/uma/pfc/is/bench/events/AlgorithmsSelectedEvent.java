

package es.uma.pfc.is.bench.events;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.Arrays;
import java.util.List;

/**
 * Event launched when algorithms are selected.
 * @author Dora Calderón
 */
public class AlgorithmsSelectedEvent {
    List<Algorithm> algorithmsSelection;

    /**
     * Constructor.
     * @param algorithmsSelection Selected algorithms.
     */
    public AlgorithmsSelectedEvent(List<Algorithm> algorithmsSelection) {
        this.algorithmsSelection = algorithmsSelection;
    }
    /**
     * Constructor.
     * @param algorithms Selected algorithms.
     */
    public AlgorithmsSelectedEvent(Algorithm ... algorithms) {
        algorithmsSelection = Arrays.asList(algorithms);
    }

    /**
     * Selected algorithms.
     * @return List of algorithms.
     */
    public List<Algorithm> getAlgorithmsSelection() {
        return algorithmsSelection;
    }
    
}
