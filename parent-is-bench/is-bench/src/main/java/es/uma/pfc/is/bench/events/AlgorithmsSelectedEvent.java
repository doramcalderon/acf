

package es.uma.pfc.is.bench.events;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import java.util.Arrays;
import java.util.List;

/**
 * Event launched when algorithms are selected.
 * @author Dora Calderón
 */
public class AlgorithmsSelectedEvent {
    List<AlgorithmInfo> algorithmsSelection;

    /**
     * Constructor.
     * @param algorithmsSelection Selected algorithms.
     */
    public AlgorithmsSelectedEvent(List<AlgorithmInfo> algorithmsSelection) {
        this.algorithmsSelection = algorithmsSelection;
    }
    /**
     * Constructor.
     * @param algorithms Selected algorithms.
     */
    public AlgorithmsSelectedEvent(AlgorithmInfo ... algorithms) {
        algorithmsSelection = Arrays.asList(algorithms);
    }

    /**
     * Selected algorithms.
     * @return List of algorithms.
     */
    public List<AlgorithmInfo> getAlgorithmsSelection() {
        return algorithmsSelection;
    }
    
}
