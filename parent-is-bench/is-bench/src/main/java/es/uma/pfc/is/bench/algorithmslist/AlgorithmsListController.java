/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.algorithmslist;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.services.AlgorithmsLoadService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class AlgorithmsListController extends Controller {

    @FXML
    private ListView algorithmsList;

    private AlgorithmsListModel model;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            initView();
            initModel();
            initBinding();
            initListeners();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmsListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void initModel() {
        model = new AlgorithmsListModel();
        AlgorithmsLoadService loadService = new AlgorithmsLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            model.setAlgorithms((List<Algorithm>) event.getSource().getValue());
            modelToView();
        });
        loadService.restart();
    }

    @Override
    protected void initListeners() {
//        algorithmsList.setOnMouseClicked(new EventListMouseClickedHandler());
    }

    @Override
    protected void modelToView() {
        algorithmsList.getItems().setAll(model.getAlgorithms());
    }

}
