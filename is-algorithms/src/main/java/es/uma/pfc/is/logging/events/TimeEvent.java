/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.logging.events;

import java.time.Instant;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class TimeEvent {

    public enum Type {
        /** Starts the process.**/
        START,
        /** Ends the process.**/
        END
    };
    /**
     * Type.
     */
    private Type type;
    private long instant;

    public TimeEvent(Type type, long instant) {
        this.type = type;
        this.instant = instant;
    }
    
    
    /**
     * Type.
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Type.
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    public long getInstant() {
        return instant;
    }

    public void setInstant(long instant) {
        this.instant = instant;
    }

    
}
