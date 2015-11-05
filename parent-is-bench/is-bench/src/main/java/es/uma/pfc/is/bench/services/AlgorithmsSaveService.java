package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.algorithms.AlgorithmsModel;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.commons.strings.StringUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service which saves the algorithms changes.
 *@author Dora Calderón
 */
public class AlgorithmsSaveService extends Service<Void> {
    /**
     * Model.
     */
    private final AlgorithmsModel model;
    /**
     * Algorithms bean.
     */
    private final AlgorithmsBean algorithmsBean;
    
    /**
     * Constructor.
     * @param model Model. 
     */
    public AlgorithmsSaveService(AlgorithmsModel model) {
        this.model = model;
        algorithmsBean = new AlgorithmsBean(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_FILE));
    }

    /**
     * Creates the background task which saves the changes.
     * @return 
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                
                AlgorithmInfo entity = new AlgorithmInfo();
                entity.setName(StringUtils.trim(model.getName()));
                entity.setShortName(StringUtils.trim(model.getShortName()));
                entity.setType((Class<? extends Algorithm>) Class.forName(model.getClassName()));
                
                algorithmsBean.addAlgorithms(entity);
                return null;
            }

        };
    }

    /**
     * This method is executed when the background task is completed succesfully.<br/>
     * Publishes an AlgorithmChangeEvent by Eventbus.
     */
    @Override
    protected void succeeded() {
        Eventbus.post(new AlgorithmChangeEvent());
    }
    
}
