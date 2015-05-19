package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.Messages;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_INIT;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_TOTAL;
import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.algorithms.io.HistoryFileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Logger.
 *
 * @author Dora Calderón
 */
public class ISBenchLogger implements Messages {

    /**
     * Performance appender name.*
     */
    protected static final String PERFORMANCE_APPENDER = "Performance";
    /**
     * Statistics appender name.*
     */
    protected static final String STATISTICS_APPENDER = "Statistics";
    /**
     * History Appender name.
     */
    protected static final String HISTORY_APPENDER = "History";

    /**
     * Marker for performance outputs.*
     */
    protected static final Marker PERFORMANCE_MARKER = MarkerFactory.getMarker("PERFORMANCE");
    /**
     * Marker for statistics outputs.*
     */
    protected static final Marker STATISTICS_MARKER = MarkerFactory.getMarker("STATS");
    /**
     * Marker for history outputs.*
     */
    protected static final Marker HISTORY_MARKER = MarkerFactory.getMarker("HISTORY");

    /**
     * SLF4J Logger.
     */
    protected Logger logger;

    private AlgorithmOptions options;
    private ModeStreams modeStreams;
    private long startTime;

    private static ResourceBundle messages;
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
    
    private HistoryFileWriter historyWriter;
    
    
    public ISBenchLogger() {
        logger = LoggerFactory.getLogger(ISBenchLogger.class);
        messages = ResourceBundle.getBundle("es.uma.pfc.is.algorithms.loggingMessages");
        modeStreams = new ModeStreams();
        options = new AlgorithmOptions();
    }

    public ISBenchLogger(AlgorithmOptions options) {
        this();
        if(options != null) {
            this.options = options;
        }
    }

    /**
     * Constructor.
     *
     * @param clazz Clase.
     */
    public ISBenchLogger(Class clazz) {
        this();
        logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Constructor.
     *
     * @param name Nombre.
     */
    public ISBenchLogger(String name) {
        this();
        logger = LoggerFactory.getLogger(name);
    }

    /**
     * Si el modo {@link Mode#PERFORMANCE} está habilitado.
     *
     * @return {@code true} si {@link Mode#PERFORMANCE} está habilitado, {@code false} en otro caso.
     */
    public boolean isPerformanceEnabled() {
        return options.isEnabled(Mode.PERFORMANCE);
    }

    /**
     * Si el modo {@link Mode#STATISTICS} está habilitado.
     *
     * @return {@code true} si {@link Mode#STATISTICS} está habilitado, {@code false} en otro caso.
     */
    public boolean isStatisticsEnabled() {
        return options.isEnabled(Mode.STATISTICS);
    }

    /**
     * Si el modo {@link Mode#TRACE} está habilitado.
     *
     * @return {@code true} si {@link Mode#TRACE} está habilitado, {@code false} en otro caso.
     */
    public final boolean isHistoryEnabled() {
        return options.isEnabled(Mode.TRACE);
    }

    public void setOptions(AlgorithmOptions options) {
        this.options = options;
        if (isHistoryEnabled()) {
        try {
            String outputname = options.getOption(Options.OUTPUT.toString());
            if (outputname != null && !outputname.trim().isEmpty()) {
                String name = outputname.substring(0, outputname.lastIndexOf("."));
                String extension = outputname.substring(outputname.lastIndexOf("."), outputname.length());
                outputname = name + "_history." + extension;
            }
            historyWriter = new HistoryFileWriter(outputname);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    }

    protected String getMessage(String name) {
        return messages.getString(name);
    }

    /**
     * Return a marker of a mode.
     * @param mode Mode.
     * @return Marker.
     */
    protected Marker getMarker(Mode mode) {
        Marker marker = null;
        switch(mode) {
            case PERFORMANCE:
                marker = PERFORMANCE_MARKER;
                break;
            case TRACE:
                marker = HISTORY_MARKER;
                break;
            case STATISTICS:
                marker = STATISTICS_MARKER;
                break;
        }
        return marker;
    }

    public void log(Mode mode, String message, Object ... args) {
        logger.info(getMarker(mode), message, args);
        modeStreams.println(mode, message, args);
    }
    
    /**
     * Escribe la hora de comienzo.
     *
     * @param time HOra de inicio.
     */
    public void startTime(Date time) {

        if (isPerformanceEnabled()) {
            startTime = time.getTime();
            log(Mode.PERFORMANCE, getMessage(PERFORMANCE_INIT), df.format(time));
        }
    }

    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     *
     * @param time Hora de fin.
     */
    public void endTime(Date time) {
        if (isPerformanceEnabled()) {
            long total = time.getTime() - startTime;
            log(Mode.PERFORMANCE, getMessage(PERFORMANCE_END), df.format(time));
            log(Mode.PERFORMANCE, getMessage(PERFORMANCE_TOTAL), total);
        }
    }

    /**
     * Write a message with History Appender.
     *
     * @param message Message.
     * @param args Message arguments.
     */
    public void history(String message, Object... args) {
        if (isHistoryEnabled()) {
            if (historyWriter != null) {
                historyWriter.print(message, args);
            }
            log(Mode.TRACE, message, args);
        }
    }

    CSVFileWriter csvWriter = null;
    public void createStatisticLog(String name, Object ... headers ) throws IOException {
        csvWriter = new CSVFileWriter(name + ".csv").header(headers);
        csvWriter.start();
    }
    /**
     * Write a message with Statistics Appender.
     *
     * @param message Message.
     * @param args Message arguments.
     */
    public void statistics(Object ... record) {
        if (isStatisticsEnabled()) {
            try {
                //log(Mode.STATISTICS, message, args);
                csvWriter.printRecord(record);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ISBenchLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addOutput(Mode mode, OutputStream os) {
        modeStreams.add(mode, os);
    }
    
    public void initOutputs(Map<Mode, List<PrintStream>> outputs) {
        modeStreams.initOutputs(outputs);
    }
    
    public void freeResources() {
        csvWriter.finish();
        historyWriter.finish();
    }
}
