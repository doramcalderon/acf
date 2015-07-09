package es.uma.pfc.is.bench.benchmarks;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.bench.ChangesManager;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.RootController;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.BenchmarksLoadService;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.validators.FilePathValidator;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.javafx.FilterableTreeItem;
import es.uma.pfc.is.javafx.TreeItemPredicate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class RunBenchmarkController extends Controller {

    /**
     * Modelo.
     */
    private RunBenchmarkModel model;

    /**
     * Input path fle.
     */
    @FXML
    private TextField txtInput;
    /**
     * Path output file.
     */
    @FXML
    private TextField txtOutput;
    /**
     * Algorithms filter.
     */
    @FXML
    private TextField filterField;
    @FXML
    private TreeView benchmarksTree;
    /**
     * Run Button.
     */
    @FXML
    private Button btnRun;
    
    @FXML
    private BorderPane rootPane;
    
    @FXML
    private TextArea txtHistoryArea;
    @FXML
    private TableView tableStatistics;
    
    @FXML
    private CheckBox chkTime, chkHistory, chkStatistics;
    
    @FXML
    private ProgressIndicator historyProgressInd;
    
    @FXML
    private ProgressIndicator statsProgressInd;

    /**
     * Service for read a file.
     */
    private FileReaderService readerService;
    
    ValidationSupport support;
    FilePathValidator filePathValidator;
    Validator requiredValidator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        initView();
        model = new RunBenchmarkModel();
        initModel();
        initBinding();
        initListeners();
        
        readerService = new FileReaderService();
        filePathValidator = new FilePathValidator();
    }

    /**
     * Initialize the view.
     */
    @Override
    protected void initView() {
        support = new ValidationSupport();
        
    }
    
    @Override
    protected void initBinding() {
        support.registerValidator(txtInput, Validator.createEmptyValidator("The field can't be empty."));
    }

    /**
     * Crea los listeners necesarios.
     */
    @Override
    protected void initListeners() {
        
        ChangesManager.get().getAlgorithmsChanges().addListener(new ChangeListener() {
            
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                reload();
            }
        });

