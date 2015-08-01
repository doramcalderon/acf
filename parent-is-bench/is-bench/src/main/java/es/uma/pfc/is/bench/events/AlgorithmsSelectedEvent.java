

package es.uma.pfc.is.bench.events;

import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import java.util.Arrays;
import java.util.List;

/**
 * Event launched when algorithms are selected.
 * @author Dora Calderón
 */
public class AlgorithmsSelectedEvent {
    List<AlgorithmEntity> algorithmsSelection;

    /**
     * Constructor.
     * @param algorithmsSelection Selected algorithms.
     */
    public AlgorithmsSelectedEvent(List<AlgorithmEntity> algorithmsSelection) {
        this.algorithmsSelection = algorithmsSelection;
    }
    /**
     * Constructor.
     * @param algorithms Selected algorithms.
     */
    public AlgorithmsSelectedEvent(AlgorithmEntity ... algorithms) {
        algorithmsSelection = Arrays.asList(algorithms);
    }

    /**
     * Selected algorithms.
     * @return List of algorithms.
     */
    public List<AlgorithmEntity> getAlgorithmsSelection() {
        return algorithmsSelection;
    }
    
}
