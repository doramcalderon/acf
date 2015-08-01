package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.bench.algorithms.AlgorithmsModel;
import es.uma.pfc.is.bench.business.WorkspaceBean;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
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
    WorkspaceBean workspaceBean;

    /**
     * Constructor.
     * @param model Model. 
     */
    public AlgorithmsSaveService(AlgorithmsModel model) {
        this.model = model;
        workspaceBean = new WorkspaceBean();
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
                
                workspaceBean.addAlgorithms(UserConfig.get().getDefaultWorkspace(), entity);
                
                return null;
            }

        };
    }

    @Override
    protected void succeeded() {
        BenchEventBus.get().post(new AlgorithmChangeEvent());
    }
    
}
