package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @since @author Dora Calderón
 */
public class AlgorithmExecService extends Service {

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
        return options;
    }

    @Override

    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                instanceAlgorithms();
                if (algorithms != null) {
                    String [] inputs = model.getSelectedInputFiles().toArray(new String[]{});

                    AlgorithmExecutor exec = new AlgorithmExecutor()
                                                .inputs(inputs)
                                                .options(getOptions());
                    algorithms.stream().filter((alg) -> (alg != null)).forEach((alg) -> {
                        exec.output(model.getOutputDir()).execute(alg);
                    });
                    
                }
                return null;
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
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

}
