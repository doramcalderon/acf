/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ImplicationsModel {
    IntegerProperty nodes;
    IntegerProperty implications;
    BooleanProperty showImage;

    public ImplicationsModel() {
    }
    
    

    public ImplicationsModel(IntegerProperty nodes, IntegerProperty implications) {
        this.nodes = nodes;
        this.implications = implications;
    }

    
    
    public IntegerProperty getNodes() {
        return nodes;
    }

    public void setNodes(IntegerProperty nodes) {
        this.nodes = nodes;
    }

    public IntegerProperty getImplications() {
        return implications;
    }

    public void setImplications(IntegerProperty implications) {
        this.implications = implications;
    }

    public BooleanProperty getShowImage() {
        return showImage;
    }

    public void setShowImage(BooleanProperty showImage) {
        this.showImage = showImage;
    }
    
    
}
