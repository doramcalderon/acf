package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import es.uma.pfc.is.bench.domain.Benchmarks;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Results bean.
 *
 * @author Dora Calderón
 */
public class ResultsBean {

    /**
     * Benchmarks persistence.
     */
    private final BenchmarksPersistence benchPersistence;
    /**
     * Results persistence.
     */
    private final ResultsPersistence resultsPersistence;

    /**
     * Constructor.
     */
    public ResultsBean() {
        benchPersistence = BenchmarksPersistence.get();
        resultsPersistence = ResultsPersistence.get();
    }

    /**
     * Save a benchmark result in results.xml file.
     *
     * @param result Benchmark result.
     * @param path Directory of results.xml.
     */
    public void save(BenchmarkResult result, String path) {
        resultsPersistence.save(result, path);
    }

    /**
     * Returns all saved results of the registered benchmarks in the path.
     *
     * @param path.
     * @return Results saved.
     */
    public List<BenchmarkResultSet> getAllResults(String path) {
        List<BenchmarkResultSet> allResults = null;
        Benchmarks registeredBenchamrks = benchPersistence.getBenchmarks(path);

        if (registeredBenchamrks != null) {

            allResults = new ArrayList<>();
            for (Benchmark b : registeredBenchamrks.getBenchmarks()) {
                BenchmarkResultSet benchResult = resultsPersistence.getResults(b);
                if (benchResult != null) {
                    allResults.add(benchResult);
                }

            }
        }

        return allResults;
    }
}