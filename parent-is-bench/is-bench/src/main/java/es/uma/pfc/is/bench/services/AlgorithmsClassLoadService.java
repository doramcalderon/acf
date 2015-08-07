/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.reflections.Reflections;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class AlgorithmsClassLoadService extends Service<List<Class<? extends Algorithm>>> {

    /**
     * Constructor.
     */
    public AlgorithmsClassLoadService() {
    }

    @Override
    protected Task<List<Class<? extends Algorithm>>> createTask() {
        return new Task<List<Class<? extends Algorithm>>>() {

            @Override
            protected List<Class<? extends Algorithm>> call() throws Exception {
                Reflections r = new Reflections();
                Set<Class<? extends Algorithm>> classes = r.getSubTypesOf(Algorithm.class);
                return (classes != null) ? new ArrayList(classes) : null;
            }

        };
    }

    @Override
    protected void failed() {
        Logger.getLogger(getClass().getName()).severe(getException().getMessage());
        getException().printStackTrace();
    }

}
