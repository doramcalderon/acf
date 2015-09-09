package es.uma.pfc.is.bench.algorithms;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.AlgorithmsClassLoadService;
import es.uma.pfc.is.bench.services.AlgorithmsSaveService;
import es.uma.pfc.is.bench.validators.ClassNameValidator;
import es.uma.pfc.is.bench.validators.EmptyStringValidator;
import es.uma.pfc.is.bench.domain.ws.WorkspaceManager;
import java.io.IOException;
import java.net.URL;
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

/**
 * Algorithms FXML Controller class.
 *
 * @author Dora Calderón
 */
public class AlgorithmsController extends Controller {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField txtAlgName;
    @FXML
    private TextField txtAlgShortName;
    @FXML
    private ComboBox<String> algorithmsCombo;
    @FXML
    private ProgressIndicator loadingIndicator;
    @FXML
    private Label lbErrorMessages;
    
    private AlgorithmsModel model;
    /**
     * Algorithms business logic.
     */
    private AlgorithmsBean algorithmsBean;

    /**
     * Initializes the controller class.
     *
     * @param url URL.
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
     * Initialize the model.
     */
    @Override
    protected void initModel() {
        model = new AlgorithmsModel();
    }

    /**
     * Initialize the bindings.
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
     * Show the Classes search.
     *
     * @param action Action Event.
     */
    @FXML
    public void handleClassesSearch(ActionEvent action) {

    }

    @FXML
    public void handleCancelAction(ActionEvent action) {
        close();
    }

    @FXML
    public void handleSaveAction(ActionEvent action) {
        save();
    }

    protected void save() {
        if (validate()) {
            AlgorithmsSaveService saveService = new AlgorithmsSaveService(model);
            saveService.setOnSucceeded((Event event) -> {
                close();
            });
            saveService.restart();
        }
    }

}
