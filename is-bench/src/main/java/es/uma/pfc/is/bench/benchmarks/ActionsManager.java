/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.benchmarks;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ActionsManager {
 public static final String NEW = "new";
    public static final String RUN = "run";
    
    /**
     * If algorithms has changed.
     */
    private StringProperty action;
    private static ActionsManager me;

    public ActionsManager() {
        action = new SimpleStringProperty();
    }
    
     /**
     * Single instance.
     * @return ChangesManager.
     */
    public static ActionsManager get() {
        synchronized(ActionsManager.class) {
            if(me == null) {
                me = new ActionsManager();
            }
        }
        return me;
    }
    
    public StringProperty getActionProperty() {
        return action;
    }
    
    public void action(String action) {
        this.action.set(action);
    }
    
}
