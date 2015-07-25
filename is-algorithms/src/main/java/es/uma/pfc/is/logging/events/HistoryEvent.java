/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.logging.events;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class HistoryEvent {
    /**
     * Message to trace.
     */
    private String message;
    /**
     * Arguments to replace in the message.
     */
    private Object [] args;
    /**
     * Constructor.
     * @param message Message.
     * @param args Arguments to replace in the message.
     */
    public HistoryEvent(String message, Object ... args) {
        this.message = message;
        this.args = args;
    }

    /**
     * Message to trace.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Message to trace.
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Arguments to replace in the message.
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Arguments to replace in the message.
     * @param args the args to set
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }
    
    

}
