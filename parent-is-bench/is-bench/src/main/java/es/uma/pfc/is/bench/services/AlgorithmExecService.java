
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.bench.benchmarks.execution.RunBenchmarkModel;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.events.MessageEvent;
import java.nio.file.Paths;
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
    private List<Algorithm> algs;

    public AlgorithmExecService(List<Algorithm> algs) {
        this.algs = algs;
    }
    public AlgorithmExecService(RunBenchmarkModel model) {
        if(model != null) {
            algs = model.getSelectedAlgorithms();
            for(Algorithm alg : algs) {
                alg.input(model.getInput());
                if(model.getSelectedAlgorithm() != null) {
                    alg.output(model.getOutput());
                } else {
                    alg.output(Paths.get(model.getOutput(), alg.getDefaultOutputFileName()).toString());
                }
            }
        }
    }

    
    @Override
    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                
                if(algs != null) {
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
        BenchEventBus.get().post(new MessageEvent("The execution has failed.", MessageEvent.Level.ERROR));
    }

    @Override
    protected void succeeded() {
        BenchEventBus.get().post(new MessageEvent("The execution has finished succeeded.", MessageEvent.Level.SUCCEEDED));
    }
    
    
}
