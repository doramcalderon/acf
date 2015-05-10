
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.Messages;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_INIT;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_TOTAL;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;



/**
 * Logger.
 * @author Dora Calderón
 */
public class ISBenchLogger  implements Messages {
    /** Performance appender name.**/
    protected static final String PERFORMANCE_APPENDER = "Performance";
    /** Statistics appender name.**/
    protected static final String STATISTICS_APPENDER = "Statistics";
    /** History Appender name.*/
    protected static final String HISTORY_APPENDER = "History";
    
    /** Marker for performance outputs.**/
    protected static final Marker PERFORMANCE_MARKER = MarkerFactory.getMarker("PERFORMANCE");
    /** Marker for statistics outputs.**/
    protected static final Marker STATISTICS_MARKER = MarkerFactory.getMarker("STATS");
    /** Marker for history outputs.**/
    protected static final Marker HISTORY_MARKER = MarkerFactory.getMarker("HISTORY");
    
        
    /**
     * SLF4J Logger.
     */
    protected Logger logger;
    
    
    private AlgorithmOptions options;
    private List<OutputStream> outputs;
    private long startTime;
    
    
            
    private static final Properties messages;
    static {
        messages = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("es/uma/pfc/is/algorithms/loggingMessages.properties");
        if(is != null) {
            try {
                messages.load(is);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public ISBenchLogger() {
        logger = LoggerFactory.getLogger(ISBenchLogger.class);
        outputs = new ArrayList();
    }

    public ISBenchLogger(AlgorithmOptions options) {
        this();
        this.options = options;
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
    public boolean isHistoryEnabled() {
        return options.isEnabled(Mode.TRACE);
    }
    


    public void setOptions(AlgorithmOptions options) {
        this.options = options;
    }
    
    protected String getMessage(String name) {
        return messages.getProperty(name);
    }
    
    /**
     * Añade una salida.
     * @param output Salida.
     */
    public void addOutput(OutputStream output) {
        if (output != null) {
            this.outputs.add(output);
        }
    }
    
    /**
     * Añade una lista de salidas.
     * @param outputs Salidas.
     */
    public void addAllOutputs(List<OutputStream> outputs) {
        if (outputs != null && !outputs.isEmpty()) {
            for(OutputStream o : outputs) {
                addOutput(o);
            }
        }
    }
    
    /**
     * Elimina una salida.
     * @param outputs Salida.
     */
    public void removeOutput(OutputStream output) {
        if (output != null) {
            this.outputs.remove(output);
        }
    }
    
    /**
     * Elimina una lista de salidas.
     * @param outputs Salidas.
     */
    public void removeAllOutputs(List<OutputStream> outputs) {
        if (outputs != null) {
            this.outputs.removeAll(outputs);
        }
    }
    /**
     * Limpia la lista de salidas.
     */
    public void clearOutputs() {
        outputs.clear();
    }
    public void initOutputs(List<OutputStream> outputs) {
        this.outputs.clear();
        addAllOutputs(outputs);
    }
    
    /**
     * Salidas en las que escribe.
     * @return Lista de salidas.
     */
    protected List<OutputStream> getOutputs() {
        return outputs;
    }
    
   
    /**
     * Escribe la hora de comienzo.
     * @param time HOra de inicio.
     */
    public void startTime(Date time) {
        
        if (isPerformanceEnabled()) {
            startTime = time.getTime();
            logger.info(PERFORMANCE_MARKER, getMessage(PERFORMANCE_INIT), time);
        }
    }
    
    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     * @param time Hora de fin.
     */
    public void endTime(Date time) {
        if(isPerformanceEnabled()) {
            long total = time.getTime() - startTime;
            logger.info(PERFORMANCE_MARKER,getMessage(PERFORMANCE_END), time);
            logger.info(PERFORMANCE_MARKER,getMessage(PERFORMANCE_TOTAL), total);
        }
    }
    
    /**
     * Write a message with History Appender.
     * @param message Message.
     * @param args Message arguments.
     */
    public void history(String message, Object ... args) {
        if(isHistoryEnabled()) {
            logger.info(HISTORY_MARKER, message, args);
        }
    }
    
    
    /**
     * Write a message with Statistics Appender.
     * @param message Message.
     * @param args Message arguments.
     */
    public void statistics(String message, Object ... args) {
        if(isStatisticsEnabled()) {
            logger.info(STATISTICS_MARKER, message, args);
        }
    }
    
}
