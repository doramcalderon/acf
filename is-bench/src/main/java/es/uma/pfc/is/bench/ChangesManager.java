/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ChangesManager {
    /**
     * If algorithms has changed.
     */
    private BooleanProperty algorithmsChanges;
    /**
     * Single instance.
     */
    private static ChangesManager me;

    private ChangesManager() {
        algorithmsChanges = new SimpleBooleanProperty(false);
    }
    
    /**
     * Single instance.
     * @return ChangesManager.
     */
    public static ChangesManager get() {
        synchronized(ChangesManager.class) {
            if(me == null) {
                me = new ChangesManager();
            }
        }
        return me;
    }
    public BooleanProperty getAlgorithmsChanges() {
        return algorithmsChanges;
    }
    

    public void setAlgorithmsChanges(Boolean algorithmsChanges) {
        this.algorithmsChanges.set(algorithmsChanges);
    }
    
    
}
