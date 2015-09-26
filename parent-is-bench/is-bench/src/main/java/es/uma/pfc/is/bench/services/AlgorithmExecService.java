package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.io.BenchmarkCSVWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @since @author Dora Calderón
 */
public class AlgorithmExecService extends Service<BenchmarkResult> {

    /**
     * Algorithms to execute.
     */
    private List<Algorithm> algorithms;
    private RunBenchmarkModel model;
    

    public AlgorithmExecService(List<Algorithm> algs) {
        this.algorithms = algs;
    }

    public AlgorithmExecService(RunBenchmarkModel model) {
        this.model = model;
    }

    /**
     * Creates the instances of the benchmark algorithms.
     */
    protected void instanceAlgorithms() {
        if (algorithms == null) {
            algorithms = new ArrayList<>();
        }
        model.getSelectedAlgorithms().stream().forEach(algEnt -> {
            algorithms.add(instanceAlgorithm(algEnt));
        });
    }

    /**
     * Instances the algorithm of type of {@code entity} param.
     *
     * @param entity Algorithm entity.
     * @return Algorithm.
     */
    //TODO junit
    protected Algorithm instanceAlgorithm(AlgorithmEntity entity) {
        try {
            
            Algorithm alg = entity.getType().newInstance();
            alg.setName(entity.getName());
            alg.setShortName(entity.getShortName());
            return alg;

        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Error runing the benchmark.", ex);
        }
    }
    

    /**
     * Sets the active modes in the model to the algorithm.
     * @param alg Algorithm.
     */
    protected AlgorithmOptions getOptions() {
        AlgorithmOptions options = new AlgorithmOptions();
        
        if (model.isHistoryChecked()) {
            options.enable(AlgorithmOptions.Mode.HISTORY);
        }
        if (model.isTimeChecked()) {
            options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        }
        if (model.isStatisticsChecked()) {
            options.enable(AlgorithmOptions.Mode.STATISTICS);
        }
        options.addOption(Options.OUTPUT_TYPE, model.getOutputFileType());
        return options;
    }
    
    /**
     * Prints the results into a CSV file.
     * @param result Results.
     * @throws IOException 
     */
    protected void printResults(BenchmarkResult result) throws IOException {
        Path csvFile = Paths.get(model.getOutputDir(), model.getSelectedBenchmark().getName() + ".csv");
        BenchmarkCSVWriter.print(result, csvFile);
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

    /**
     * Task for execute algorithms and benchmarks.<br/>
     * If the {@link Mode#STATISTICS} mode is enabled, prints the results into a CSV file.
     * @return Task
     */
    @Override
    protected Task<BenchmarkResult> createTask() {
        return new Task<BenchmarkResult>() {

            @Override
            protected BenchmarkResult call() throws Exception {
                BenchmarkResult benchmarkResult = null;
                instanceAlgorithms();
                List<AlgorithmResult> results = new ArrayList<>();
                Date timeStamp = new Date();
                
                if (algorithms != null) {
                    String [] inputs = model.getSelectedInputFiles().toArray(new String[]{});
                    AlgorithmOptions options = getOptions();
                    AlgorithmExecutor exec = new AlgorithmExecutor()
                                                .inputs(inputs)
                                                .options(options);
                    algorithms.stream().filter((alg) -> (alg != null)).forEach((alg) -> {
                        results.addAll(exec.execute(alg));
                    });
                    
                    benchmarkResult = new BenchmarkResult(results, timeStamp);
                    if(options.isEnabled(Mode.STATISTICS)) {
                        printResults(benchmarkResult);
                    }
                }
                
            
                return benchmarkResult;
            }
        };
    }

    /**
     * The onFinish event handler is called whenever the Task state transitions to the finished state: CANCELLED, FAILED
     * or SUCCEEDED.
     *
     * @param handler Handler.
     */
    public void setOnFinished(EventHandler<WorkerStateEvent> handler) {
        setOnCancelled(handler);
        setOnFailed(handler);
        setOnSucceeded(handler);
    }

    @Override
    protected void failed() {
        getException().printStackTrace();
        Eventbus.post(new MessageEvent("The execution has failed: " + getException().getMessage(), MessageEvent.Level.ERROR));
    }

    /**
     * If statistics mode is enabled, prints the benchmark results into a CSV file.
     */
    @Override
    protected void succeeded() {
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

}
