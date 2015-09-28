package es.uma.pfc.is.bench.algorithms.results;

import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.bench.benchmarks.execution.BenchmarkResultsModel;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Results view model.
 *
 * @author Dora Calderón
 */
public class ResultsModel {
    private static final  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");

    /**
     * All results.
     */
    private List<BenchmarkResultSet> allResults;

    /**
     * Constructor.
     */
    public ResultsModel() {
        allResults = new ArrayList<>();
    }

    /**
     * All results.
     *
     * @return the allResults
     */
    public List<BenchmarkResultSet> getAllResults() {
        return allResults;
    }

    /**
     * All results.
     *
     * @param allResults the allResults to set
     */
    public void setAllResults(List<BenchmarkResultSet> allResults) {
        this.allResults = allResults;
    }

    /**
     * Returns a Benchmark node.
     *
     * @param resultSet Benchmark resultset.
     * @return Benchmark node.
     */
    public BenchmarkResultsModel getBenchmarkNode(BenchmarkResultSet resultSet) {
        BenchmarkResultsModel node = null;

        if (resultSet != null) {
            node = new BenchmarkResultsModel(resultSet.getName());
        }

        return node;
    }

    public BenchmarkResultsModel getBenchmarkResultNode(BenchmarkResult result) {
        BenchmarkResultsModel node = null;

        if (result != null) {
            node = new BenchmarkResultsModel();
            node.setName(df.format(result.getDate()));
        }

        return node;
    }

    public BenchmarkResultsModel getAlgorithmResultNode(AlgorithmResult result) {
        BenchmarkResultsModel node = null;
        if (result != null) {
            node = new BenchmarkResultsModel();
            node.setExecutionTime(result.getExecutionTime());
            node.setInput(result.getInputFile());
            node.setOutput(result.getOutputFile());
            node.setLog(result.getLogFile());
        }
        return node;
    }
}
