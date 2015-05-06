
package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.uitls.Chooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class UserConfigController implements Initializable {
    @FXML
    private AnchorPane wsAnchorPane;
    @FXML
    private TextField txtDefaultWorkspace;

    private UserConfig userConfig;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userConfig = UserConfig.get();
        modelToView();
    }    
    
    public void save() {
        viewToModel();
        try {
            userConfig.save();
        } catch (IOException ex) {
            Logger.getLogger(UserConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void modelToView() {
        txtDefaultWorkspace.setText(userConfig.getDefaultWorkspace());
    }
    protected void viewToModel() {
        userConfig.setDefaultWorkspace(txtDefaultWorkspace.getText());
    }
    
    
    @FXML
    protected void handleSelectWorkspace(ActionEvent event) {
        Window mainStage = wsAnchorPane.getScene().getWindow();
        
        File selectedDir = Chooser.openDirectoryChooser(mainStage, "Select Workspace", 
                                                        new File(userConfig.getDefaultWorkspace()));
        if(selectedDir != null) {
            txtDefaultWorkspace.setText(selectedDir.getPath());
        }
        
    }
    
    @FXML
    protected void handleSaveEvent(ActionEvent event) {
        save();
        close();
    }
    @FXML
    protected void handleCancelAction(ActionEvent event) {
        close();
    }
    
    protected void close() {
        ((Stage) this.wsAnchorPane.getScene().getWindow()).close();
    }
}
