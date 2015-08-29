/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.config.ConfigManager;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchmarksLoadService extends Service<List<Benchmark>> {

    @Override
    protected Task<List<Benchmark>> createTask() {
        return new Task<List<Benchmark>>(){

            @Override
            protected List<Benchmark> call() throws Exception {
                return new BenchmarksBean().getBenchmarks(ConfigManager.get().getDefaultWorkspace());
            }
        };
    }
    

}
