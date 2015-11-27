package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.business.ResultsBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.events.MessageEvent.Level;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads the benchmark results of current workspace.
 *
 * @author Dora Calderón
 */
public class ResultsLoadService extends Service<List<BenchmarkResultSet>> {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ResultsLoadService.class);

    @Override
    protected Task<List<BenchmarkResultSet>> createTask() {
        return new Task<List<BenchmarkResultSet>>() {

            @Override
            protected List<BenchmarkResultSet> call() throws Exception {
                ResultsBean resultsBean = new ResultsBean();
                return resultsBean.getAllResults(WorkspaceManager.get().currentWorkspace().getLocation());
            }
        };
    }

    @Override
    protected void failed() {
        String message = BenchMessages.get().getMessage(BenchMessages.LOADING_RESULTS_ERROR);
        logger.error(message, getException());
        Eventbus.post(new MessageEvent(message, Level.ERROR));
    }
    
    

}
