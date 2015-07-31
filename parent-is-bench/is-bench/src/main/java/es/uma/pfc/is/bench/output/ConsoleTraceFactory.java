

package es.uma.pfc.is.bench.output;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory of console that show trace.
 * @author Dora Calderón
 */
public class ConsoleTraceFactory {
    /** Single instance.**/
    private static ConsoleTraceFactory me;
    /** Registered consoles by Mode.**/
    private Map<Mode, ConsolePrintStream> consoles;
    
    /**
     * Constructor.
     */
    private ConsoleTraceFactory() {
        consoles = new HashMap();
    }
    
    public static ConsoleTraceFactory get() {
        synchronized(ConsoleTraceFactory.class) {
            if(me == null) {
                me = new ConsoleTraceFactory();
            }
        }
        return me;
    }
    
    /**
     * Register a console for a {@link Mode}.
     * @param mode Execution mode.
     * @param console 
     */
    public void register(Mode mode, ConsolePrintStream console) {
        consoles.put(mode, console);
                
    }
    /**
     * Gets a console for a mode.
     * @param mode Mode.
     * @return Console.
     */
    public ConsolePrintStream console(Mode mode) { 
        return consoles.get(mode);
    }
            
}
