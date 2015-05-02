
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
import java.util.logging.Level;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;



/**
 * Logger.
 * @author Dora Calderón
 */
public class ISBenchLogger  implements Messages {
    protected Logger logger;
    
    private AlgorithmOptions options;
    private List<OutputStream> outputs;
    private long startTime;
    
    
    private static final Marker performanceMarker = MarkerFactory.getMarker("PERFORMANCE");
            
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
        BasicConfigurator.configure();
        logger = LoggerFactory.getLogger("Performance");
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
    public boolean isTraceEnabled() {
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
     * Escribe un mensaje en las distintas salidas.
     * @param message Mensaje.
     */
    protected void writeMessage(String message, Object ... args)  {
        for (OutputStream output : outputs) {
            writeMessage(message, output);
        }
    }
    /**
     * Escribe el mensaje en una salida.
     * @param message Mensaje.
     * @param output Salida.
     */
    protected void writeMessage(String message, OutputStream output, Object ... args)  {
        String messageWithArgs = String.format(message, args);
        char[] messageChars = messageWithArgs.toCharArray();
        try {
            for (char c : messageChars) {
                output.write(message.getBytes());
            }
            output.write("\n".getBytes());
            output.flush();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PerformanceLogger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ISBenchLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Escribe la hora de comienzo.
     * @param time HOra de inicio.
     */
    public void startTime(Date time) {
        if (isPerformanceEnabled()) {
            startTime = time.getTime();
            writeMessage(getMessage(PERFORMANCE_INIT));
//            logger.info(getMessage(PERFORMANCE_INIT), time);
        }
    }
    
    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     * @param time Hora de fin.
     */
    public void endTime(Date time) {
        if(isPerformanceEnabled()) {
            long total = time.getTime() - startTime;
            writeMessage(getMessage(PERFORMANCE_END), time);
            writeMessage(getMessage(PERFORMANCE_TOTAL), total);
            // logger.info(getMessage(PERFORMANCE_END), time);
            //logger.info(getMessage(PERFORMANCE_TOTAL), total);
        }
    }
    
}
