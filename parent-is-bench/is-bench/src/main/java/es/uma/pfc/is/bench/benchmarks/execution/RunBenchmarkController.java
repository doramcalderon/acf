package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import com.google.common.eventbus.Subscribe;
import es.uma.pfc.implications.generator.controller.ImplicationsController;
import es.uma.pfc.implications.generator.events.SystemSaved;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.ISBenchApp;
import es.uma.pfc.is.bench.benchmarks.newbm.BenchmarksDelegate;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.Benchmark;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmResultModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeBenchmarkResultModel;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.BenchmarksChangeEvent;
import es.uma.pfc.is.bench.events.MessageEvent.Level;
import es.uma.pfc.is.bench.events.NewResultsEvent;
import es.uma.pfc.is.bench.events.OpenFileEvent;
import es.uma.pfc.is.bench.events.ViewFileActionEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.AlgorithmExecService;
import es.uma.pfc.is.bench.services.BenchmarksLoadService;
import es.uma.pfc.is.bench.uitls.Animations;
import es.uma.pfc.is.bench.uitls.Chooser;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.javafx.FilterableTreeItem;
import es.uma.pfc.is.javafx.NoZeroDoubleCellFactory;
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
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run view controller.
 *
 * @author Dora Calderón
 */
public class RunBenchmarkController extends Controller {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(RunBenchmarkController.class);

    /**
     * Model.
     */
    private RunBenchmarkModel model;

    @FXML
    private ListView<String> inputsList;
    /**
     * Path output file.
     */
    @FXML
    private Label lbSelectedFiles;
    /**
     * Output field.
     */
    @FXML
    private TextField txtOutput;
    /**
     * Algorithms filter.
     */
    @FXML
    private TextField filterField;
    /**
     * Benchmarks and algorithms tree.
     */
    @FXML
    private TreeView benchmarksTree;
    /**
     * Run Button.
     */
    @FXML
    private Button btnRun;

    /**
     * Root pane.
     */
    @FXML
    private BorderPane rootPane;
    /**
     * Output type combobox.
     */
    @FXML
    private ChoiceBox<String> cbOutputType;

    /**
     * Mode checks.
     */
    @FXML
    private CheckBox chkTime, chkHistory, chkStatistics;

    /**
     * Statistics loading progress indicator.
     */
    @FXML
    private ProgressIndicator statsProgressInd;
    /**
     * Execution progress indicator.
     */
    @FXML
    private ProgressIndicator execurtionIndicator;
    /**
     * Layer which contains progress indicators.
     */
    @FXML
    private AnchorPane busyLayer;
    /**
     * Execution results table.
     */
    @FXML
    private TreeTableView<TreeResultModel> tableResults;
    /**
     * Columns of execution results table.
     */
    @FXML
    private TreeTableColumn nameColumn, timeColumn, sizeColumn, cardColumn, inputColumn, outputColumn;

    private AlgorithmExecService service;

    /**
     * Initializes the controller.
     *
     * @param url URL of the view.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();
            initExecutionService();
        } catch (IOException ex) {
            logger.error("Error initializing the RunBenchmarkController.", ex);
        }
    }

    /**
     * Initializes the algorithm execution service.
     */
    protected void initExecutionService() {
        service = new AlgorithmExecService();
        service.setOnRunning((WorkerStateEvent event) -> {
            busyLayer.setVisible(true);
        });
        service.setOnFinished((WorkerStateEvent event) -> {
            finishAlgExecution((BenchmarkResult) event.getSource().getValue(), event.getSource().getException());
        });

        execurtionIndicator.visibleProperty().bind(service.runningProperty());
    }

