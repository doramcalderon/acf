package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.business.ResultsBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.io.BenchmarkCSVWriter;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
import es.uma.pfc.is.commons.strings.date.DateUtils;
import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for the background execution of algorithms and benchmarks.
 * @author Dora Calderón
 */
public class AlgorithmExecService extends Service<BenchmarkResult> {
/**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(AlgorithmExecService.class);
    /**
     * Algorithms to execute.
     */
    private List<Algorithm> algorithms;
    /**
     * Model.
     */
    private RunBenchmarkModel model;
    private AlgorithmExecutor exec;

    public AlgorithmExecService() {
    }
    
    
    
    /**
     * Constructor.
     * @param algs Algorithms to execute.
     */
    public AlgorithmExecService(List<Algorithm> algs) {
        this.algorithms = algs;
    }
    /**
     * Constructor.
     * @param model Model. 
     */
    public AlgorithmExecService(RunBenchmarkModel model) {
        this.model = model;
    }
    
    public void setModel(RunBenchmarkModel model) {
        this.model = model;
    }

    /**
     * Creates the instances of the benchmark algorithms.
     */
    protected void instanceAlgorithms() {
        algorithms = new ArrayList<>();
        model.getSelectedAlgorithms().stream().forEach(algEnt -> {
            algorithms.add(instanceAlgorithm(algEnt));
        });
    }

    /**
     * Instances the algorithm of type of {@code entity} param.
     *
     * @param algorithm Algorithm entity.
     * @return Algorithm.
     */
    protected Algorithm instanceAlgorithm(AlgorithmInfo algorithm) {
        
        try (URLClassLoader classLoader = 
                    ReflectionUtil.getClassLoader(
                            Paths.get(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_PATH)))) {
        
            Algorithm alg = (Algorithm) classLoader.loadClass(algorithm.getType()).newInstance();
            alg.setName(algorithm.getName());
            alg.setShortName(algorithm.getShortName());
            return alg;

        } catch (InstantiationException | IllegalAccessException |  IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Error runing the benchmark.", ex);
        }
    }
    

    /**
     * Establishes the algorithm options from the model.
     * @return Algorithm execution options.
     */
    protected AlgorithmOptions getOptions() {
        AlgorithmOptions options = new AlgorithmOptions();
        
        if (model.isHistoryChecked()) {
            options.enable(AlgorithmOptions.Mode.TRACE);
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
        Path csvFile = Paths.get(result.getStatisticsFileName());
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
                    exec = new AlgorithmExecutor()
                                                .inputs(inputs)
                                                .options(options)
                                                .output(Paths.get(model.getOutputDir(), 
                                                        DateUtils.getDateString(timeStamp)).toString());
                    algorithms.stream().filter((alg) -> (alg != null)).forEach((alg) -> {
                        results.addAll(exec.execute(alg));
                    });
//                    exec.shutdown();
                    
                    benchmarkResult = new BenchmarkResult(model.getSelectedBenchmark().getName(), results, timeStamp);
                    
                    new ResultsBean().save(benchmarkResult, model.getSelectedBenchmark().getBenchmarkPath());
                    
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
      /**
     * This method is executed when the background task is completed with errors.<br/>
     */
    @Override
    protected void failed() {
        logger.error("The execution has failed. ", getException());
        Eventbus.post(new MessageEvent("The execution has failed: " + getException().getMessage(), MessageEvent.Level.ERROR));
    }

    /**
     * Publishes a message event when the task is finished succesfully.
     */
    @Override
    protected void succeeded() {
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

    @Override
    public boolean cancel() {
        if (exec != null) {
            try {
                exec.shutdown();
            } catch (InterruptedException ex) {
                logger.error("The service has been interrupted.", ex);
            }
        }
        return super.cancel(); 
    }

    
}
