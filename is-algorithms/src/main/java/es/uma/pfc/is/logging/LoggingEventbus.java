

package es.uma.pfc.is.logging;

import com.google.common.eventbus.EventBus;

/**
 * Singleton eventbus for loggingEvents.
 * @author Dora Calderón
 */
public class LoggingEventbus extends EventBus {
    /**
     * Single instance.
     */
    private static LoggingEventbus me;

    /**
     * Constructor.
     */
    private LoggingEventbus() {
        super("Logging Eventbus");
    }
    /**
     * Returns a single instance of LoggingEvenvbus.
     * @return LoggingEventbus instance.
     */
    public static LoggingEventbus get() {
        synchronized(LoggingEventbus.class) {
            if(me == null) {
                me = new LoggingEventbus();
            }
        }
        return me;
    }
    
}
