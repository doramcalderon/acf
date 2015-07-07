/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.algorithmslist.view.AlgorithmsListController;
import es.uma.pfc.is.bench.benchmarks.business.BenchmarksBean;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

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
    private ResourceBundle rb;
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
                benchmark.setWorkspace(UserConfig.get().getDefaultWorkspace());
                benchamrksBean.create(benchmark);
                
                return benchmark;
            }
        };
    }

    @Override
    protected void failed() {
        String message = BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_ERROR, getException().getMessage());
        Logger.getLogger(AlgorithmsListController.class.getName()).log(Level.SEVERE, message, getException());
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    @Override
    protected void succeeded() {
        new Alert(Alert.AlertType.INFORMATION, BenchMessages.get().getMessage(BenchMessages.BENCHMARK_CREATION_SUCCEEDED)).showAndWait();
        
    }

    
}
