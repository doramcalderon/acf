package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmExecService;
import es.uma.pfc.is.bench.services.AlgorithmsLoadService;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.validators.FilePathValidator;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class BenchmarksController extends Controller {

    /**
     * Modelo.
     */
    private BenchModel model;

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

    /**
     * Algorithms ListView.
     */
    @FXML
    private ListView<Algorithm> algorithmsList;
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
        model = new BenchModel();
        initModel();
        initBinding();
        initListeners();
        modelToView();
        
        readerService = new FileReaderService();
        filePathValidator = new FilePathValidator();
    }

    
    /**
     * Initialize the view.
     */
    @Override
    protected void initView() {
        support = new ValidationSupport();
        btnRun.setDisable(Boolean.TRUE);
        algorithmsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Override
    protected void initBinding() {
        support.registerValidator(txtInput,  Validator.createEmptyValidator("The field can't be empty."));
        
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
        
        algorithmsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Algorithm>() {

            @Override
            public void changed(ObservableValue<? extends Algorithm> observable, Algorithm oldValue, Algorithm newValue) {
                btnRun.setDisable(newValue == null);
                txtOutput.clear();
                if(algorithmsList.getSelectionModel().getSelectedItems().size() == 1) {
                    if(newValue != null) {
                        txtOutput.setDisable(false);
                        txtOutput.setText(model.getDefaultOutput(newValue));
                    }
                } else {
                    txtOutput.setDisable(true);
                }
            }
        });
    }

    /**
     * Inicializa el modelo.
     */
    @Override
    protected void initModel() {
        AlgorithmsLoadService loadService = new AlgorithmsLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> 
            {
                model.setAlgorithms((List<Algorithm>) event.getSource().getValue());
                modelToView();
            });
        loadService.restart();
        
    }

    /**
     * Actualiza la vista con los valores del modelo.
     */
    @Override
    protected void modelToView() {
        FilteredList<Algorithm> filteredData = new FilteredList<>(FXCollections.observableArrayList(model.getAlgorithms()), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(algorithm -> {
                return (StringUtils.isEmpty(newValue) || StringUtils.containsIgnoreCase(algorithm.getName(), newValue));
            });
        });
        algorithmsList.setItems(filteredData);
    }

    /**
     * Actualiza el modelo con los valores de la vista.
     */
    protected void viewToModel() {
        model.setInput(txtInput.getText());
        model.setOutput(txtOutput.getText());
        
        model.setSelectedAlgorithms(algorithmsList.getSelectionModel().getSelectedItems());
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
     * @return {@code true} if values are valids, {@code false} en otro caso.
     */
    protected boolean validate() {
        boolean valid = !support.isInvalid();
        String message = null;
        
        if(valid) {
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
        
        if(!valid) {
            new Alert(Alert.AlertType.ERROR, message).show();
        }
        return valid;
    }
    protected void reload() {
        initModel();
        modelToView();
        ChangesManager.get().setAlgorithmsChanges(Boolean.FALSE);
    }
    
    /**
    /**
     * Añade al modelo la activación de un modo de ejecución.
     *
     * @param mode Modo.
     */
    protected void addModeToModel(Mode mode) {
        if(model.getSelectedAlgorithms() != null) {
            model.getSelectedAlgorithms().forEach(alg -> alg.enable(mode));
        }
    }

    @FXML
    public void handleAddAlgAction(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(RootController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        if(validate()) {
            viewToModel();
            try {
                List<Algorithm> algs = model.getSelectedAlgorithms();
                AlgorithmExecService service = new AlgorithmExecService(algs);
                service.setOnFinished((WorkerStateEvent event1) -> {
                    if(algorithmsList.getSelectionModel().getSelectedItems().size() == 1) {
                        showHistory();
                        showStatistics();
                    }
                    algs.forEach(alg -> alg .reset());
                });
                historyProgressInd.visibleProperty().bind(service.runningProperty());
                statsProgressInd.visibleProperty().bind(service.runningProperty());
                service.restart();

            } catch (AlgorithmException ex) {
                Logger.getLogger(BenchmarksController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }     
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
            if(outputname != null) {
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
        this.algorithmsList.getSelectionModel().clearSelection();
    }
}
