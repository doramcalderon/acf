package es.uma.pfc.is.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgMessages;
import static es.uma.pfc.is.algorithms.AlgMessages.*;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Logger.
 *
 * @author Dora Calderón
 */
public class AlgorithmLogger {

    /**
     * Marker for performance outputs.*
     */
    protected static final Marker PERFORMANCE_MARKER = MarkerFactory.getMarker("PERFORMANCE");
    /**
     * Marker for statistics outputs.*
     */
    protected static final Marker STATISTICS_MARKER = MarkerFactory.getMarker("STATISTICS");
    /**
     * Marker for history outputs.*
     */
    protected static final Marker HISTORY_MARKER = MarkerFactory.getMarker("HISTORY");

    /**
     * SLF4J Logger.
     */
    protected Logger logger;
    /**
     * I18n messages.
     */
    private AlgMessages i18nMessages;
    /**
     * Algorithm options.
     */
    private AlgorithmOptions options;

    private long startTime;
    /**
     * Date format for time outputs.*
     */
    private final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
    /**
     * Algorithm name. *
     */
    private final String algorithmName;
    /**
     * Output name. *
     */
    private String output;

    /**
     * Logging context.
     */
    private LoggerContext loggerContext;
    /**
     * Log messages.
     */
    private List<MessageLog> messages;
    /**
     * If the messages flush to loggers immediately.
     */
    private boolean immediateFlush;
    
    /**
     * Constructor.
     *
     * @param algorithmName Algorithm name.
     * @param options
     */
    public AlgorithmLogger(String algorithmName, AlgorithmOptions options) {
        this(algorithmName, options, false);
    }
    
    /**
     * Constructor.
     *
     * @param algorithmName Algorithm name.
     * @param options
     * @param immediateFlush If the messages flush to loggers immediately.
     */
    public AlgorithmLogger(String algorithmName, AlgorithmOptions options, boolean immediateFlush) {
        configure("logback.xml");
        messages = new ArrayList<>();
        logger = LoggerFactory.getLogger(algorithmName);
        this.algorithmName = algorithmName;
        this.options = options;
        this.output = getOutputName();
        this.i18nMessages = AlgMessages.get();
        this.immediateFlush = immediateFlush;
    }
    
    /**
     * Configure logging context.
     * @param configFile
     */
    protected final void configure(String configFile) {
        try {
            InputStream configFileStream = AlgorithmLoggerFactory.class.getResourceAsStream(configFile);

            loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            loggerContext.reset();
            configurator.setContext(loggerContext);
            configurator.doConfigure(configFileStream);
        } catch (JoranException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the output base name for history and statistics logs.<br/>
     * If the output file name is configured in algorithm options, this will be the output name for logs.<br/>
     * Otherwise, the ouput name will be the algorithm name.
     *
     * @return Output name.
     */
    protected final String getOutputName() {
        String outputName = algorithmName;
        if (options != null) {
            String outputDir = options.getOption(Options.OUTPUT);
            if (outputDir == null) {outputDir = "";}
            
            String logBaseName = options.getOption(Options.LOG_BASE_NAME);
//            System.out.println("[AlgorithmLogger] LOG_BASE_NAME: " + logBaseName);
            if (logBaseName == null) {logBaseName = algorithmName;}
            
            outputName = Paths.get(outputDir, logBaseName).toString();
        }
//        System.out.println("[AlgorithmLogger] outputName: " + outputName);
        return outputName;
    }

    /**
     * Testing usage.
     *
     * @return Dateformat.
     */
    protected DateFormat getDf() {
        return df;
    }

    /**
     * If {@link Mode#PERFORMANCE} mode is enabled.
     *
     * @return {@code true} if {@link Mode#PERFORMANCE} is enabled, {@code false} otherwise.
     */
    public boolean isPerformanceEnabled() {
        return options.isEnabled(Mode.PERFORMANCE);
    }

    /**
     * If {@link Mode#STATISTICS} mode is enabled.
     * @return {@code true} if {@link Mode#STATISTICS} is enabled, {@code false} otherwise.
     */
    public boolean isStatisticsEnabled() {
        return options.isEnabled(Mode.STATISTICS);
    }

    /**
     * If {@link Mode#TRACE} mode is enabled.
     *
     * @return {@code true} if {@link Mode#TRACE} is enabled, {@code false} otherwise.
     */
    public boolean isHistoryEnabled() {
        return options.isEnabled(Mode.TRACE);
    }

    /**
     * Sets the algorithm options.
     * @param options Options.
     */
    public void setOptions(AlgorithmOptions options) {
        this.options = options;
        this.output = getOutputName();
    }

    /**
     * Returns a marker of a mode.
     *
     * @param mode Mode.
     * @return Marker.
     */
    protected Marker getMarker(Mode mode) {
        Marker marker = null;
        switch (mode) {
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
    /**
     * Flush the messages to loggers.
     */
    public void flush() {
       messages.stream().filter(msg -> msg != null)
               .forEach(msg -> logFile(msg.getMode(), msg.getMessage(), msg.getArgs()));
       messages.clear();
    }
    /**
     * Adds a message to log.
     * @param mode Mode.
     * @param message Message.
     * @param args Arguments.
     */
    public void log(Mode mode, String message, Object... args) {
        if(immediateFlush) {
            logFile(mode, message, args);
        } else {
            messages.add(new MessageLog(mode, message, args));
        }
    }
    /**
     * Log to the file.
     * @param mode Mode.
     * @param message Message.
     * @param args Arguments.
     */
    public void logFile(Mode mode, String message, Object... args) {
        MDC.put("outputAlg", output);
        logger.info(getMarker(mode), message, args);
    }

    /**
     * Writhes the start time.
     */
    public void startTime() {
        long time = System.currentTimeMillis();
        if (isPerformanceEnabled()) {
            startTime = time;
            log(Mode.PERFORMANCE, i18nMessages.getMessage(PERFORMANCE_INIT), df.format(new Date(startTime)));
        }
    }

    /**
     * Writes the end time and the start and end difference.
     */
    public long endTime() {
        long total = 0;
        long time = System.currentTimeMillis();
        if (isPerformanceEnabled()) {
            total = time - startTime;
            log(Mode.PERFORMANCE, i18nMessages.getMessage(PERFORMANCE_END), df.format(new Date(time)));
            log(Mode.PERFORMANCE, i18nMessages.getMessage(PERFORMANCE_TOTAL), total);
        }
        return total;
    }


    /**
     * Writes a message with History Appender.
     *
     * @param message Message.
     * @param args Message arguments.
     */
    public void history(String message, Object... args) {
        if (isHistoryEnabled()) {
            log(Mode.TRACE, message, args);
        }
    }

    /**
     * Clears the messages.
     */
    public void freeResources() {
        messages.clear();
    }

}
