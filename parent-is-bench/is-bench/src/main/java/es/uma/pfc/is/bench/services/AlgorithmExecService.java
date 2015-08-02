package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class AlgorithmExecService extends Service {

    /**
     * Algorithms to execute.
     */
    private List<Algorithm> algs;
    private RunBenchmarkModel model;

    public AlgorithmExecService(List<Algorithm> algs) {
        this.algs = algs;
    }

    public AlgorithmExecService(RunBenchmarkModel model) {
        this.model = model;
    }

    /**
     * Creates the instances of the benchmark algorithms.
     */
    protected void instanceAlgorithms() {
        if (algs == null) {
            algs = new ArrayList<>();
        }
        model.getSelectedAlgorithms().stream().forEach(algEnt -> {
            algs.add(instanceAlgorithm(algEnt));
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
            alg.input(model.getInput());
            setOutput(alg);
            setModes(alg);
            return alg;

        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Error runing the benchmark.", ex);
        }
    }
    /**
     * Sets the output of an algorithm.
     * @param alg Algorithm.
     */
    protected void setOutput(Algorithm alg) {
        if (model.getSelectedAlgorithm() != null) {
            alg.output(model.getOutput());
        } else {
            alg.output(Paths.get(model.getOutput(), alg.getDefaultOutputFileName()).toString());
        }
    }

    /**
     * Sets the active modes in the model to the algorithm.
     * @param alg Algorithm.
     */
    protected void setModes(Algorithm alg) {
        if (model.isHistoryChecked()) {
            alg.enable(AlgorithmOptions.Mode.HISTORY);
        }
        if (model.isTimeChecked()) {
            alg.enable(AlgorithmOptions.Mode.PERFORMANCE);
        }
        if (model.isStatisticsChecked()) {
            alg.enable(AlgorithmOptions.Mode.STATISTICS);
        }
    }

    @Override

    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                instanceAlgorithms();
                if (algs != null) {
                    AlgorithmExecutor executor = new AlgorithmExecutor();
                    algs.forEach(alg -> executor.execute(alg));
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
        Eventbus.post(new MessageEvent("The execution has failed.", MessageEvent.Level.ERROR));
    }

    @Override
    protected void succeeded() {
        Eventbus.post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }

}
