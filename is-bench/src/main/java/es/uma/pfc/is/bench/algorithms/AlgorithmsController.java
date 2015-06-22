
package es.uma.pfc.is.bench.algorithms;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.tasks.AlgorithmsSaveService;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.validators.ClassNameValidator;
import es.uma.pfc.is.bench.validators.EmptyStringValidator;
import es.uma.pfc.is.commons.persistence.PropertiesPersistence;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class AlgorithmsController extends Controller  {
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
    @FXML
    private Button btnClassesSearch;

    private AlgorithmsModel model;
    ValidationSupport support;
    

    /**
     * Initializes the controller class.
     * @param url URL.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        initView();
        initModel();
        initBindings();
        
    }    
    /**
     * View initialization.
     */
    protected void initView() {
        support = new ValidationSupport();
       
    }
    /**
     * Initialize the model.
     */
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
     * @return {@code true} if validation is succeeded, {@code false} otherwise.
     */
    protected boolean validate() {
        boolean valid = !support.isInvalid();
        if(!valid) {
            lbErrorMessages.setText(getI18nMessage(BenchMessages.EMPTY_VALUES));
        } else {
            try {
                Properties values = PropertiesPersistence.load(AlgorithmsSaveService.ALG_PROPERTIES_PATH);
                
                if(values.containsKey(model.getShortName()+".name")) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, getI18nMessage(BenchMessages.ALGORITHM_EXISTS));
                    valid = a.showAndWait().filter(response -> response.equals(ButtonType.OK)).isPresent();

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return valid;
    }
    
    /**
     * Show the Classes search.
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
        if(validate()) {
            AlgorithmsSaveService saveService = new AlgorithmsSaveService(model);
            saveService.setOnSucceeded(new EventHandler() {

                @Override
                public void handle(Event event) {
                    close();
                }
            });
            saveService.restart();
        }
    }
   
}
