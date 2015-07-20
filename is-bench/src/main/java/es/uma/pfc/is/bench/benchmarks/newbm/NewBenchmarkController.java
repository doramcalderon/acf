package es.uma.pfc.is.bench.benchmarks.newbm;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.algorithmslist.view.AlgorithmsListController;
import es.uma.pfc.is.bench.benchmarks.business.BenchmarksBean;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.events.AlgorithmChangeEvent;
import es.uma.pfc.is.bench.events.AlgorithmsSelectedEvent;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmsLoadService;
import es.uma.pfc.is.bench.services.BenchmarkSaveService;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private AnchorPane rootPane;
    
    /**
     * Model.
    */
    private NewBenchmarkModel model;
    

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
    
    @Override
    protected void initValidation() {
       getValidationSupport().registerValidator(txtName, 
                                    Validator.createEmptyValidator(getI18nMessage(BenchMessages.EMPTY_ALGORITHM_NAME)));
    }
    
    @Override
    protected void modelToView() {
        algorithmsList.setItems(model.getAlgorithmsFilteredList());
    }
    
    /**
     * Form validations.
     * @return {@code true} if there is one algorithm selected at least, and the benchmark name not exists or user
     * wants override it, and parent validations is succeeded. {@code false} otherwise.
     */
    @Override
    protected boolean validate() {
        // si se produce un error por no seleccionar un algoritmo, y la siguiente vez ya se ha seleccionado,
        // no se muestra el error anterior
        // registrando un validator para el ListView, no detecta los cambios en  la lista
//        getValidationSupport().getValidationResult().addErrorIf(algorithmsSelected, 
//                                                              getI18nMessage(BenchMessages.EMPTY_ALGORITHM_LIST), 
//                                                              algorithmsSelected.getItems().isEmpty());
        if(algorithmsSelected.getItems().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, null, getI18nMessage(BenchMessages.EMPTY_ALGORITHM_LIST));
            return false;
        }
        
        boolean validBenchmarkName = !new BenchmarksBean().exists(model.getName(), UserConfig.get().getDefaultWorkspace());
        if(!validBenchmarkName) {
            Optional<ButtonType> confirm = 
                    showAlert(Alert.AlertType.CONFIRMATION, null, getI18nMessage(BenchMessages.DUPLICATED_BENCHMARK));
           
           if(confirm.isPresent() && confirm.get().equals(ButtonType.CANCEL)) {
               return false;
           };
        }
        return super.validate();
    }
    
    protected void reload() {
        initModel();
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }
    
 
    @Subscribe
    public void algorithmsSelected(AlgorithmsSelectedEvent event) {
        if (event.getAlgorithmsSelection() != null) {
            algorithmsSelected.getItems().addAll(event.getAlgorithmsSelection());
        }
    }
    
     
    @Subscribe
    public void handleAlgorithmChanges(AlgorithmChangeEvent event) {
        reload();
    }

    /**
     * When there is a double click in algorithms list, the selection is added to algorithms selected.
     * @param mouseEvent Mouse event.
     */
    @FXML
    protected void handleListDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            BenchEventBus.get().post(new AlgorithmsSelectedEvent(algorithmsList.getSelectionModel().getSelectedItems()));
        }
    }
    
   
   
    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if(validate()) {
            Benchmark benchmark = new Benchmark(model.getName(), model.getAlgorithmsSelectedList());
            BenchmarkSaveService service = new BenchmarkSaveService(benchmark);
            service.setOnSucceeded((WorkerStateEvent event1) -> {clear();});
            service.restart();
        }
    }
    
    @FXML
    protected void handleClearButton(ActionEvent event) {
        clear();
        
    }
    
    @FXML
    public void handleAddAlgAction(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    protected void clear() {
        txtName.clear();
        algorithmsSelected.getItems().clear();
    }
}
