package es.uma.pfc.is.bench.algorithms.results;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import es.uma.pfc.is.bench.business.ResultsBean;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import es.uma.pfc.is.bench.events.AlgorithmResultSelection;
import es.uma.pfc.is.bench.events.NewResultsEvent;
import es.uma.pfc.is.bench.services.StatisticsReaderService;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.javafx.AlgorithmResultCellFactory;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class ResultsController extends Controller {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TitledPane statsTiltedPane;
    @FXML
    private TreeTableView<TreeResultModel> tableResults;
    @FXML
    private TableView tableStatistics;
    @FXML
    private TreeTableColumn nameColumn, timeColumn, inputColumn, outputColumn;

    private ResultsModel model;

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

    @Override
    protected void initView() throws IOException {
        statsTiltedPane.setExpanded(false);
        tableResults.setShowRoot(false);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        timeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("executionTime"));
        timeColumn.setCellFactory(new NoZeroLongCellFactory());
        inputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("input"));
        inputColumn.setCellFactory(new AlgorithmResultCellFactory(getBundle(), getRootPane()));
        outputColumn.setCellValueFactory(new TreeItemPropertyValueFactory("output"));
        outputColumn.setCellFactory(new AlgorithmResultCellFactory(getBundle(), getRootPane()));

    }

    @Override
    protected void initModel() {
        model = new ResultsModel();
        ResultsBean resultsBean = new ResultsBean();
        List<BenchmarkResultSet> allResults = resultsBean.getAllResults(WorkspaceManager.get().currentWorkspace().getLocation());
        model.setAllResults(allResults);
    }

    @Override
    protected void initListeners() {
        Eventbus.register(this);
        tableResults.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TreeItem<TreeResultModel>> observable,
                                TreeItem<TreeResultModel> oldValue, TreeItem<TreeResultModel> newValue) -> {
                    if ((newValue == null) || !newValue.getValue().isAlgorithmResult()) {
                        Eventbus.post(new AlgorithmResultSelection(null));
                    }
                    if(newValue != null){
                        String statsFile = newValue.getValue().statsFileProperty().get();
                        if (!StringUtils.isEmpty(statsFile)) {
                            showStatistics(statsFile);
                        }
                    }
                });

    }

    @Override
    protected void modelToView() {
        List<BenchmarkResultSet> allResults = model.getAllResults();

        if (allResults != null) {
            TreeItem<TreeResultModel> rootItem = new TreeItem<>(new TreeResultModel());
            List<TreeItem<TreeResultModel>> benchmarkItems = new ArrayList<>();

            for (BenchmarkResultSet resultset : allResults) {
                TreeItem<TreeResultModel> benchItem = new TreeItem<>(new TreeResultModel(resultset.getName()));

                for (BenchmarkResult benchResult : resultset.getResults()) {
                    TreeItem<TreeResultModel> executionNode = new TreeItem<>(model.getBenchmarkResultNode(benchResult));

                    Map<AlgorithmInfo, List<AlgorithmResult>> results = benchResult.groupByAlgorithm();
                    List<TreeItem<TreeResultModel>> algorithmItems = new ArrayList<>();
                    List<TreeItem<TreeResultModel>> resultItems = new ArrayList<>();

                    for (AlgorithmInfo algorithm : results.keySet()) {
                        resultItems.clear();
                        List<AlgorithmResult> algResults = results.getOrDefault(algorithm, new ArrayList<>());

                        TreeResultModel node = new TreeResultModel(algorithm.getName());
                        TreeItem<TreeResultModel> algorithmItem = new TreeItem<>(node);

                        algResults.stream().map((r) -> model.getAlgorithmResultNode(r)).forEach((resultNode) -> {
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
            rootItem.getChildren().addAll(benchmarkItems);

            tableResults.setRoot(rootItem);
        }
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    protected void reload() {
        clear();
        initModel();
        modelToView();
    }
    
     protected void clear() {
        Eventbus.post(new AlgorithmResultSelection(null));
        tableStatistics.getColumns().clear();
        tableStatistics.getItems().clear();
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
    
    @Subscribe
    public void handleNewResult(NewResultsEvent event) {
        if(event != null) {
            reload();
        }
    }
   
}
