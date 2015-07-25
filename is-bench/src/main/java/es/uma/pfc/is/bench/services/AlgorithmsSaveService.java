package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsBean;
import es.uma.pfc.is.bench.algorithms.AlgorithmsModel;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.bench.events.BenchEventBus;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 *@author Dora Calderón
 */
public class AlgorithmsSaveService extends Service {
    /**
     * Model.
     */
    AlgorithmsModel model;
    AlgorithmsBean algorithmsBean;

    /**
     * Constructor.
     * @param model Model. 
     */
    public AlgorithmsSaveService(AlgorithmsModel model) {
        this.model = model;
        algorithmsBean = new AlgorithmsBean();
    }

    @Override
    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                
                AlgorithmEntity entity = new AlgorithmEntity();
                entity.setName(model.getName());
                entity.setShortName(model.getShortName());
                entity.setType((Class<? extends Algorithm>) Class.forName(model.getClassName()));
                
                algorithmsBean.insert(entity);
                
                return null;
            }

        };
    }

    @Override
    protected void succeeded() {
        BenchEventBus.get().post(new AlgorithmChangeEvent());
    }
    
}
