package es.uma.pfc.is.bench.algorithms.results;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeAlgorithmResultModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeBenchmarkResultModel;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import es.uma.pfc.is.bench.business.ResultsBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import es.uma.pfc.is.bench.events.AlgorithmResultSelection;
import es.uma.pfc.is.bench.events.NewResultsEvent;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.javafx.NoZeroDoubleCellFactory;
import es.uma.pfc.is.javafx.NoZeroLongCellFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Results view controller.
 *
 * @author Dora Calderón
 */
public class ResultsController extends Controller {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TreeTableView<TreeResultModel> tableResults;
    @FXML
    private TableView tableStatistics;
    @FXML
    private TreeTableColumn nameColumn, timeColumn, sizeColumn, cardColumn, inputColumn, outputColumn;

    /**
     * Results view model.
     */
    private ResultsModel model;

    /**
     * Initializes the view.
     *
     * @param url URL of the view.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            initView();
            initModel();
            initListeners();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the results table.
     *
     * @throws IOException
     */
    @Override
    protected void initView() throws IOException {
        tableResults.setShowRoot(false);
        tableResults.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TreeItem<TreeResultModel>> observable,
                                TreeItem<TreeResultModel> oldValue, TreeItem<TreeResultModel> newValue) -> {
                    AlgorithmResult result = null;
                    TreeResultModel value = (newValue != null) ? newValue.getValue() : null;
                    if (value != null && (value instanceof TreeAlgorithmResultModel)) {
                        result = ((TreeAlgorithmResultModel) value).getAlgorithmresult();
                    }
                    Eventbus.post(new AlgorithmResultSelection(result));
                });
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        timeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("executionTime"));
        timeColumn.setCellFactory(new NoZeroDoubleCellFactory());
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("size"));
        sizeColumn.setCellFactory(new NoZeroLongCellFactory());
        cardColumn.setCellValueFactory(new TreeItemPropertyValueFactory("cardinality"));
        cardColumn.setCellFactory(new NoZeroLongCellFactory());
        inputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("input"));
        outputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("output"));

    }

    /**
     * Initializes the model.
     */
    @Override
    protected void initModel() {
        model = new ResultsModel();
        ResultsBean resultsBean = new ResultsBean();
        List<BenchmarkResultSet> allResults = resultsBean.getAllResults(WorkspaceManager.get().currentWorkspace().getLocation());
        model.setAllResults(allResults);
    }

    /**
     * Initializes the listners.
     */
    @Override
    protected void initListeners() {
        Eventbus.register(this);
        tableResults.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TreeItem<TreeResultModel>> observable,
                                TreeItem<TreeResultModel> oldValue, TreeItem<TreeResultModel> newValue) -> {
                    AlgorithmResult result = null;
                    TreeResultModel value = (newValue != null) ? newValue.getValue() : null;
                    if (value != null) {
                        if (value instanceof TreeAlgorithmResultModel) {
                            result = ((TreeAlgorithmResultModel) value).getAlgorithmresult();
                        }
                    }
                    Eventbus.post(new AlgorithmResultSelection(result));
                });

    }


    /**
     * Loads the model data into the results table.
     */
    @Override
    protected void modelToView() {
        List<BenchmarkResultSet> allResults = model.getAllResults();

        if (allResults != null) {
            TreeItem<TreeResultModel> rootItem = new TreeItem<>(new TreeResultModel());
            List<TreeItem<TreeResultModel>> benchmarkItems = new ArrayList<>();

            for (BenchmarkResultSet resultset : allResults) {
                TreeItem<TreeResultModel> benchItem = new TreeItem<>(new TreeResultModel(resultset.getName()));

                for (BenchmarkResult benchResult : resultset.getResults()) {
                    TreeItem<TreeResultModel> executionNode = new TreeItem<>(new TreeBenchmarkResultModel(benchResult));

                    Map<AlgorithmInfo, List<AlgorithmResult>> results = benchResult.groupByAlgorithm();
                    List<TreeItem<TreeResultModel>> algorithmItems = new ArrayList<>();
                    List<TreeItem<TreeResultModel>> resultItems = new ArrayList<>();

                    for (AlgorithmInfo algorithm : results.keySet()) {
                        resultItems.clear();
                        List<AlgorithmResult> algResults = results.getOrDefault(algorithm, new ArrayList<>());

                        TreeResultModel node = new TreeAlgorithmModel(algorithm);
                        TreeItem<TreeResultModel> algorithmItem = new TreeItem<>(node);

                        algResults.stream().map((r) -> new TreeAlgorithmResultModel(r))
                                .forEach((resultNode) -> {
                                    resultItems.add(new TreeItem<>(resultNode));
                                });
                        algorithmItem.setExpanded(true);
                        algorithmItem.getChildren().addAll(resultItems);
                        algorithmItems.add(algorithmItem);

                    }
                    executionNode.getChildren().addAll(algorithmItems);
                    benchItem.getChildren().add(executionNode);

                }
                benchmarkItems.add(benchItem);
            }
            if (benchmarkItems.size() > 0) {
                benchmarkItems.get(0).setExpanded(true);
            }
            rootItem.getChildren().addAll(benchmarkItems);
            rootItem.setExpanded(true);
            tableResults.setRoot(rootItem);
        }
    }

    /**
     * Root pane.
     *
     * @return Pane.
     */
    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    /**
     * Reloads the view and the model.
     */
    protected void reload() {
        clear();
        initModel();
        modelToView();
    }

    /**
     * Clear the tables.
     */
    protected void clear() {
        Eventbus.post(new AlgorithmResultSelection(null));
    }

    /**
     * Shows the result statistics into a table.
     *
     * @param statisticsFile Statistics file path.
     */
    protected void showStatistics(String statisticsFile) {
        tableStatistics.getColumns().clear();
        tableStatistics.getItems().clear();

        if (!StringUtils.isEmpty(statisticsFile)) {
            StatisticsReaderService statisticsReader
                    = new StatisticsReaderService(statisticsFile, tableStatistics);
            statisticsReader.restart();
        }
    }

    /**
     * Handles the NewResultsEvent published by Eventbus and reload the view and the model.
     *
     * @param event Event.
     */
    @Subscribe
    public void handleNewResult(NewResultsEvent event) {
        if (event != null) {
            reload();
        }
    }

}
