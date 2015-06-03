/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class AlgorithmExecService extends Service {
    private Algorithm alg;

    public AlgorithmExecService(Algorithm alg) {
        this.alg = alg;
    }
    
    

    @Override
    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                return new AlgorithmExecutor().execute(alg);
            }
        };
    }


    /**
     * The onFinish event handler is called whenever the Task state transitions to the finished state: CANCELLED, FAILED
     * or SUCCEEDED.
     * @param handler Handler. 
     */
    public void setOnFinished(EventHandler<WorkerStateEvent> handler) {
        setOnCancelled(handler);
        setOnFailed(handler);
        setOnSucceeded(handler);
    }
}