

package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Dora Calderón
 */
public class BenchmarkResult {
    private Benchmark benchmark;
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
    public BenchmarkResult(Benchmark b, List<AlgorithmResult> algorithmResults, Date timestamp) {
        this.benchmark = b;
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
    
    public Map<AlgorithmInfo, List<AlgorithmResult>> groupByAlgorithm() {
        return getAlgorithmResults().stream()
                .collect(Collectors.groupingBy(AlgorithmResult::getAlgorithmInfo));
    }

    /**
     * @return the benchmark
     */
    public Benchmark getBenchmark() {
        return benchmark;
    }

    /**
     * @param benchmark the benchmark to set
     */
    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }
    
}
