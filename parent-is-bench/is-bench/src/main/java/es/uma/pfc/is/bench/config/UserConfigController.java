package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.domain.ws.Workspace;
import es.uma.pfc.is.bench.domain.ws.WorkspaceManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.util.StringConverter;

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
    @FXML
    private TableView<PreferenceModel> preferencesTable;
    @FXML
    private TableColumn<PreferenceModel, String> nameColumn;
    @FXML
    private TableColumn<PreferenceModel, String> valueColumn;
    private UserConfigModel model;

    private WorkspaceManager wsManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            wsManager = WorkspaceManager.get();
            initView();
            initModel();
            initBinding();
            initListeners();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(UserConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Pane getRootPane() {
        return wsAnchorPane;
    }

    @Override
    protected void initView() throws IOException {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().getValue());
        
        cbWorkspaces.setConverter(new StringConverter<Workspace>() {
            @Override
            public String toString(Workspace workspace) {
                if (workspace == null) {
                    return null;
                } else {
                    return workspace.getLocation();
                }
            }

            @Override
            public Workspace fromString(String workspaceString) {
                Workspace ws = null;
                if(model.workspacesListProperty().get() != null) {
                    Optional<Workspace> result = model.workspacesListProperty().get()
                                                .filtered(w -> workspaceString.equals(w.getLocation())).stream()
                                                .findFirst();
                    if (result.isPresent()) {
                        ws = result.get();
                    } else {
                        ws = new Workspace();
                        ws.setLocation(workspaceString);
                        ws.setName(Paths.get(workspaceString).getFileName().toString());
                    }
                }
                return ws;
            }
        });
    }

    @Override
    protected void initModel() {
        model = new UserConfigModel();
        model.setWorkspacesList(wsManager.registeredWorkspaces());
        model.setWorkspaceSelected(wsManager.currentWorkspace());
    }

    @Override
    protected void initBinding() {
        cbWorkspaces.itemsProperty().bind(model.workspacesListProperty());
        cbWorkspaces.valueProperty().bindBidirectional(model.workspaceSelected());
        preferencesTable.itemsProperty().bindBidirectional(model.preferencesProperty());

        StringBinding wsNameBinding = new StringBinding() {
            {super.bind(cbWorkspaces.valueProperty());}

            @Override
            protected String computeValue() {
                Workspace selected = cbWorkspaces.getValue();
                return (selected != null) ? selected.getName() : "";
            }  
        };
        txtCurrentWorkspace.textProperty().bind(wsNameBinding);
        
    }

    public void save() {
        List<Workspace> workspaces = model.workspacesListProperty().get();
        boolean newWs;
        boolean changeWs = false;
        
        Workspace wsSelected = model.workspaceSelected().getValue();
        if (workspaces != null) {
            newWs = !workspaces.stream()
                                .filter(w -> w.getLocation().equals(wsSelected.getLocation()))
                                .findFirst().isPresent();
        } else {
            newWs = true;
        }
        if(newWs) {
            Optional<ButtonType> result = this.showAlert(Alert.AlertType.CONFIRMATION, "Current Workspace", "Would you like set the new workspace as the current?");
            changeWs = result.orElse(ButtonType.CANCEL).equals(ButtonType.OK);
            wsManager.create(wsSelected, changeWs);
            
        } else if (!wsManager.currentWorkspace().getName().equals(wsSelected.getName())){
            wsManager.change(wsSelected);
            changeWs = true;
        }
        if(changeWs) {
            showAlert(Alert.AlertType.INFORMATION, "Current Workspace", "The change of workspace will do effective the next startup of the application.");
        }
    }


    @FXML
    protected void handleSelectWorkspace(ActionEvent event) {
        Window mainStage = wsAnchorPane.getScene().getWindow();

        File selectedDir = Chooser.openDirectoryChooser(mainStage, getI18nLabel(I18n.SELECT_WORKSPACE_DIALOG_TITLE),
                new File(wsManager.currentWorkspace().getLocation()));
        if (selectedDir != null) {
            Workspace ws = new Workspace(selectedDir.getName(), selectedDir.getPath());
            cbWorkspaces.setValue(ws);
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
