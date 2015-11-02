
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;

/**
 * Algoritm message log.
 * @author Dora Calderón
 */
public class MessageLog  {
    /**
     * Mode.
     */
    private final Mode mode;
    /**
     * Message.
     */
    private final String message;
    /**
     * Arguments.
     */
    private final Object [] args;

    public MessageLog(Mode mode, String message, Object ... args) {
        this.mode = mode;
        this.message = message;
        this.args = args;
    }
    
    /**
     * Gets the mode.
     * @return Mode.
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Gets the message.
     * @return Message.
     */
    public String getMessage() {
        return message;
    }
    /**
     * Gets the arguments.
     * @return Arguments.
     */
    public Object[] getArgs() {
        return args;
    }
    
    
}
