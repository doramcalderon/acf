package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import com.google.common.eventbus.Subscribe;
import es.uma.pfc.implications.generator.controller.ImplicationsController;
import es.uma.pfc.implications.generator.events.SystemSaved;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.ISBenchApp;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.benchmarks.newbm.BenchmarksDelegate;
import es.uma.pfc.is.bench.benchmarks.newbm.NewBenchmarkController;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmResultModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeBenchmarkResultModel;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.BenchmarksChangeEvent;
import es.uma.pfc.is.bench.events.NewResultsEvent;
import es.uma.pfc.is.bench.events.ViewFileActionEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmExecService;
import es.uma.pfc.is.bench.services.BenchmarksLoadService;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.bench.uitls.Animations;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.validators.FilePathValidator;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.javafx.FilterableTreeItem;
import es.uma.pfc.is.javafx.NoZeroLongCellFactory;
import es.uma.pfc.is.javafx.TreeItemPredicate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class RunBenchmarkController extends Controller {

    /**
     * Modelo.
     */
    private RunBenchmarkModel model;

    @FXML
    private ListView<String> inputsList;
    /**
     * Path output file.
     */
    @FXML
    private Label lbSelectedFiles;
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
    private ChoiceBox<String> cbOutputType;
    @FXML
    private TextArea txtHistoryArea;
    @FXML
    private TableView tableStatistics;

    @FXML
    private CheckBox chkTime, chkHistory, chkStatistics;

    @FXML
    private ProgressIndicator statsProgressInd;

    @FXML
    private ProgressIndicator execurtionIndicator;
    @FXML
    private AnchorPane busyLayer;

    @FXML
    private TreeTableView<TreeResultModel> tableResults;
    @FXML
    private TreeTableColumn nameColumn, timeColumn, inputColumn, outputColumn;

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

    @Override
    protected void initView() throws IOException {
        inputsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cbOutputType.getItems().add(0, getI18nLabel(I18n.TEXT_FILE));
        cbOutputType.getItems().add(1, getI18nLabel(I18n.PROLOG_FILE));
        cbOutputType.getSelectionModel().select(0);

        MenuItem logItem = new MenuItem("Show log"); // TODO crear label
        logItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showLog();
            }
        });
        tableResults.setContextMenu(new ContextMenu(logItem));
        tableResults.setShowRoot(false);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        timeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("executionTime"));
        timeColumn.setCellFactory(new NoZeroLongCellFactory());
        inputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("input"));
        outputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("output"));

    }

    /**
     * Shows the algorithm result selected log.
     */
    public void showLog() {
        TreeItem<TreeResultModel> item = tableResults.getSelectionModel().getSelectedItem();
        if (item != null && item.getValue().isAlgorithmResult()) {
            String logFile = item.getValue().logProperty().get();
            
            if (!StringUtils.isEmpty(logFile)) {
                Eventbus.post(new ViewFileActionEvent(new File(logFile), logFile,
                                                      getRootPane().getScene().getWindow(),
                                                      getBundle()));
            }
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

        inputsList.itemsProperty().bindBidirectional(model.selectedInputFilesListProperty());
        lbSelectedFiles.textProperty().bind(new StringBinding() {
            {
                super.bind(inputsList.itemsProperty());
            }

            @Override
            protected String computeValue() {
                return BenchMessages.get().getMessage(I18n.SELECTED_FILES_COUNT, inputsList.getItems().size());
            }
        });
        txtOutput.textProperty().bindBidirectional(model.outputDirProperty());
        initModelBinding();
    }

    /**
     * Initialize the bindings with the model.
     */
    protected void initModelBinding() {
        model.timeCheckedProperty().bind(chkTime.selectedProperty());
        model.historyCheckedProperty().bind(chkHistory.selectedProperty());
        model.statisticsCheckedProperty().bind(chkStatistics.selectedProperty());
        model.outputTypeProperty().bind(cbOutputType.getSelectionModel().selectedIndexProperty());
    }

    /**
     * Crea los listeners necesarios.
     */
    @Override
    protected void initListeners() {
        Eventbus.register(this);
        benchmarksTree.getSelectionModel().selectedItemProperty().addListener(new BenchmarkSelectionListener());
        tableResults.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

            if (event.getClickCount() > 1) {
                TreeTableView<TreeResultModel> table = (TreeTableView<TreeResultModel>) event.getSource();
                TreeResultModel value = table.getSelectionModel().getSelectedItem().getValue();
                
                if (value instanceof TreeAlgorithmResultModel) {
                    TreeAlgorithmResultModel resultItem = (TreeAlgorithmResultModel) value;
                    AlgorithmResult algorithmResult = resultItem.getAlgorithmresult();
                    
                    if (!es.uma.pfc.is.algorithms.util.StringUtils.isEmpty(algorithmResult.getLogFile())) {
                        Eventbus.post(new ViewFileActionEvent(new File(algorithmResult.getLogFile()),
                                algorithmResult.getLogFile(), rootPane.getScene().getWindow(),
                                null));
                    }
                }
            }
        });
    }

    @Subscribe
    public void handleBenchamrksChange(BenchmarksChangeEvent event) {
        reload();
    }

    @Override
    protected void initValidation() {
        filePathValidator = new FilePathValidator();
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
                if (bench.getAlgorithmsEntities() != null) {
                    final List<TreeItem> algItems = new ArrayList();
                    bench.getAlgorithmsEntities().stream().forEach(algorithm
                            -> algItems.add(new TreeItem(algorithm)));
                    benchItem.getInternalChildren().addAll(algItems);
                }
                root.getInternalChildren().add(benchItem);
            });

            root.setExpanded(true);
            root.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                if (filterField.getText() == null || filterField.getText().isEmpty()) {
                    return null;
                }
                return TreeItemPredicate.create(
                        benchmark -> StringUtils.containsIgnoreCase(benchmark.toString(), filterField.getText()));
            }, filterField.textProperty()));

            benchmarksTree.setRoot(root);
            benchmarksTree.setShowRoot(false);
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

        return valid;
    }

    protected void reload() {
        initModel();
        initModelBinding();
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
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
            executeBenchmark(model.getSelectedAlgorithms());
        }
    }

    /**
     * Execute a benchmark.
     *
     * @param algs Algorithms to execute.
     */
    protected void executeBenchmark(List<AlgorithmInfo> algs) {
        try {
            AlgorithmExecService service = new AlgorithmExecService(model);
            service.setOnRunning((WorkerStateEvent event) -> {
                busyLayer.setVisible(true);
            });
            service.setOnFinished((WorkerStateEvent event) -> {
                finishAlgExecution((BenchmarkResult) event.getSource().getValue(), event.getSource().getException());
            });

            execurtionIndicator.visibleProperty().bind(service.runningProperty());
            service.restart();

        } catch (AlgorithmException ex) {
            Logger.getLogger(RunBenchmarkController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Show the traces if are checked.
     *
     * @param r Benchmark result.
     * @param ex Exception.
     */
    protected void finishAlgExecution(BenchmarkResult r, Throwable ex) {
        Animations.fadeOut(busyLayer);
        if (ex == null) {
            model.setLastExecutionResult(r);
            tableResults.setRoot(getData(r));
            showStatistics(r.getStatisticsFileName());
            Eventbus.post(new NewResultsEvent());
        }
    }

    /**
     * Shows the result statistics into a table.
     * @param statisticsFile Statistics file path.
     */
    protected void showStatistics(String statisticsFile) {
        if (chkStatistics.isSelected()) {
            if (!StringUtils.isEmpty(statisticsFile)) {
                StatisticsReaderService statisticsReader
                        = new StatisticsReaderService(statisticsFile, tableStatistics);
                statsProgressInd.visibleProperty().bind(statisticsReader.runningProperty());
                statisticsReader.restart();
            }
        }
    }

    /**
     * Handles the {@link SystemSaved} event, copying the path of system into input field.
     *
     * @param event Event.
     */
    @Subscribe
    public void handleSystemSaved(SystemSaved event) {
        String[] paths = event.getPaths();
        if (paths != null) {
            Arrays.stream(paths)
                    .forEach(path -> model.selectedInputFilesListProperty().get().add(Paths.get(path).toString()));
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
        File defaultInputDir = new File(model.getSelectedBenchmark().getInputsDir());

        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN,
                getI18nLabel(I18n.SELECT_INPUT_DIALOG_TITLE), defaultInputDir,
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nLabel(I18n.PROLOG_FILE), "*.pl"));

        if (selectedFile != null) {
            model.selectedInputFilesListProperty().add(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Shows the generator panel for generate a random system.
     *
     * @param event Action event.
     */
    @FXML
    public void handleGenerateSystem(ActionEvent event) {
        try {
            String implicationsPath = Paths.get(model.getSelectedBenchmark().getInputsDir(), "implications.txt").toString();
            FXMLLoader loader = new FXMLLoader(ISBenchApp.class.getResource("/" + es.uma.pfc.implications.generator.view.FXMLViews.IMPLICATIONS_VIEW),
                    ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", Locale.getDefault()));

            Pane generatorForm = loader.load();
            loader.<ImplicationsController>getController().setOutput(implicationsPath);
            String title = getI18nLabel("Implications Generator"); // TODO crear label
            Dialogs.showModalDialog(title, generatorForm, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
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
        File defaultOutputDir = new File(model.getOutputDir());

        File selectedFile = Chooser.openDirectoryChooser(mainStage,
                getI18nLabel(I18n.SELECT_OUTPUT_DIALOG_TITLE),
                defaultOutputDir);
        if (selectedFile != null) {
            txtOutput.setText(selectedFile.getPath());
        }
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
        clearStatistics(null);
    }

    /**
     * Clear the model and the view.
     */
    protected void clear() {
        benchmarksTree.getSelectionModel().clearSelection();
        chkTime.setSelected(true);
        chkHistory.setSelected(false);
        chkStatistics.setSelected(false);
        filterField.clear();
        inputsList.getItems().clear();
        txtOutput.clear();
        tableResults.getRoot().getChildren().clear();
        clearTraces();
        model.setLastExecutionResult(null);
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
            if (newItem != null) {
                tableStatistics.getColumns().clear();

                Benchmark selectedBenchmark;
                boolean isBenchmark = (newItem.getValue() instanceof Benchmark);

                if (isBenchmark) {
                    selectedBenchmark = (Benchmark) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(null);
                    model.outputDirProperty().setValue(
                            Paths.get(selectedBenchmark.getOutputDir()).toString());

                } else {
                    selectedBenchmark = (Benchmark) newItem.getParent().getValue();
                    AlgorithmInfo alg = (AlgorithmInfo) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(alg);
                    model.outputDirProperty().setValue(Paths.get(WorkspaceManager.get().currentWorkspace().getLocation(),
                            selectedBenchmark.getName(),
                            alg.getName(), "output").toString());

                }

                loadInputFiles(model.getSelectedBenchmark().getName());
                chkTime.setSelected(true);
                chkHistory.setSelected(!isBenchmark && chkHistory.isSelected());
                chkHistory.setDisable(isBenchmark);
                chkStatistics.setSelected(!isBenchmark && chkStatistics.isSelected());
            } else {
                txtOutput.clear();
            }
        }

    }

    /**
     * Removes the selected items from the list.
     *
     * @param event Event.
     */
    @FXML
    protected void handleDeleteInputAction(ActionEvent event) {
        List<String> selectedItems = inputsList.getSelectionModel().getSelectedItems();
        inputsList.getItems().removeAll(selectedItems);
    }

    protected void loadInputFiles(String benchmark) {
        BenchmarksDelegate benchmarksDelegate = new BenchmarksDelegate();
        try {
            Benchmark b = benchmarksDelegate.getBenchmark(benchmark);
            if (b != null) {
                File inputsDir = Paths.get(b.getInputsDir()).toFile();
                List<String> inputs = new ArrayList<>();
                Arrays.stream(inputsDir.listFiles()).forEach(p -> inputs.add(p.toString()));

                model.selectedInputFilesListProperty().set(FXCollections.observableArrayList(inputs));
            }
        } catch (Exception ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads the benchmark results into a TreeTableView hierarchy.
     *
     * @param benchResult Benchmark results.
     * @return TreeItem<BenchmarkResultsModel>
     */
    private TreeItem<TreeResultModel> getData(BenchmarkResult benchResult) {
        Map<AlgorithmInfo, List<AlgorithmResult>> results = benchResult.groupByAlgorithm();

        TreeItem<TreeResultModel> rootItem = new TreeItem<>(new TreeBenchmarkResultModel(benchResult));
        List<TreeItem<TreeResultModel>> algorithmItems = new ArrayList<>();
        List<TreeItem<TreeResultModel>> resultItems = new ArrayList<>();

        for (AlgorithmInfo algorithm : results.keySet()) {
            resultItems.clear();
            List<AlgorithmResult> algResults = results.getOrDefault(algorithm, new ArrayList<>());
            TreeItem<TreeResultModel> algorithmItem = new TreeItem<>(new TreeAlgorithmModel(algorithm));

            algResults.stream().map((r) -> new TreeAlgorithmResultModel(r)).forEach((resultNode) -> {
                resultItems.add(new TreeItem<>(resultNode));
            });
            algorithmItem.setExpanded(true);
            algorithmItem.getChildren().addAll(resultItems);
            algorithmItems.add(algorithmItem);

        }
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(algorithmItems);
        return rootItem;
    }

}
