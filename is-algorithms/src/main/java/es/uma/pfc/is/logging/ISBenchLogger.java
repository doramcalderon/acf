package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.Messages;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_INIT;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_TOTAL;
import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.algorithms.io.HistoryFileWriter;
import es.uma.pfc.is.algorithms.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Logger.
 *
 * @author Dora Calderón
 */
public class ISBenchLogger implements Messages {

    private AlgorithmOptions options;
    private ModeStreams modeStreams;
    private long startTime;

    private static ResourceBundle messages;
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
    
    private CSVFileWriter csvWriter;
    
    
    public ISBenchLogger() {
        messages = ResourceBundle.getBundle("es.uma.pfc.is.algorithms.loggingMessages");
        modeStreams = new ModeStreams();
        options = new AlgorithmOptions();
    }

    public ISBenchLogger(AlgorithmOptions options) {
        this();
        if(options != null) {
            setOptions(options);
        }
    }

    /**
     * Constructor.
     *
     * @param clazz Clase.
     */
    public ISBenchLogger(Class clazz) {
        this();
    }

    /**
     * Constructor.
     *
     * @param name Nombre.
     */
    public ISBenchLogger(String name) {
        this();
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
     * Si el modo {@link Mode#HISTORY} está habilitado.
     *
     * @return {@code true} si {@link Mode#HISTORY} está habilitado, {@code false} en otro caso.
     */
    public final boolean isHistoryEnabled() {
        return options.isEnabled(Mode.HISTORY);
    }

    public final void setOptions(AlgorithmOptions options) {
        this.options = options;
        
        if(isHistoryEnabled() || isPerformanceEnabled()) {
            addHistoryWriter();
        }
    }
    
    /**
     * Adds a history writer.
     * @throws NullPointerException when output name is null.
     */
    protected void addHistoryWriter() {
        String outputname = options.getOutputBaseName();
        if (outputname == null) {
            throw new  NullPointerException("The output is mandatory.");
        } else {
            outputname = outputname + "_history.txt";
        } 
        try {
            HistoryFileWriter writer = new HistoryFileWriter(outputname);
            if(isPerformanceEnabled()) {
                modeStreams.add(Mode.PERFORMANCE, writer);
            }
            if(isHistoryEnabled()) {
                modeStreams.add(Mode.HISTORY, writer);
            }
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(ISBenchLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    protected String getMessage(String name) {
        return messages.getString(name);
    }

       

    public void log(Mode mode, String message, Object ... args) {
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
            log(Mode.HISTORY, message, args);
        }
    }

    /**
     * Create the statistics file if statistics mode is enabled.
     * @param name Name of statistics file.
     * @param headers Headers.
     * @throws IOException 
     */
    public void createStatisticLog(String name, Object ... headers ) throws IOException {
        if(isStatisticsEnabled()) {
            csvWriter = new CSVFileWriter(name + ".csv").header(headers);
            csvWriter.start();
        }
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
        if(csvWriter != null) {
            csvWriter.finish();
        }
        if(modeStreams != null) {
            modeStreams.closeAll();
        }
        modeStreams.getMap().clear();
    }
    
    protected SimpleDateFormat getDf() {
        return df;
    }
}
