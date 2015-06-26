package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.ChangesManager;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsBean;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsPersistence;
import es.uma.pfc.is.bench.algorithms.view.AlgorithmsModel;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.config.UserConfig;
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.xml.bind.JAXB;

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
        ChangesManager.get().setAlgorithmsChanges(Boolean.TRUE);
    }
    
}