    /**
     * Initializes the view.
     *
     * @throws IOException
     */
    @Override
    protected void initView() throws IOException {
        inputsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cbOutputType.getItems().add(0, getI18nLabel(I18n.TEXT_FILE));
        cbOutputType.getItems().add(1, getI18nLabel(I18n.PROLOG_FILE));
        cbOutputType.getSelectionModel().select(0);

        MenuItem logItem = new MenuItem(getI18nLabel(I18n.SHOW_LOG));
        logItem.setOnAction((ActionEvent event) -> {
            showLog();
        });
        MenuItem statisticsItem = new MenuItem(getI18nLabel(I18n.SHOW_STATS));
        logItem.setOnAction((ActionEvent event) -> {
            showLog();
        });
        statisticsItem.setOnAction((ActionEvent event) -> {
            showStatisticsFile();
        });
        tableResults.setContextMenu(new ContextMenu(logItem, statisticsItem));
        tableResults.setShowRoot(false);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        timeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("executionTime"));
        timeColumn.setCellFactory(new NoZeroDoubleCellFactory());
        inputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("input"));
        outputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("output"));
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("size"));
        sizeColumn.setCellFactory(new NoZeroLongCellFactory());
        cardColumn.setCellValueFactory(new TreeItemPropertyValueFactory("cardinality"));
        cardColumn.setCellFactory(new NoZeroLongCellFactory());

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
     * Shows the statistics files if exists.
     */
    public void showStatisticsFile() {
        if (tableResults.getRoot() != null) {
            TreeBenchmarkResultModel benchmarkResult = (TreeBenchmarkResultModel) tableResults.getRoot().getValue();
            if (benchmarkResult != null) {
                String statisticsPath = benchmarkResult.getBenchmarkResult().getStatisticsFileName();
                Eventbus.post(new OpenFileEvent(statisticsPath));
            }
        }
    }

    /**
     * Initializes the model loading the benchmarks into the tree.
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

    /**
     * Initializes the bindings between components view and the model.
     */
    @Override
    protected void initBinding() {
        btnRun.disableProperty().bind(benchmarksTree.getSelectionModel().selectedItemProperty().isNull());
        lbSelectedFiles.textProperty().bind(new StringBinding() {
            {
                super.bind(model.selectedInputFilesListProperty());
            }

            @Override
            protected String computeValue() {
                return BenchMessages.get().getMessage(I18n.SELECTED_FILES_COUNT, model.selectedInputFilesListProperty().size());
            }
        });
        initModelBinding();
    }

    /**
     * Initializes the bindings with the model.
     */
    protected void initModelBinding() {
        model.timeCheckedProperty().bind(chkTime.selectedProperty());
        model.historyCheckedProperty().bind(chkHistory.selectedProperty());
        model.statisticsCheckedProperty().bind(chkStatistics.selectedProperty());
        model.outputTypeProperty().bind(cbOutputType.getSelectionModel().selectedIndexProperty());
        model.selectedInputFilesListProperty().bind(inputsList.itemsProperty());
        model.outputDirProperty().bind(txtOutput.textProperty());
    }

    /**
     * Initializes the listeners.
     */
    @Override
    protected void initListeners() {
        Eventbus.register(this);
        benchmarksTree.getSelectionModel().selectedItemProperty().addListener(new BenchmarkSelectionListener());
        tableResults.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

            if (event.getClickCount() > 1) {
                TreeTableView<TreeResultModel> table = (TreeTableView<TreeResultModel>) event.getSource();
                TreeItem<TreeResultModel> selectedItem = table.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    TreeResultModel value = selectedItem.getValue();

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
            }
        });
    }

    /**
     * Handles the event BenchmarksChangeEvent published by Eventbus, and
     * reloads view and model.
     *
     * @param event Event.
     */
    @Subscribe
    public void handleBenchamrksChange(BenchmarksChangeEvent event) {
        reload();
    }

    /**
     * Updates the view with model values.
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
     * Validates the fields values.
     *
     * @return {@code true} if values are valids, {@code false} en otro caso.
     */
    @Override
    protected boolean validate() {
        boolean valid = super.validate();

        return valid;
    }

    /**
     * Reloads the view and model.
     */
    protected void reload() {
        initModel();
        initModelBinding();
    }

    /**
     * Return the root pane.
     *
     * @return Root pane.
     */
    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    /**
     * Handles the event thrown when Run button is pressed.<br/>
     * Runs the selected benchmark / algorithm.
     *
     * @param event Event.
     */
    @FXML
    public void handleRunAction(ActionEvent event) {
        if (tableResults.getRoot() != null && tableResults.getRoot().getChildren() != null) {
            tableResults.getRoot().getChildren().clear();
        }
        if (validate()) {
            execute();
        }
    }

    /**
     * Executes the algorithms or benchmark selected with AlgorithmExecService.
     */
    protected void execute() {
        try {
            service.setModel(model);
            service.restart();

        } catch (AlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
            publicMessage(ex.getMessage(), Level.ERROR);
        }
    }

    /**
     * Shows the results and statistics.
     *
     * @param r Benchmark result.
     * @param ex Exception.
     */
    protected void finishAlgExecution(BenchmarkResult r, Throwable ex) {
        Animations.fadeOut(busyLayer);
        if (ex == null) {
            model.setLastExecutionResult(r);
            if (State.SUCCEEDED.equals(service.getState())) {
                tableResults.setRoot(getData(r));
                Eventbus.post(new NewResultsEvent());
            }
        }
    }

    /**
     * Handles the {@link SystemSaved} event, copying the path of system into
     * input field.
     *
     * @param event Event.
     */
    @Subscribe
    public void handleSystemSaved(SystemSaved event) {
        if (this.getClass().equals(event.getCalledBy())) {
            String[] paths = event.getPaths();
            if (paths != null) {
                Arrays.stream(paths)
                        .forEach(path -> model.selectedInputFilesListProperty().get().add(Paths.get(path).toString()));
            }
        }
    }

    @FXML
    public void handleStop(ActionEvent event) {
        if (service.isRunning()) {
            service.cancel();
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
     * Shows a dialog box for select the input of algorithm.
     *
     * @param event Event.
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
            loader.<ImplicationsController>getController().calledBy(this.getClass()).setOutput(implicationsPath);
            String title = getI18nLabel("Implications Generator"); // TODO crear label
            Dialogs.showModalDialog(title, generatorForm, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            String message = "Error opening the Implications Generator.";
            logger.error(message, ex);
            publicMessage(message, Level.ERROR);
        }

    }

    /**
     * Shows the dialog box for select the target of algorithm results.
     *
     * @param event Event.
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

    /**
     * Clears the model and the view.
     */
    protected void clear() {
        benchmarksTree.getSelectionModel().clearSelection();
        chkTime.setSelected(true);
        chkHistory.setSelected(false);
        chkStatistics.setSelected(false);
        filterField.clear();
        if (inputsList.getItems() != null) {
            inputsList.getItems().clear();
        }
        txtOutput.clear();
        clearResults();
        model.setLastExecutionResult(null);
    }

    /**
     * Clears the results table.
     */
    protected void clearResults() {
        if (tableResults.getRoot() != null && tableResults.getRoot().getChildren() != null) {
            tableResults.getRoot().getChildren().clear();
        }
    }

    /**
     * Benchmarks tree listener.<br/>
     * When a benchmark is selected, the output field is initialized with
     * benchmark output dir and is disabled.<br/>
     * In this case, the mode checks and console outputs are disabled too.<br/>
     * If the selection is an algorithm, the output field is enabled and
     * initialized with the path of algorithm default output file.<br/>
     * If the selection is more than one algorithm, the output field is cleared
     * and disabled.<br/>
     * If the selection contains benchmarks and algorithms the output field is
     * cleared ant the Run button disabled.
     */
    protected class BenchmarkSelectionListener implements ChangeListener<TreeItem> {

        @Override
        public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldItem, TreeItem newItem) {

            if (newItem != null) {
                clearResults();

                Benchmark selectedBenchmark;
                boolean isBenchmark = (newItem.getValue() instanceof Benchmark);

                if (isBenchmark) {
                    selectedBenchmark = (Benchmark) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(null);
                    txtOutput.setText(Paths.get(selectedBenchmark.getOutputDir()).toString());

                } else {
                    selectedBenchmark = (Benchmark) newItem.getParent().getValue();
                    AlgorithmInfo alg = (AlgorithmInfo) newItem.getValue();
                    model.setSelectedBenchmark(selectedBenchmark);
                    model.setSelectedAlgorithm(alg);
                    txtOutput.setText(Paths.get(WorkspaceManager.get().currentWorkspace().getLocation(),
                            selectedBenchmark.getName().trim(),
                            alg.getShortName(), "output").toString());

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

    /**
     * Loads the input files defined for the selected benchmark.
     *
     * @param benchmark Benchmark's name.
     */
    protected void loadInputFiles(String benchmark) {
        BenchmarksDelegate benchmarksDelegate = new BenchmarksDelegate();
        try {
            Benchmark b = benchmarksDelegate.getBenchmark(benchmark);
            if (b != null) {
                File inputsDir = Paths.get(b.getInputsDir()).toFile();
                List<String> inputs = new ArrayList<>();
                Arrays.stream(inputsDir.listFiles()).forEach(p -> inputs.add(p.toString()));
                inputsList.getItems().setAll(inputs);
            }
        } catch (Exception ex) {
            logger.error("Error loading the input files", ex);
            publicMessage("Error loading the input files: " + ex.getMessage(), Level.ERROR);
        }
    }

    /**
     * Loads the benchmark results into a TreeTableView hierarchy.
     *
     * @param benchResult Benchmark results.
     * @return TreeItem<BenchmarkResultsModel>
     */
    private TreeItem<TreeResultModel> getData(BenchmarkResult benchResult) {
        TreeItem<TreeResultModel> rootItem = new TreeItem();
        if (benchResult != null) {
            Map<AlgorithmInfo, List<AlgorithmResult>> results = benchResult.groupByAlgorithm();

            rootItem = new TreeItem<>(new TreeBenchmarkResultModel(benchResult));
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
        }
        return rootItem;
    }

}
