package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.io.PrintStream;
import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Output that show the trace of a Mode.
 *
 * @author Dora Calderón
 */
public class ModeStreams {

    private Map<Mode, List<PrintStream>> modeStreams;

    /**
     * Constructor.
     */
    public ModeStreams() {
        modeStreams = new HashMap();
    }

    /**
     * Add output to Mode.
     *
     * @param mode Mode.
     * @param output OutputStream.
     */
    public void add(Mode mode, OutputStream output) {
        add(mode, new PrintStream(output));
    }

    /**
     * Add output to Mode.
     *
     * @param mode Mode.
     * @param output OutputStream.
     */
    public void add(Mode mode, PrintStream output) {
        if (output != null) {
            List<PrintStream> streams = modeStreams.get(mode);
            if (streams == null) {
                streams = new ArrayList();
            }
            streams.add(output);

            modeStreams.put(mode, streams);
        }
    }

    /**
     * Añade una lista de salidas.
     *
     * @param mode Mode.
     * @param outputs Salidas.
     */
    public void addAll(Mode mode, List<OutputStream> outputs) {
        if (outputs != null && !outputs.isEmpty()) {
            for (OutputStream o : outputs) {
                if (o != null) {
                    if (!(o instanceof PrintStream)) {
                        add(mode, new PrintStream(o));
                    } else {
                        add(mode, o);
                    }
                }
            }
        }
    }

    /**
     * Remove an output from mode.
     *
     * @param mode Mode.
     * @param output Output.
     */
    public void remove(Mode mode, PrintStream output) {
        if (output != null) {
            List<PrintStream> streams = modeStreams.get(mode);
            if (streams != null) {
                streams.remove(output);
            }
        }
    }

    /**
     * Remove a list of outptus from a mode.
     *
     * @param mode Mode.
     * @param outputs Outputs.
     */
    public void removeAll(Mode mode, List<OutputStream> outputs) {
        if (outputs != null) {
            List<PrintStream> streams = modeStreams.get(mode);
            if (streams != null) {
                streams.removeAll(outputs);
            }
        }
    }

    /**
     * Clear all outputs from a mode.
     *
     * @param mode Mode.
     */
    public void clearOutputs(Mode mode) {
        if (modeStreams.containsKey(mode)) {
            modeStreams.get(mode).clear();
        }
    }

    public void initOutputs(Mode mode, List<OutputStream> outputs) {
        List<PrintStream> streams = modeStreams.get(mode);
        if (streams != null) {
            closeAll(mode);
            streams.clear();
        }

        addAll(mode, outputs);
    }

    public void initOutputs(Map<Mode, List<PrintStream>> outputs) {
        if (modeStreams != null) {
            closeAll();
        }
        modeStreams = outputs;
    }

    /**
     * Write a message in all mode outputs.
     *
     * @param mode Mode.
     * @param message Message.
     * @param args Arguments.
     */
    public void println(Mode mode, String message, Object... args) {
        List<PrintStream> printStreams = modeStreams.get(mode);
        if (printStreams != null) {
            for (PrintStream ps : printStreams) {
                ps.println(StringUtils.replaceArgs(message, "{}", args));
            }

        }
    }

    /**
     * Close all streams.
     */
    public void closeAll() {
        for (Mode mode : modeStreams.keySet()) {
            closeAll(mode);
        }
    }

    /**
     * Close all streams for a mode.
     * @param mode Mode.
     */
    protected void closeAll(Mode mode) {
        List<PrintStream> streams = modeStreams.get(mode);
        streams.stream().map((stream) -> {
            stream.flush();
            return stream;
        }).forEach((stream) -> {
            if(stream.isCloseAtFinish()) {
                stream.close();
            }
        });
    }

    protected Map<Mode, List<PrintStream>> getMap() {
        return modeStreams;
    }
}
