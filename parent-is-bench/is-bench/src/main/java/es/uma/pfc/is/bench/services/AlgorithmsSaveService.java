package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.algorithms.AlgorithmsModel;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.commons.workspace.Preferences;
import es.uma.pfc.is.commons.workspace.WorkspaceManager;
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
    private final AlgorithmsModel model;
    /**
     * Algorithms bean.
     */
    private final AlgorithmsBean algorithmsBean;
    /**
     * Workspace manager.
     */
    private final WorkspaceManager wsManager;
    
    /**
     * Constructor.
     * @param model Model. 
     */
    public AlgorithmsSaveService(AlgorithmsModel model) {
        this.model = model;
        this.wsManager = WorkspaceManager.get();
        algorithmsBean = new AlgorithmsBean(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_FILE));
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
                
                algorithmsBean.addAlgorithms(entity);
                
                return null;
            }

        };
    }

    @Override
    protected void succeeded() {
        Eventbus.post(new AlgorithmChangeEvent());
    }
    
}
