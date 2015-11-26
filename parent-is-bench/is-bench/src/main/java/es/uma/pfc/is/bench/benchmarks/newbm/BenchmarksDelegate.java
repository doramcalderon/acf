

package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.bench.business.BenchmarksBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.Benchmark;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Methods for access to business logic.
 * @author Dora Calderón
 */
public class BenchmarksDelegate {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(BenchmarksDelegate.class);
    /**
     * Benchmarks business bean.
     */
    private final BenchmarksBean benchmarksBean;

    public BenchmarksDelegate() {
        benchmarksBean = new BenchmarksBean();
    }
    
    /**
     * Return the benchmark with the specified name in the current workspace.
     * 
     * @param name Benchmark name.
     * @return the benchmark with the specified name in the current workspace.<br/> {@code null} if no exists.
     */
    public Benchmark getBenchmark(String name) {
        Benchmark benchmark = null;
        try {
            benchmark = benchmarksBean.getBenchmark(name, WorkspaceManager.get().currentWorkspace().getLocation());
        } catch (Exception ex) {
            logger.error("Error getting benchmarks.", ex);
        }
        return benchmark;
    }

}
