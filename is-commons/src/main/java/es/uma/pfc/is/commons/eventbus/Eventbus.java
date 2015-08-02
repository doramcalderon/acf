

package es.uma.pfc.is.commons.eventbus;

import com.google.common.eventbus.EventBus;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Eventbus {

    /**
     * Eventbus.
     */
    private static final EventBus eventBus = new EventBus("Bench");
    
    /**
     * Register a listener.
     * @param listener Listener.
     */
    public static void register(Object listener) {
        eventBus.register(listener);
    }
    /**
     * Unregister a listener.
     * @param listener Listener.
     */
    public static void unregister(Object listener) {
        eventBus.unregister(listener);
    }
    
    public static void post(Object event) {
        eventBus.post(event);
    }
}
