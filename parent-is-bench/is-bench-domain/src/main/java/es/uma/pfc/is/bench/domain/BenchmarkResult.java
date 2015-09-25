

package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.AlgorithmResult;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarkResult {
    /**
     * Algorithm results.
     */
    private final List<AlgorithmResult> algorithmResults;
    /**
     * Execution date/time.
     */
    private final Date timestamp;

    /**
     * Constructor.
     * @param algorithmResults
     * @param timestamp 
     */
    public BenchmarkResult(List<AlgorithmResult> algorithmResults, Date timestamp) {
        this.algorithmResults = algorithmResults;
        this.timestamp = timestamp;
    }

    /**
     * Algorithm results.
     * @return the algorithmResults
     */
    public List<AlgorithmResult> getAlgorithmResults() {
        return algorithmResults;
    }

    /**
     * Execution date/time.
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }
    
    
}
