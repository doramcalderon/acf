package es.uma.pfc.is.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgMessages;
import static es.uma.pfc.is.algorithms.AlgMessages.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private AlgMessages messages;
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
     * Constructor.
     *
     * @param algorithmName Algorithm name.
     */
    public AlgorithmLogger(String algorithmName, AlgorithmOptions options) {
        configure("logback.xml");
        this.algorithmName = algorithmName;
        logger = LoggerFactory.getLogger(algorithmName);
        this.options = options;
        this.output = getOutputName();
        this.messages = AlgMessages.get();
    }

    /**
     * Configure logging context.
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
     * Return the output base name for history and statistics logs.<br/>
     * If the output file name is configured in algorithm options, this will be the output name for logs.<br/>
     * Otherwise, the ouput name will be the algorithm name.
     *
     * @return Output name.
     */
    protected final String getOutputName() {
        String outputName = algorithmName;
        if (options != null) {
            String outputBaseName = options.getOutputBaseName();
            if (outputBaseName != null && !outputBaseName.isEmpty()) {
                outputName = outputBaseName;
            }
        }
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
    public boolean isHistoryEnabled() {
        return options.isEnabled(Mode.HISTORY);
    }

    public void setOptions(AlgorithmOptions options) {
        this.options = options;
        this.output = getOutputName();
    }

    /**
     * Return a marker of a mode.
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
            case HISTORY:
                marker = HISTORY_MARKER;
                break;
            case STATISTICS:
                marker = STATISTICS_MARKER;
                break;
        }
        return marker;
    }

    public void log(Mode mode, String message, Object... args) {
        MDC.put("outputAlg", output);
        logger.info(getMarker(mode), message, args);
    }

    /**
     * Escribe la hora de comienzo.
     *
     * @param time HOra de inicio.
     */
    public void startTime() {
        startTime(System.currentTimeMillis());
    }

    public void startTime(long time) {

        if (isPerformanceEnabled()) {
            startTime = time;
            log(Mode.PERFORMANCE, messages.getMessage(PERFORMANCE_INIT), df.format(new Date(startTime)));
        }
    }

    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     *
     * @param time Hora de fin.
     */
    public void endTime(long time) {
        if (isPerformanceEnabled()) {
            long total = time - startTime;
            log(Mode.PERFORMANCE, messages.getMessage(PERFORMANCE_END), df.format(new Date(time)));
            log(Mode.PERFORMANCE, messages.getMessage(PERFORMANCE_TOTAL), total);
        }
    }

    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     */
    public void endTime() {
        endTime(System.currentTimeMillis());
    }

    /**
     * Write a message with History Appender.
     *
     * @param message Message.
     * @param args Message arguments.
     */
    public void history(String message, Object... args) {
        if (isHistoryEnabled()) {
            log(Mode.HISTORY, message, args);
        }
    }

    /**
     * Write a message with Statistics Appender.
     *
     * @param values Values of row.
     */
    public void statistics(Object... values) {
        if (isStatisticsEnabled()) {
            StringBuilder sb = new StringBuilder();
            if (values != null) {
                for (Object value : values) {
                    sb.append(",");
                    sb.append(String.valueOf(value));
                }
                sb.deleteCharAt(0);
            }
            log(Mode.STATISTICS, sb.toString());
        }
    }

    public void freeResources() {

    }

    public void traceOutputs(Map<Mode, List<PrintStream>> outputsByMode) {
        if (outputsByMode != null && !outputsByMode.isEmpty()) {
            List<PrintStream> outputs;
            for (Mode mode : outputsByMode.keySet()) {
                outputs = outputsByMode.get(mode);

                if (outputs != null && !outputs.isEmpty()) {
                    for (PrintStream ps : outputs) {
                        addAppender("", ps);
                    }
                }
            }
        }
    }

    public void addAppender(String name, PrintStream output) {
        OutputStreamAppender appender = new OutputStreamAppender();
        appender.setContext(loggerContext);

        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.setPattern("%m%n");
        layoutEncoder.start();
        appender.setEncoder(layoutEncoder);

        ModeFilter filter = new ModeFilter();
        filter.setMarkers("HISTORY, PERFORMANCE");
        filter.start();
        appender.addFilter(filter);

        appender.setOutputStream(output);
        appender.start();

        ch.qos.logback.classic.Logger lbLogger = (ch.qos.logback.classic.Logger) logger;
        lbLogger.addAppender(appender);
        lbLogger.setAdditive(true);

    }
}
