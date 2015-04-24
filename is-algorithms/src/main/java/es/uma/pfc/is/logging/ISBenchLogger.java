
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Logger.
 * @author Dora Calderón
 */
public class ISBenchLogger  {
    private Logger logger;
    private String performanceLog;
    private String statisticLog;
    private String traceLog;
    private AlgorithmOptions options;
    private String outputDir;
    
    public ISBenchLogger() {
    }

    public ISBenchLogger(AlgorithmOptions options) {
        this.options = options;
        if(options != null) {
            outputDir = options.<String>getOption(Options.OUTPUT.toString());
            
            if (isPerformanceEnabled()) {
                
            }
        }
        
        
    }
    /**
     * Si el modo {@link Mode#PERFORMANCE} está habilitado.
     * @return {@code true} si {@link Mode#PERFORMANCE} está habilitado, {@code false} en otro caso.
     */
    public boolean isPerformanceEnabled() {
        return options.isEnabled(Mode.PERFORMANCE);
    }
    /**
     * Si el modo {@link Mode#STATISTICS} está habilitado.
     * @return {@code true} si {@link Mode#STATISTICS} está habilitado, {@code false} en otro caso.
     */
    public boolean isStatisticsEnabled() {
        return options.isEnabled(Mode.STATISTICS);
    }
    /**
     * Si el modo {@link Mode#TRACE} está habilitado.
     * @return {@code true} si {@link Mode#TRACE} está habilitado, {@code false} en otro caso.
     */
    public boolean isTraceEnabled() {
        return options.isEnabled(Mode.TRACE);
    }
    

    /**
     * Constructor.
     * @param clazz Clase. 
     */
    public ISBenchLogger(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Constructor.
     * @param name Nombre.
     */
    public ISBenchLogger(String name) {
        logger = LoggerFactory.getLogger(name);
    }
    
    
}
