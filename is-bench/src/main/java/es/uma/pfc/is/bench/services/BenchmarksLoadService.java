/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.benchmarks.business.BenchmarksBean;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
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
                return new BenchmarksBean().getBenchmarks(UserConfig.get().getDefaultWorkspace());
            }
        };
    }
    

}