package es.uma.pfc.is.bench.io;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Writes to CSV a benchmarks results.
 *
 * @author Dora Calderón
 */
public class BenchmarkCSVWriter {

    /**
     * CSV Writer.
     */
    private static CSVFileWriter csvWriter;

    /**
     * Prints a benchmark results to a CSV file.
     *
     * @param result Benchmark result.
     * @param outputPath CSV file otuput path.
     * @throws IOException
     */
    public static void print(BenchmarkResult result, Path outputPath) throws IOException {
        if (result != null) {
             Map<AlgorithmInfo, List<AlgorithmResult>> resultsGrouped = result.groupByAlgorithm();
            int inputsCount = resultsGrouped.values().stream().findFirst().get().size();
            
            csvWriter = new CSVFileWriter(outputPath);
            addHeaders(inputsCount);
            csvWriter.start();
            printResultsByAlgorithm(resultsGrouped);
            csvWriter.finish();
        }
    }

    /**
     * Adds the headers.<br/>
     * These will be: "Algorithm", "Input1", "Size1", "Card1", "Input2", "Size2", "Card2", ...,"InputN", "SizeN",
     * "CardN" where N is the results number by algorithm.
     *
     * @param resultsNumber Results number.
     */
    protected static void addHeaders(int resultsNumber) {
        // add headers
        List<String> headers = new ArrayList<>();
        headers.add("Algorithm");

        for (int index = 1; index <= resultsNumber; index++) {
            headers.add("Input" + index);
            headers.add("Size" + index);
            headers.add("Card" + index);
        }
        csvWriter.header(headers.toArray(new String[]{}));
    }

    /**
     * Prints a row by result algorithm.
     *
     * @param resultsGrouped Benchmark results grouped by algorithm.
     * @throws IOException
     */
    protected static void printResultsByAlgorithm(Map<AlgorithmInfo, List<AlgorithmResult>> resultsGrouped) throws IOException {
        
        List<AlgorithmResult> currentResults;
        List<String> fields = new ArrayList<>();

        for (AlgorithmInfo algInfo : resultsGrouped.keySet()) {
            fields.clear();
            fields.add(algInfo.getShortName());

            currentResults = resultsGrouped.get(algInfo);
            if (currentResults != null && !currentResults.isEmpty()) {
                for (AlgorithmResult r : currentResults) {
                    fields.add(FileUtils.getName(r.getInputFile()));
                    fields.add(String.valueOf(r.getSize()));
                    fields.add(String.valueOf(r.getCardinality()));
                }

            }
            csvWriter.printRecord(fields.toArray());
        }
    }

}
