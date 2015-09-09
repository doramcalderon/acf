
package es.uma.pfc.is.bench.services;

import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.bench.domain.ws.WorkspaceManager;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Loads the benchamarks registered in the current workspace.
 * @author Dora Calderón
 */
public class BenchmarksLoadService extends Service<List<Benchmark>> {

    @Override
    protected Task<List<Benchmark>> createTask() {
        return new Task<List<Benchmark>>(){

            @Override
            protected List<Benchmark> call() throws Exception {
                return new BenchmarksBean().getBenchmarks(WorkspaceManager.get().currentWorkspace().getLocation());
            }
        };
    }
    

}
