
package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.commons.workspace.Workspace;
import es.uma.pfc.is.commons.workspace.WorkspaceManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class UserConfigController extends Controller {
    @FXML
    private AnchorPane wsAnchorPane;
    @FXML
    private TextField txtCurrentWorkspace;
    @FXML
    private ComboBox<Workspace> cbWorkspaces;
    private UserConfigModel model;
    private ConfigManager userConfig;
    private WorkspaceManager wsManager;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        userConfig = ConfigManager.get();
        wsManager = WorkspaceManager.get();
        initModel();
        initBinding();
        modelToView();
    }    

    @Override
    public Pane getRootPane() {
        return wsAnchorPane;
    }

    @Override
    protected void initModel() {
        model = new UserConfigModel();
        model.setWorkspacesList(wsManager.registeredWorkspaces());
    }

    @Override
    protected void initBinding() {
        cbWorkspaces.itemsProperty().bind(model.workspacesListProperty());
        txtCurrentWorkspace.textProperty().bind(cbWorkspaces.selectionModelProperty().asString());
        
        
        ObjectBinding<Workspace> selectedWsBinding = new ObjectBinding<Workspace>() {

            @Override
            protected Workspace computeValue() {
                return cbWorkspaces.getSelectionModel().selectedItemProperty().get();
            }
        };
        
    }
    
    
    
    
    public void save() {
        viewToModel();
        try {
            userConfig.save();
        } catch (IOException ex) {
            Logger.getLogger(UserConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void viewToModel() {
        userConfig.setDefaultWorkspace(wsManager.currentWorkspace().getLocation());
    }
    
    @FXML
    protected void changeWorkspace(ActionEvent event) {
        txtCurrentWorkspace.setText(model.getWorkspaceSelected().getName());
    }
    
    @FXML
    protected void handleSelectWorkspace(ActionEvent event) {
        Window mainStage = wsAnchorPane.getScene().getWindow();
        
        File selectedDir = Chooser.openDirectoryChooser(mainStage, getI18nLabel(I18n.SELECT_WORKSPACE_DIALOG_TITLE), 
                                                        new File(userConfig.getDefaultWorkspace()));
        if(selectedDir != null) {
            
            //txtDefaultWorkspace.setText(selectedDir.getPath());
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
 
}
