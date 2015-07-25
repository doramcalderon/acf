package es.uma.pfc.is.bench.algorithms;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.algorithms.business.AlgorithmsBean;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.AlgorithmsSaveService;
import es.uma.pfc.is.bench.validators.ClassNameValidator;
import es.uma.pfc.is.bench.validators.EmptyStringValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.validation.ValidationSupport;

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
    private TextField txtAlgClass;
    @FXML
    private Label lbErrorMessages;

    ValidationSupport support;
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
        super.initialize(url, rb);
        initView();
        initModel();
        initBindings();

        algorithmsBean = new AlgorithmsBean();

    }

    /**
     * View initialization.
     */
    @Override
    protected void initView() {
        support = new ValidationSupport();

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
        txtAlgClass.textProperty().bindBidirectional(model.getClassNameProperty());

        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();

        support.registerValidator(txtAlgName, emptyStringValidator);
        support.registerValidator(txtAlgShortName, emptyStringValidator);
        support.registerValidator(txtAlgClass, new ClassNameValidator());
    }

    @Override
    public Pane getRootPane() {
        return gridPane;
    }

    /**
     * Perform form validations.
     *
     * @return {@code true} if validation is succeeded, {@code false} otherwise.
     */
    @Override
    protected boolean validate() {
        boolean valid = !support.isInvalid();
        if (!valid) {
            StringBuilder sb = new StringBuilder();
            support.getValidationResult().getMessages().stream().forEach(msg -> sb.append(msg.getText()).append("\n"));
            lbErrorMessages.setText(sb.toString());
        } else {
            boolean existsName = algorithmsBean.existsName(model.getName());
            boolean existsShortName = algorithmsBean.existsShortName(model.getShortName());
            
            if (existsName || existsShortName) {
                String attribute = (existsName) ? getI18nMessage(BenchMessages.ALGORITHM_NAME) 
                                                :  getI18nMessage(BenchMessages.ALGORITHM_SHORT_NAME);
                
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
            saveService.setOnSucceeded((Event event) -> {close();});
            saveService.restart();
        }
    }

}
