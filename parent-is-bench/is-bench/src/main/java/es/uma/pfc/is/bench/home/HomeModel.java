package es.uma.pfc.is.bench.home;

import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Home view model.
 *
 * @author Dora Calderón
 */
public class HomeModel {

    /**
     * Results to show in the home view.
     */
    private List<BenchmarkResultSet> results;

    /**
     * Results to show in the home view.
     *
     * @return the results
     */
    public List<BenchmarkResultSet> getResults() {
        return results;
    }

    /**
     * Results to show in the home view.
     *
     * @param results the results to set
     */
    public void setResults(List<BenchmarkResultSet> results) {
        this.results = results;
    }

    /**
     * Groups the results by the benchmark name.
     * @return Benchmark results grouped by name.
     */
    public Map<String, List<BenchmarkResultSet>> groupByBenchmark() {
        Map<String, List<BenchmarkResultSet>> groupedResults = new HashMap<>();
        if (getResults()!= null) {
            groupedResults = getResults().stream()
                    .collect(Collectors.groupingBy(BenchmarkResultSet::getName));
        }
        return groupedResults;
    }
}
