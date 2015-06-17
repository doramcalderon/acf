package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.view.FXMLViews;
import static es.uma.pfc.is.bench.view.FXMLViews.ABOUT_VIEW;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Root Vieww.
 *
 * @author Dora Calderón
 */
public class RootController extends Controller {

    @FXML
    private Menu preferencesMenu;

    @FXML
    private BorderPane rootPane;
    
    @FXML
    private Tab generatorTab;
    
    @FXML
    private Tab benchmarksTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.initialize(location, resources);
            Pane benchmarksForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.BENCHMARK_VIEW), resources);
            benchmarksTab.setContent(benchmarksForm);
            
            Pane generatorForm = FXMLLoader.load(ISBenchApp.class.getResource("/" + es.uma.pfc.implications.generator.view.FXMLViews.IMPLICATIONS_VIEW), 
                                                 ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", Locale.getDefault()));
            generatorTab.setContent(generatorForm);
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handler of Preferences Menu action event.
     *
     * @param event Event.
     */
    @FXML
    public void handleWorkspacesAction(ActionEvent event) {
        try {
            TabPane configView = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.USER_CONFIG_VIEW), getBundle());
            Scene preferencesScene = new Scene(configView);

            Stage preferencesStage = new Stage();
            preferencesStage.initOwner(rootPane.getScene().getWindow());
            preferencesStage.initModality(Modality.WINDOW_MODAL);
            preferencesStage.setTitle(getI18nString(I18n.WORKSPACE_CONFIG_TITLE));
            preferencesStage.centerOnScreen();
            preferencesStage.setScene(preferencesScene);
            preferencesStage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbout(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(RootController.class.getResource(ABOUT_VIEW), getBundle());
            Scene aboutScene = new Scene(pane);
            
            Stage aboutStage = new Stage();
            aboutStage.initOwner(rootPane.getScene().getWindow());
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.setTitle("About");
            aboutStage.centerOnScreen();
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
