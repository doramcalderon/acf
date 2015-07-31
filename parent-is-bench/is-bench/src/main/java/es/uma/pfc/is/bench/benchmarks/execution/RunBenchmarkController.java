package es.uma.pfc.is.bench.benchmarks.execution;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.events.BenchEventBus;
import es.uma.pfc.is.bench.events.BenchmarksChangeEvent;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmExecService;
import es.uma.pfc.is.bench.services.BenchmarksLoadService;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.bench.uitls.Animations;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.validators.FilePathValidator;
import es.uma.pfc.is.javafx.FilterableTreeItem;
import es.uma.pfc.is.javafx.TreeItemPredicate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
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
    private Button btnSelectOutput;

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

    @FXML
    private ProgressIndicator execurtionIndicator;
    @FXML
    private AnchorPane busyLayer;

    /**
     * Service for read a file.
     */
    private FileReaderService readerService;

    FilePathValidator filePathValidator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();

            readerService = new FileReaderService();
        } catch (IOException ex) {
            Logger.getLogger(RunBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

       /**
     * Inicializa el modelo.
     */
    @Override
    protected void initModel() {
        model = new RunBenchmarkModel();
        BenchmarksLoadService loadService = new BenchmarksLoadService();
        loadService.setOnSucceeded((WorkerStateEvent event) -> {
            model.setBenchmarks((List<Benchmark>) event.getSource().getValue());
            modelToView();
        });
        loadService.restart();

    }

    @Override
    protected void initBinding() {
        btnRun.disableProperty().bind(benchmarksTree.getSelectionModel().selectedItemProperty().isNull());
    
        BooleanBinding isBenchmark = new BooleanBinding() {
            {
                super.bind(benchmarksTree.getSelectionModel().selectedItemProperty());
            }

            @Override
            protected boolean computeValue() {
                TreeItem selectedItem = (TreeItem) benchmarksTree.getSelectionModel().getSelectedItem();
                
                return ((selectedItem != null) && (selectedItem.getValue() instanceof Benchmark));
            }
        };
        btnSelectOutput.disableProperty().bind(isBenchmark);
        model.inputProperty().bind(txtInput.textProperty());
        model.outputProperty().bind(txtOutput.textProperty());
    }

    /**
     * Crea los listeners necesarios.
     */
    @Override
    protected void initListeners() {
        BenchEventBus.get().register(this);
        benchmarksTree.getSelectionModel().selectedItemProperty().addListener(new BenchmarkSelectionListener());
    }
   
    @Subscribe
    public void handleBenchamrksChange(BenchmarksChangeEvent event) {
        reload();
    }
    
    @Override
    protected void initValidation() {
        filePathValidator = new FilePathValidator();
        getValidationSupport().registerValidator(txtInput, Validator.createEmptyValidator("The field can't be empty."));
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
        boolean valid = super.validate();
        String message = null;

        if (valid) {
            ValidationResult result = filePathValidator.apply(txtInput, txtInput.getText());
            if (!result.getErrors().isEmpty()) {
                ValidationMessage valMessage = result.getErrors().toArray(new ValidationMessage[]{})[0];
                getValidationSupport().getValidationDecorator().applyValidationDecoration(valMessage);
                message = valMessage.getText();
            } else {
                getValidationSupport().getValidationDecorator().removeDecorations(txtInput);
            }
            valid = result.getErrors().isEmpty();
        } else {
            message = getValidationSupport().getHighestMessage(txtInput).get().getText();
        }

        if (!valid) {
            publicMessage(message, MessageEvent.Level.ERROR);
        }
        return valid;
    }

    protected void reload() {
        initModel();
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
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
            executeBenchmark(model.getSelectedAlgorithms());
        }
    }

    /**
     * Execute a benchmark.
     *
     * @param algs Algorithms to execute.
     */
    protected void executeBenchmark(List<Algorithm> algs) {
        try {
            AlgorithmExecService service = new AlgorithmExecService(model);
            service.setOnRunning((Event event) -> {busyLayer.setVisible(true);});
            service.setOnFinished((WorkerStateEvent event) -> {finishAlgExecution();});

            execurtionIndicator.visibleProperty().bind(service.runningProperty());
            service.restart();

        } catch (AlgorithmException ex) {
            Logger.getLogger(RunBenchmarkController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Show the traces if are checked.
     */
    protected void finishAlgExecution() {
        Animations.fadeOut(busyLayer);
        
        if(model.getSelectedAlgorithm() != null) {
            showHistory();
            showStatistics();
        } else {
            showOpenOutputDir();
        }
    }
    /**
     * Shows a confirm alert for open the output folder.
     */
    protected void showOpenOutputDir() {
        boolean open = showAlert(Alert.AlertType.CONFIRMATION, "Open results", "Would you like see the results?")
                        .filter(response -> response.equals(ButtonType.OK)).isPresent();
        if(open) {
            File resultFile = Chooser.openFileChooser(getRootPane().getScene().getWindow(), 
                                                      Chooser.FileChooserMode.OPEN, "Results", 
                                                      Paths.get(model.getOutput()).toFile());
            if(resultFile != null) {
                showHistory(resultFile.getName(), resultFile);
            }
        }
        
    }
    
    protected void showHistory() {
        if (chkTime.isSelected() || chkHistory.isSelected()) {
            String outputname = model.getOutput();
            String historyName = outputname.substring(0, outputname.lastIndexOf(".")).concat("_history.log");

            showHistory("History", Paths.get(historyName).toFile());
        }
        
    }
    protected void showHistory(String title, File file) {
        readerService.setFile(file);
        historyProgressInd.visibleProperty().bind(readerService.runningProperty());
        txtHistoryArea.textProperty().bindBidirectional(readerService.contentFileProperty());
        readerService.restart();
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
//        model.clear();
        chkTime.setSelected(true);
        chkHistory.setSelected(false);
        chkStatistics.setSelected(false);
        filterField.clear();
        txtInput.clear();
        txtOutput.clear();
        clearTraces();

    }
    
     
    /**
     * Benchmarks tree listener.<br/>
     * When a benchmark is selected, the output field is initialized with benchmark output dir and is disabled.<br/>
     * In this case, the mode checks and console outputs are disabled too.<br/>
     * If the selection is an algorithm, the output field is enabled and initialized with the path of algorithm default
     * output file.<br/>
     * If the selection is more than one algorithm, the output field is cleared and disabled.<br/>
     * If the selection contains benchmarks and algorithms the output field is cleared ant the Run button disabled.
     */
    protected class BenchmarkSelectionListener implements ChangeListener<TreeItem> {

        @Override
        public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldItem, TreeItem newItem) {
            //btnRun.setDisable(newItem == null);
            if (newItem != null) {
                Benchmark selectedBenchmark;
                boolean isBenchmark = (newItem.getValue() instanceof Benchmark);
                
                if (isBenchmark) {
                    selectedBenchmark = (Benchmark) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(null);
                    txtOutput.setText(model.getDefaultOutput(null));
                    
                } else {
                    selectedBenchmark = (Benchmark) newItem.getParent().getValue();
                    Algorithm alg = (Algorithm) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(alg);
                    
                    txtOutput.setText(model.getDefaultOutput(alg));
                    
                }
                
                txtOutput.setDisable(isBenchmark);
                chkTime.setSelected(true);
                chkHistory.setSelected(!isBenchmark && chkHistory.isSelected());
                chkHistory.setDisable(isBenchmark);
                chkStatistics.setSelected(!isBenchmark && chkStatistics.isSelected());
                chkStatistics.setDisable(isBenchmark);
            } else {
                txtOutput.clear();
            }
        }

    }

 

}
