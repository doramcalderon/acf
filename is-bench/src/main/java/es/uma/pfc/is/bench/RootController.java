

package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Root Vieww.
 * @author Dora Calderón
 */
public class RootController implements Initializable {
    @FXML
    private Menu preferencesMenu;

    @FXML
    private BorderPane rootPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");   
    }
    
    /**
     * Handler of Preferences Menu action event.
     * @param event Event.
     */
    @FXML
    public void handleWorkspacesAction(ActionEvent event) {
        try {
            TabPane configView = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.USER_CONFIG_VIEW));
            Scene preferencesScene = new Scene(configView);
            
            Stage preferencesStage = new Stage();
            preferencesStage.initOwner(rootPane.getScene().getWindow());
            preferencesStage.initModality(Modality.WINDOW_MODAL);
            preferencesStage.setTitle(" Workspaces");
            preferencesStage.centerOnScreen();
            preferencesStage.setScene(preferencesScene);
            preferencesStage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
