
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
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

    /**
     * Execute the algorithms of a benchmark.
     * @param benchmark Benchmark.
     */
    public AlgorithmExecService(Benchmark benchmark) {
        if(benchmark != null) {
            algs = benchmark.getAlgorithms();
            
            if(algs != null) {
                algs.forEach((alg) -> {
                    alg.output(Paths.get(benchmark.getOutputDir(), alg.getDefaultOutputPath()).toString());
                });    
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
}