//        algorithmsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Algorithm>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Algorithm> observable, Algorithm oldValue, Algorithm newValue) {
//                btnRun.setDisable(newValue == null);
//                txtOutput.clear();
//                if(algorithmsList.getSelectionModel().getSelectedItems().size() == 1) {
//                    if(newValue != null) {
//                        txtOutput.setDisable(false);
//                        txtOutput.setText(model.getDefaultOutput(newValue));
//                    }
//                } else {
//                    txtOutput.setDisable(true);
//                }
//            }
//        });
    }

    /**
     * Inicializa el modelo.
     */
    @Override
    protected void initModel() {
        BenchmarksLoadService loadService = new BenchmarksLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            model.setBenchmarks((List<Benchmark>) event.getSource().getValue());
            modelToView();
        });
        loadService.restart();
        
    }

    /**
     * Actualiza la vista con los valores del modelo.
     */
    @Override
    protected void modelToView() {
        if (model.getBenchmarks() != null) {
            FilterableTreeItem root = new FilterableTreeItem(null);
            
            model.getBenchmarks().stream().forEach((bench) -> {
                FilterableTreeItem benchItem = new FilterableTreeItem(bench);
                if (bench.getAlgorithms() != null) {
                    final List<TreeItem> algItems = new ArrayList();
                    bench.getAlgorithms().stream().forEach(algorithm -> algItems.add(new TreeItem(algorithm)));
                    benchItem.getInternalChildren().addAll(algItems);
                }
                root.getInternalChildren().add(benchItem);
            });
            
            root.setExpanded(true);
            root.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                if (filterField.getText() == null || filterField.getText().isEmpty()) {
                    return null;
                }
                return TreeItemPredicate.create(benchmark -> benchmark.toString().contains(filterField.getText()));
            }, filterField.textProperty()));
            
            benchmarksTree.setRoot(root);
            benchmarksTree.setShowRoot(false);
        }
    }

    /**
     * Actualiza el modelo con los valores de la vista.
     */
    protected void viewToModel() {
        model.setInput(txtInput.getText());
        model.setOutput(txtOutput.getText());
        
        model.setInputOutputs();
        
        if (chkTime.isSelected()) {
            addModeToModel(Mode.PERFORMANCE);
        }
        if (chkHistory.isSelected()) {
            addModeToModel(Mode.HISTORY);
        }
        if (chkStatistics.isSelected()) {
            addModeToModel(Mode.STATISTICS);
        }
    }

    /**
     * Validate the fields values.
     *
     * @return {@code true} if values are valids, {@code false} en otro caso.
     */
    @Override
    protected boolean validate() {
        boolean valid = !support.isInvalid();
        String message = null;
        
        if (valid) {
            ValidationResult result = filePathValidator.apply(txtInput, txtInput.getText());
            if (!result.getErrors().isEmpty()) {                
                ValidationMessage valMessage = result.getErrors().toArray(new ValidationMessage[]{})[0];
                support.getValidationDecorator().applyValidationDecoration(valMessage);
                message = valMessage.getText();
            } else {
                support.getValidationDecorator().removeDecorations(txtInput);
            }
            valid = result.getErrors().isEmpty();
        } else {
            message = support.getHighestMessage(txtInput).get().getText();
        }        
        
        if (!valid) {
            new Alert(Alert.AlertType.ERROR, message).show();
        }
        return valid;
    }

    protected void reload() {
        initModel();
        ChangesManager.get().setAlgorithmsChanges(Boolean.FALSE);
    }

    /**
     * /**
     * Añade al modelo la activación de un modo de ejecución.
     *
     * @param mode Modo.
     */
    protected void addModeToModel(Mode mode) {
        if (model.getSelectedAlgorithms() != null) {
            model.getSelectedAlgorithms().forEach(alg -> alg.enable(mode));
        }
    }
    
  
    /**
     * Manejador del evento ActionEvent del botón <i>Run</i>.<br/>
     * Ejecuta el algoritmo seleccionado.
     *
     * @param event Evento.
     */
    @FXML
    public void handleRunAction(ActionEvent event) {
        clearTraces();
        
        if (validate()) {
            viewToModel();
//            try {
//                List<Algorithm> algs = model.getSelectedAlgorithms();
//                AlgorithmExecService service = new AlgorithmExecService(algs);
//                service.setOnFinished((WorkerStateEvent event1) -> {
//                    if(algorithmsList.getSelectionModel().getSelectedItems().size() == 1) {
//                        showHistory();
//                        showStatistics();
//                    }
//                    algs.forEach(alg -> alg .reset());
//                });
//                historyProgressInd.visibleProperty().bind(service.runningProperty());
//                statsProgressInd.visibleProperty().bind(service.runningProperty());
//                service.restart();
//
//            } catch (AlgorithmException ex) {
//                Logger.getLogger(RunBenchmarkController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }     
        }
    }
    
    protected void showHistory() {
        if (chkTime.isSelected() || chkHistory.isSelected()) {
            String outputname = model.getOutput();
            String historyName = outputname.substring(0, outputname.lastIndexOf(".")).concat("_history.log");
            
            readerService.setFileName(historyName);
            historyProgressInd.visibleProperty().bind(readerService.runningProperty());
            txtHistoryArea.textProperty().bindBidirectional(readerService.contentFileProperty());
            // piExecution.progressProperty().bind(reader.progressProperty());
            readerService.restart();
        }
    }
    
    protected void showStatistics() {
        if (chkStatistics.isSelected()) {
            String outputname = model.getOutput();
            if (outputname != null) {
                StatisticsReaderService statisticsReader
                        = new StatisticsReaderService(outputname.substring(0, outputname.lastIndexOf(".")).concat(".csv"), tableStatistics);
                statsProgressInd.visibleProperty().bind(statisticsReader.runningProperty());
                statisticsReader.restart();
            }
        }
    }

    /**
     * ActionEvent handler of Run button.<br/>
     * Clear all traces.
     *
     * @param event Event.
     */
    @FXML
    public void handleClearAll(ActionEvent event) {
        clear();
    }

    /**
     * Abre el cuadro diálogo para seleccionar la entrada del algoritmo.
     *
     * @param event Evento.
     */
    @FXML
    public void handleSelectInputAction(ActionEvent event) {
        Window mainStage = rootPane.getScene().getWindow();
        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN,
                getI18nLabel(I18n.SELECT_INPUT_DIALOG_TITLE), UserConfig.get().getDefaultInputDir(),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.PROLOG_FILE), "*.pl"));
        if (selectedFile != null) {
            txtInput.setText(selectedFile.getPath());
        }
    }

    /**
     * Abre el cuadro de diálogo para seleccionar el destino de los resultados del algoritmo.
     *
     * @param event Evento.
     */
    @FXML
    public void handleSelectOutputAction(ActionEvent event) {
        
        Window mainStage = rootPane.getScene().getWindow();
        
        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN,
                getI18nLabel(I18n.SELECT_OUTPUT_DIALOG_TITLE), UserConfig.get().getDefaultOutputDir(),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.PROLOG_FILE), "*.pl"));
        if (selectedFile != null) {
            txtOutput.setText(selectedFile.getPath());
        }
    }
    
    @FXML
    public void clearHistory(ActionEvent event) {
        txtHistoryArea.textProperty().set("");
    }
    
    @FXML
    public void clearStatistics(ActionEvent event) {
        tableStatistics.getItems().clear();
        tableStatistics.getColumns().clear();
    }

    /**
     * Clear the textareas content.
     */
    protected void clearTraces() {
        clearHistory(null);
        clearStatistics(null);
    }

    /**
     * Clear the model and the view.
     */
    protected void clear() {
        model.clear();
        chkTime.setSelected(true);
        chkHistory.setSelected(false);
        chkStatistics.setSelected(false);
        filterField.clear();
        txtInput.clear();
        txtOutput.clear();
        clearTraces();
        
    }
}
