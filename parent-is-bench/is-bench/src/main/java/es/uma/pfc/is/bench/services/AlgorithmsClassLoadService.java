/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.commons.reflection.ReflectionUtil;
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
public class AlgorithmsClassLoadService extends Service<List<String>> {

    /**
     * Constructor.
     */
    public AlgorithmsClassLoadService() {
    }

    /**
     * Searches the implementantios of Algorithm interface and returns its names.
     * @return List of classes names.
     */
    @Override
    protected Task<List<String>> createTask() {
        return new Task<List<String>>() {

            @Override
            protected List<String> call() throws Exception {
                Reflections r = new Reflections();
                Set<Class<? extends Algorithm>> classes = r.getSubTypesOf(Algorithm.class);
                List<String> classesName = new ArrayList();
                if(classes != null) {
                    classes.stream().filter(clazz ->  ReflectionUtil.isImplementation(clazz))
                                    .forEach(clazz -> classesName.add(clazz.getName()));
                }
                
                return classesName;
            }

        };
    }

    @Override
    protected void failed() {
        Logger.getLogger(getClass().getName()).severe(getException().getMessage());
        getException().printStackTrace();
    }

}
