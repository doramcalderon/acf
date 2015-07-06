package es.uma.pfc.is.bench.benchmarks.newbm;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.algorithmslist.view.AlgorithmsListController;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.AlgorithmsLoadService;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class NewBenchmarkController extends Controller {
    @FXML
    private TextField txtName;
     /**
     * Algorithms filter.
     */
    @FXML
    private TextField txtFilter;
    @FXML
    private ListView algorithmsList;
    @FXML
    private ListView algorithmsSelected;
     @FXML
    private Label lbErrorMessages;

    /**
     * Model.
    */
    private NewBenchmarkModel model;
    /**
     * Validation support.
     */
    private ValidationSupport validationSupport;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void initView() throws IOException {

    }
   

    @Override
    protected void initModel() {
        model = new NewBenchmarkModel();
        AlgorithmsLoadService loadService = new AlgorithmsLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            List<Algorithm> algorithms = (List<Algorithm>) event.getSource().getValue();
            model.algorithmsListProperty().clear();
            if(algorithms != null) {
                 model.algorithmsListProperty().set(FXCollections.observableArrayList(algorithms));
            }
            modelToView();
        });
        loadService.restart();
    }
    
     @Override
    protected void initBinding() {
        txtName.textProperty().bindBidirectional(model.nameProperty());
        algorithmsSelected.itemsProperty().bindBidirectional(model.algorithmsSelectedProperty());
    }
    
    @Override
    protected void initListeners() {
        BenchEventBus.get().register(this);
        
        txtFilter.textProperty().addListener(
                (observable, oldValue, newValue) -> { 
                    model.getAlgorithmsFilteredList().setPredicate(algorithm -> {
                        return (StringUtils.isEmpty(newValue) || StringUtils.containsIgnoreCase(algorithm.getName(), newValue));
                    });
                });
        
    }
    
    protected void initValidation() {
       validationSupport = new ValidationSupport();
       validationSupport.registerValidator(txtName, (Control c, String newValue) -> 
         	    ValidationResult.fromErrorIf( c, getI18nMessage(BenchMessages.EMPTY_VALUES), StringUtils.isEmpty(newValue)));
       validationSupport.registerValidator(algorithmsList, Validator.createEmptyValidator(getI18nMessage(BenchMessages.EMPTY_VALUES)));
    }
    
    @Override
    protected void modelToView() {
        algorithmsList.setItems(model.getAlgorithmsFilteredList());
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
            algorithmsSelected.getItems().addAll(event.getAlgorithmsSelection());
        }
    }

    /**
     * When there is a double click in algorithms list, the selection is added to algorithms selected.
     * @param mouseEvent Mouse event.
     */
    @FXML
    private void handleListDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            BenchEventBus.get().post(new AlgorithmsSelectedEvent(algorithmsList.getSelectionModel().getSelectedItems()));
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
