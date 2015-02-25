/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @since @author Dora Calderón
 */
public class RootController implements Initializable {

    @FXML
    private MenuItem aboutOption;
    
    @FXML
    private BorderPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleHelpOption(ActionEvent event) {
//        try {  
            WebView helpViewer = new WebView();
        String html = Thread.currentThread().getContextClassLoader().getResource("es/uma/pfc/implications/generator/help/help.xhtml").toExternalForm();
        
        helpViewer.getEngine().load(html);
            Stage st = new Stage();
            st.setScene(new Scene(helpViewer));
            st.show();
    }
    

}
