/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.events;

import com.google.common.eventbus.EventBus;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchEventBus {
    /**
     * Eventbus.
     */
    private final EventBus eventBus;
    /**
     * Single instance.
     */
    private static BenchEventBus me;

    /**
     * Constructor.
     */
    private BenchEventBus() {
        eventBus = new EventBus("Bench");
    }
    
    /**
     * Gets a single instance.
     * @return BenchEventBus.
     */
    public static BenchEventBus get() {
        synchronized(BenchEventBus.class) {
            if(me == null) {
                me = new BenchEventBus();
            }
        }
        return me;
    }
    /**
     * Register a listener.
     * @param listener Listener.
     */
    public void register(Object listener) {
        eventBus.register(listener);
    }
    /**
     * Unregister a listener.
     * @param listener Listener.
     */
    public void unregister(Object listener) {
        eventBus.unregister(listener);
    }
    
    public void post(Object event) {
        eventBus.post(event);
    }
}
