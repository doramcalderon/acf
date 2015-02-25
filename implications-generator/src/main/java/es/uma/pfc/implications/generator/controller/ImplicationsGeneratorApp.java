/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.controller;

import es.uma.pfc.implications.generator.view.FXMLViews;
import es.uma.pfc.implications.generator.view.MessagesResourceBundle;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Dora Calderón.
 */
public class ImplicationsGeneratorApp extends Application {
    private Stage primaryStage;
    private BorderPane root;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Implications Generator");
        
        initRootLayout();
        showImplicationsForm();
    }
    
    /**
     * Initialize root layout.
     */
    protected void initRootLayout() {
        try {
            root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource(FXMLViews.ROOT_VIEW));
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Show the implications form inside the root layout.
     */
    protected void showImplicationsForm() {
        try {
            AnchorPane implicationsForm = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource(FXMLViews.IMPLICATIONS_VIEW));
            root.setCenter(implicationsForm);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void close(ActionEvent event) {
        primaryStage.close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
