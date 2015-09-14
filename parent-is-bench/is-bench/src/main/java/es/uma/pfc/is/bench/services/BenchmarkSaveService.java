/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.algorithmslist.AlgorithmsListController;
import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.BenchmarksChangeEvent;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.domain.ws.WorkspaceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchmarkSaveService extends Service<Benchmark>{
    /**
     * Benchmark to save.
     */
    private Benchmark benchmark;
    
    /**
     * Constructor.
     * @param benchmark Benchmark to save.
     */
    public BenchmarkSaveService(Benchmark benchmark) {
        this.benchmark = benchmark;
    }
    
    

    @Override
    protected Task<Benchmark> createTask() {
        return new Task<Benchmark>(){

            @Override
            protected Benchmark call() throws Exception {
                
                BenchmarksBean benchamrksBean = new BenchmarksBean();
                benchmark.setWorkspace(WorkspaceManager.get().currentWorkspace().getLocation());
                benchamrksBean.update(benchmark);
                
                return benchmark;
            }
        };
    }

    @Override
    protected void failed() {
        String message = BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_ERROR, getException().getMessage());
        Logger.getLogger(AlgorithmsListController.class.getName()).log(Level.SEVERE, message, getException());
        Eventbus.post(new MessageEvent(message, MessageEvent.Level.ERROR));
    }

    @Override
    protected void succeeded() {
        Eventbus.post(new BenchmarksChangeEvent());
        Eventbus.post(new MessageEvent(BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_SUCCEEDED), MessageEvent.Level.SUCCEEDED));
    }

    
}
