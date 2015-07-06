
package es.uma.pfc.is.bench.benchmarks.newbm;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * NewBenchFormController class.
 *
 * @author Dora Calderón
 */
public class NewBenchFormController extends Controller {
    
    @FXML
    private TextField txtName;
    @FXML
    private ListView<Algorithm> algorithmsList;
    @FXML
    private Label lbErrorMessages;
    /**
     * Model.
     */
    private NewBenchmarkModel model;
    
    private ValidationSupport validationSupport;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initModel();
        super.initialize(url, rb);
        initBinding();
        initListeners();
        initValidation();
        
    }    

    @Override
    protected void initModel() {
        model = new NewBenchmarkModel();
        // TODO cargar lista de algoritmos
    }

    
    @Override
    protected void initBinding() {
        txtName.textProperty().bindBidirectional(model.nameProperty());
        algorithmsList.itemsProperty().bindBidirectional(model.algorithmsSelectedProperty());
    }

    @Override
    protected void initListeners() {
        BenchEventBus.get().register(this);
    }
    
    
    protected void initValidation() {
       validationSupport = new ValidationSupport();
       validationSupport.registerValidator(txtName, (Control c, String newValue) -> 
         	    ValidationResult.fromErrorIf( c, getI18nMessage(BenchMessages.EMPTY_VALUES), StringUtils.isEmpty(newValue)));
       validationSupport.registerValidator(algorithmsList, Validator.createEmptyValidator(getI18nMessage(BenchMessages.EMPTY_VALUES)));
    }
    
    protected boolean validate() {
        boolean isValid = !validationSupport.isInvalid();
        if (!isValid) {
            StringBuilder sb = new StringBuilder();
            validationSupport.getValidationResult().getMessages().stream().forEach(msg -> sb.append(msg.getText()).append("\n"));
            lbErrorMessages.setText(sb.toString());
        }
        return isValid;
    }
    
    @Subscribe
    public void algorithmsSelected(AlgorithmsSelectedEvent event) {
        if (event.getAlgorithmsSelection() != null) {
            algorithmsList.getItems().addAll(event.getAlgorithmsSelection());
        }
    }
    
    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if(validate()) {
            
        }
    }
    
    @FXML
    protected void handleClearButton(ActionEvent event) {
        txtName.clear();
        algorithmsList.getItems().clear();
        lbErrorMessages.setText("");
        
    }
}
