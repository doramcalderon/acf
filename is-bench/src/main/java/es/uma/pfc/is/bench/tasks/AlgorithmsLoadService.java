
package es.uma.pfc.is.bench.tasks;


import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.ChangesManager;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsBean;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Load the algorithms declared in algorithms.properties file in the actual workspace.
 * @author Dora Calderón
 */
public class AlgorithmsLoadService extends Service<List<Algorithm>>{
    /**
     * Algorithms logic.
     */
    private final AlgorithmsBean algorithmsBean;

    /**
     * Constructor.
     */
    public AlgorithmsLoadService() {
        algorithmsBean = new AlgorithmsBean();
    }
    
    

    @Override
    protected Task<List<Algorithm>> createTask() {
        return new Task<List<Algorithm>>() {

            @Override
            protected List<Algorithm> call() throws Exception {
                List<Algorithm> algorithms = new ArrayList();
                
                List<AlgorithmEntity> algsEntities = algorithmsBean.getAlgorithms();
                if(algsEntities != null) {
                        for(AlgorithmEntity entity : algsEntities) {
                            if(entity != null) {
                                Algorithm alg = entity.getType().newInstance();
                                alg.setName(entity.getName());
                                algorithms.add(alg);
                                
                            }
                        }
                }
                return algorithms;
            }
            
        };
    }

    
    @Override
    protected void failed() {
        Logger.getLogger(getClass().getName()).severe(getException().getMessage());
        getException().printStackTrace();
    }

    
  
}
