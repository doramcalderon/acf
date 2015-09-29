

package es.uma.pfc.is.bench.algorithms.results.treemodel;

import es.uma.pfc.is.bench.domain.BenchmarkResult;
import java.text.SimpleDateFormat;

/**
 * Model for benchmark result node.
 * @author Dora Calderón
 */
public class TreeBenchmarkResultModel extends TreeResultModel {
    /**
     * Benchmark result.
     */
    private final BenchmarkResult benchmarkResult;

    /**
     * Constructor.
     * @param result Benchmark result.
     */
    public TreeBenchmarkResultModel(BenchmarkResult result) {
        super();
        this.benchmarkResult = result;
        if (result != null && result.getDate() != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
            nameProperty().set(df.format(result.getDate()));
        }
    }

    /**
     * Benchmark result.
     * @return the benchmarkResult
     */
    public BenchmarkResult getBenchmarkResult() {
        return benchmarkResult;
    }
    
    
}
