/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.Benchmark;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Methods for access to business logic.
 * @author Dora Calderón
 */
public class BenchmarksDelegate {
    /**
     * Benchmarks business bean.
     */
    private final BenchmarksBean benchmarksBean;

    public BenchmarksDelegate() {
        benchmarksBean = new BenchmarksBean();
    }
    
    /**
     * Return the benchmark with the specified name in the current workspace.
     * 
     * @param name Benchmark name.
     * @return the benchmark with the specified name in the current workspace.<br/> {@code null} if no exists.
     */
    public Benchmark getBenchmark(String name) {
        Benchmark benchmark = null;
        try {
            benchmark = benchmarksBean.getBenchmark(name, WorkspaceManager.get().currentWorkspace().getLocation());
        } catch (Exception ex) {
            Logger.getLogger(BenchmarksDelegate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return benchmark;
    }

}
