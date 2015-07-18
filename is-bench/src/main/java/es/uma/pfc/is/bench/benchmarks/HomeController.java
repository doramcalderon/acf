/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks;

import es.uma.pfc.is.bench.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class HomeController extends Controller implements Initializable {
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }    
    
    @FXML
    public void handleRunAction(ActionEvent event) {
        ActionsManager.get().action(ActionsManager.RUN);
    }
    
    @FXML
    public void handleNewAction(ActionEvent event) {
        ActionsManager.get().action(ActionsManager.NEW);
    }
    
}
