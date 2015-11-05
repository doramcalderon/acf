
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.BenchmarksChangeEvent;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service which saves benchmark changes.
 * @since 
 * @author Dora Calderón
 */
public class BenchmarkSaveService extends Service<Benchmark>{
    /**
     * Benchmark to save.
     */
    private Benchmark benchmark;
    /**
     * Benchmarks logic.
     */
    private BenchmarksBean benchmarksBean;
    
    /**
     * Constructor.
     * @param benchmark Benchmark to save.
     */
    public BenchmarkSaveService(Benchmark benchmark) {
        this.benchmark = benchmark;
        this.benchmarksBean = new BenchmarksBean();
    }
    
    
    /**
     * Creates the background task which saves the benchmarks changes.
     * @return Updated benchmark.
     */
    @Override
    protected Task<Benchmark> createTask() {
        return new Task<Benchmark>(){

            @Override
            protected Benchmark call() throws Exception {
                benchmark.setWorkspace(WorkspaceManager.get().currentWorkspace().getLocation());
                benchmarksBean.update(benchmark);
                
                return benchmark;
            }
        };
    }

    /**
     * This method is executed when the background task is completed with errors.
     */
    @Override
    protected void failed() {
        String message = BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_ERROR, getException().getMessage());
        Logger.getLogger(BenchmarkSaveService.class.getName()).log(Level.SEVERE, message, getException());
        Eventbus.post(new MessageEvent(message, MessageEvent.Level.ERROR));
    }

    /**
     * This method is executed when the background task is completed succesfully.<br/>
     * Publishes a BenchmarksChangeEvent and MessageEvent by the Eventbus.
     */
    @Override
    protected void succeeded() {
        Eventbus.post(new BenchmarksChangeEvent());
        Eventbus.post(new MessageEvent(BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_SUCCEEDED), MessageEvent.Level.SUCCEEDED));
    }

    
}
