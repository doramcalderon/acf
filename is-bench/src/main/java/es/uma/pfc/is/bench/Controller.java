/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public abstract class Controller implements Initializable {
    private ResourceBundle bundle;

    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
    }

    /**
     * Bundle of view.
     * @return Bundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }
    /**
     * Establish bundle of view.
     * @param bundle Bundle.
     */
    protected void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * Return internazionalized value.
     * @param key Key.
     * @return Internazionalized value if exists, key otherwise.
     */
    public String getI18nString(String key) {
        String value = key;
        if(bundle.containsKey(key)) {
            value = bundle.getString(key);
        }
        return value;
    }
    /**
     * Hook method for get the root pane of the view.
     * @return Root pane of the view. {@code null} if it isn't overrided.
     */
    protected Pane getRootPane(){
        return null;
    }
    
    /**
     * Close the parent window.
     */
    protected void close() {
        Pane rootPane = getRootPane();
        if (rootPane != null) {
            ((Stage) rootPane.getScene().getWindow()).close();
        }
    }
    
}
