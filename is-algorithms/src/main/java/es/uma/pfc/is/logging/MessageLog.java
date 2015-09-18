
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;

/**
 *
 * @author Dora Calderón
 */
public class MessageLog  {
    private Mode mode;
    private String message;
    private Object [] args;

    public MessageLog(Mode mode, String message, Object ... args) {
        this.mode = mode;
        this.message = message;
        this.args = args;
    }

    public Mode getMode() {
        return mode;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArgs() {
        return args;
    }
    
    
}
