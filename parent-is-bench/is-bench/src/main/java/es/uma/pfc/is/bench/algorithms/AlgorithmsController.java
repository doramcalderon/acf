package es.uma.pfc.is.bench.algorithms;


import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.AlgorithmsClassLoadService;
import es.uma.pfc.is.bench.services.AlgorithmsSaveService;
import es.uma.pfc.is.bench.validators.ClassNameValidator;
import es.uma.pfc.is.bench.validators.EmptyStringValidator;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.ws.Preferences;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Chooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 * Algorithms FXML Controller class.
 *
 * @author Dora Calderón
 */
public class AlgorithmsController extends Controller {
    /** Root pane.*/
    @FXML
    private GridPane gridPane;
    /** Algorithm name field.*/
    @FXML
    private TextField txtAlgName;
    /** Short name field.**/
    @FXML
    private TextField txtAlgShortName;
    /** Algorithms in the classpath combobox.*/
    @FXML
    private ComboBox<String> algorithmsCombo;
    /** Indicator of algorithms load.**/
    @FXML
    private ProgressIndicator loadingIndicator;
    /** Label wich contains error messages.*/
    @FXML
    private Label lbErrorMessages;
    /**
     * Model.
     */
    private AlgorithmsModel model;
    /**
     * Algorithms business logic.
     */
    private AlgorithmsBean algorithmsBean;

    /**
     * Initializes the controller class.
     *
     * @param url URL of the view.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            initView();
            initModel();
            initBindings();
            initListeners();

            algorithmsBean = new AlgorithmsBean(WorkspaceManager.get().currentWorkspace().getLocation());
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

    /**
     * Initializes the model.
     */
    @Override
    protected void initModel() {
        model = new AlgorithmsModel();
    }

    /**
     * Initializes the bindings between view components and model.
     */
    protected void initBindings() {
        txtAlgName.textProperty().bindBidirectional(model.getNameProperty());
        txtAlgShortName.textProperty().bindBidirectional(model.getShortNameProperty());
        algorithmsCombo.itemsProperty().bind(model.algorithmsProperty());
        model.getClassNameProperty().bind( algorithmsCombo.getEditor().textProperty());

        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();

        getValidationSupport().registerValidator(txtAlgName, emptyStringValidator);
        getValidationSupport().registerValidator(txtAlgShortName, emptyStringValidator);
        getValidationSupport().registerValidator(algorithmsCombo.getEditor(), new ClassNameValidator());
    }

    /**
     * @see Controller#initListeners() 
     * Initializes a text change listener for the algorithms combo.<br/>
     * This listener only executes once time.
     */
    @Override
    protected void initListeners() {
        EventHandler<Event> listener = new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if (model.algorithmsProperty().isNull().get()) {
                    algorithmsCombo.disarm();
                    loadAlgorithmsClasses();
                } else {
                    algorithmsCombo.removeEventHandler(EventType.ROOT, this);
                }

            }

        };
        algorithmsCombo.setOnShowing(listener);
    }

    @Override
    public Pane getRootPane() {
        return gridPane;
    }

    /**
     * Searchs the algorithms implementations in the classpath, and loads it into the model.
     */
    private void loadAlgorithmsClasses() {
        AlgorithmsClassLoadService loadService = new AlgorithmsClassLoadService();
        loadService.setOnSucceeded((WorkerStateEvent ev) -> {
            algorithmsCombo.hide();
            model.setAlgorithms((List<String>) ev.getSource().getValue());
            algorithmsCombo.show();
            //algorithmsCombo.setItems(FXCollections.observableArrayList(model.algorithmsProperty()));
        });
        loadingIndicator.getParent().visibleProperty().bind(loadService.runningProperty());
        loadingIndicator.visibleProperty().bind(loadService.runningProperty());
        loadService.restart();
    }

    /**
     * Perform form validations.
     *
     * @return {@code true} if validation is succeeded, {@code false} otherwise.
     */
    @Override
    protected boolean validate() {
        boolean valid = !getValidationSupport().isInvalid();
        if (!valid) {
            StringBuilder sb = new StringBuilder();
            getValidationSupport().getValidationResult().getMessages().stream().forEach(msg -> sb.append(msg.getText()).append("\n"));
            lbErrorMessages.setText(sb.toString());
        } else {
            boolean existsName = algorithmsBean.existsName(model.getName());
            boolean existsShortName = algorithmsBean.existsShortName(model.getShortName());

            if (existsName || existsShortName) {
                String attribute = (existsName) ? getI18nMessage(BenchMessages.ALGORITHM_NAME)
                        : getI18nMessage(BenchMessages.ALGORITHM_SHORT_NAME);

                Alert a = new Alert(Alert.AlertType.CONFIRMATION, getI18nMessage(BenchMessages.ALGORITHM_EXISTS, attribute));
                valid = a.showAndWait().filter(response -> response.equals(ButtonType.OK)).isPresent();

            }
        }
        return valid;
    }
    
    /**
     * Shows the Open File dialog box for select one or more JARs library which will be added to lib directory 
     * in the current workspace.
     * @param action Event thrown when the Add Lib button is pressed.
     */
    public void handleAddLib(ActionEvent action) {
        File homeDir = Paths.get(System.getProperty("user.home")).toFile();
        List<File> jars = Chooser.openMultipleFileChooser(getRootPane().getScene().getWindow(),
                Chooser.FileChooserMode.OPEN, getI18nLabel(I18n.SELECT_JAR_DIALOG_TITLE), homeDir,
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.JAR_FILE), "*.jar"));
        
        Path targetDir = Paths.get(WorkspaceManager.get().getPreference(Preferences.ALGORITHMS_PATH));
        
        if(jars != null) {
            for(File jar : jars) {
                try {
                    //TODOD hacer que pregunte si el archivo existe ya en el directorio
                    Path targetFile = Paths.get(targetDir.toString(), jar.getName());
                    Files.copy(jar.toPath(), targetFile, StandardCopyOption.COPY_ATTRIBUTES,  StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(AlgorithmsController.class.getName())
                            .log(Level.SEVERE, getI18nMessage(BenchMessages.COPYING_JARS_ERROR, jar, targetDir), ex);
                }
            }
        }
        
    }

    /**
     * Handles the event thrown when Cancel button is pressed.
     * Ignores the changes and closes the window.
     * @param action Event.
     */
    @FXML
    public void handleCancelAction(ActionEvent action) {
        close();
    }

    /**
     * Handles the event thrown whtn Save button is pressed.
     * Saves the changes.
     * @param action Event.
     */
    @FXML
    public void handleSaveAction(ActionEvent action) {
        save();
    }

    /**
     * Validates the changes and if are ok, these are saved.
     */
    protected void save() {
        if (validate()) {
            AlgorithmsSaveService saveService = new AlgorithmsSaveService(model);
            saveService.setOnSucceeded((WorkerStateEvent event) -> {
                close();
            });
           
            saveService.restart();
        }
    }

}
