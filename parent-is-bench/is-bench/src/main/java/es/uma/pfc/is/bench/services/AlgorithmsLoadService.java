package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import java.util.Set;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Load the algorithms declared in algorithms.properties file in the actual workspace.
 * @author Dora Calderón
 */
public class AlgorithmsLoadService extends Service<Set<AlgorithmInfo>>{
    /**
     * Algorithms logic.
     */
    private final AlgorithmsBean algorithmsBean;

    /**
     * Constructor.
     */
    public AlgorithmsLoadService() {
        algorithmsBean = new AlgorithmsBean(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_FILE));
    }

    

    @Override
    protected Task<Set<AlgorithmInfo>> createTask() {
        return new Task<Set<AlgorithmInfo>>() {

            @Override
            protected Set<AlgorithmInfo> call() throws Exception {
                return algorithmsBean.getAlgorithms();
            }

        };
    }

    
    @Override
    protected void failed() {
        Logger.getLogger(getClass().getName()).severe(getException().getMessage());
        getException().printStackTrace();
    }

    
  
}
