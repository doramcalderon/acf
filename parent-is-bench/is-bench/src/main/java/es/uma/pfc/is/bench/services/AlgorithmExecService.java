package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

    @Override

    protected Task<BenchmarkResult> createTask() {
        return new Task<BenchmarkResult>() {

            @Override
            protected BenchmarkResult call() throws Exception {
                instanceAlgorithms();
                List<AlgorithmResult> results = new ArrayList<>();
                Date timeStamp = new Date();
                
                if (algorithms != null) {
                    String [] inputs = model.getSelectedInputFiles().toArray(new String[]{});
                    
                    AlgorithmExecutor exec = new AlgorithmExecutor()
                                                .inputs(inputs)
                                                .options(getOptions());
                    algorithms.stream().filter((alg) -> (alg != null)).forEach((alg) -> {
                        results.addAll(exec.execute(alg));
                    });
                    
                }
                return new BenchmarkResult(results, timeStamp);
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

    @Override
    protected void succeeded() {
        AlgorithmOptions options = getOptions();
        options.addOption(Options.OUTPUT, model.getOutputDir());
        options.addOption(Options.LOG_BASE_NAME, model.getSelectedBenchmark().getName());
        AlgorithmLogger logger = new AlgorithmLogger(model.getSelectedBenchmark().getName(), options, true);
        BenchmarkResult results = this.getValue();
        if(results != null) {
            CSVFileWriter csvWriter = null;
//            try {
                csvWriter = new CSVFileWriter(Paths.get(model.getOutputDir(), model.getSelectedBenchmark().getName() + ".csv"));

                // add headers
                List<String> headers = new ArrayList<>();
                headers.add("Algorithm");

                model.getSelectedInputFiles().stream().forEach((input) -> {
                    headers.add("Input");
                    headers.add("Size");
                    headers.add("Card");
                });
            try {
                csvWriter.header(headers.toArray(new String[]{}));
                csvWriter.start();
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmExecService.class.getName()).log(Level.SEVERE, null, ex);
            }

                Map<Class, List<AlgorithmResult>> resultsGrouped = results.getAlgorithmResults().stream()
                        .collect(Collectors.groupingBy(AlgorithmResult::getAlgorithmClass));

                List<AlgorithmResult> currentResults;
                List<String> fields = new ArrayList<>();

                for (Class algClass : resultsGrouped.keySet()) {
                    fields.clear();
                    fields.add(algClass.getSimpleName());

                    currentResults = resultsGrouped.get(algClass);
                    if (currentResults != null && !currentResults.isEmpty()) {
                        for (AlgorithmResult r : currentResults) {
                            fields.add(FileUtils.getName(r.getInputFile()));
                            fields.add(String.valueOf(r.getSize()));
                            fields.add(String.valueOf(r.getCardinality()));
                        }

                    }
                    try {
                        csvWriter.printRecord(fields.toArray());
                    } catch (IOException ex) {
                        Logger.getLogger(AlgorithmExecService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//            } catch (IOException ex) {
//                Logger.getLogger(AlgorithmExecService.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                if(csvWriter != null) {
//                    csvWriter.finish();
//                }
//            }

        }
        
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

}
