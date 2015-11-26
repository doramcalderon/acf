package es.uma.pfc.implications.generator.controller;

import es.uma.pfc.implications.generator.view.FXMLViews;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Implications generator launcher.
 * @author Dora Calderón.
 */
public class ImplicationsGeneratorApp extends Application {
    private Stage primaryStage;
    private BorderPane root;
    private ResourceBundle bundle;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Implications Generator");
        loadResources(Locale.getDefault());
        initRootLayout();
        showImplicationsForm();
    }
    
    /**
     * Load resource bundles.
     * @param locale Locale.
     */
    protected void loadResources(Locale locale) {
        bundle = ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", locale);
    }
    /**
     * Initialize root layout.
     */
    protected void initRootLayout() {
        try {
            root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource(FXMLViews.ROOT_VIEW), bundle);
            
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
            Pane implicationsForm = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource(FXMLViews.IMPLICATIONS_VIEW), bundle);
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
