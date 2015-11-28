package es.uma.pfc.is.bench.home;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.domain.BenchmarkResult;
import es.uma.pfc.is.bench.domain.BenchmarkResultSet;
import es.uma.pfc.is.bench.events.MessageEvent.Level;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.NavigationEvent;
import es.uma.pfc.is.bench.events.NewResultsEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.services.ResultsLoadService;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class HomeController extends Controller implements Initializable {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox container;

    /**
     * Model.
     */
    private HomeModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initModel();

        } catch (IOException ex) {
            String message = getI18nMessage(BenchMessages.INITIALIZING_CONTROLLER_ERROR,
                    HomeController.class.getSimpleName());
            logger.error(message, ex);
            publicMessage(message, Level.INFO);
        }
    }

    @Override
    protected void initView() throws IOException {
        container.getChildren().clear();  
    }

    @Override
    protected void initModel() {
        model = new HomeModel();
        ResultsLoadService resultsService = new ResultsLoadService();
        resultsService.setOnSucceeded((WorkerStateEvent event) -> {
            List<BenchmarkResultSet> results = (List<BenchmarkResultSet>) event.getSource().getValue();
            model.setResults(results);
            modelToView();
        });
        resultsService.restart();
    }

    @Override
    protected void modelToView() {
        DateFormat df = new SimpleDateFormat();
        container.getChildren().clear();

        if (model.getResults() != null && !model.getResults().isEmpty()) {
            int numResults = model.getResults().size() % 6;
            Map<String, List<BenchmarkResultSet>> groupedByName = model.groupByBenchmark();
            
            Iterator<String> rsIterator = groupedByName.keySet().iterator();
            int i = 0;
            while (rsIterator.hasNext() && i <= numResults) {
                String benchName = rsIterator.next();
                BenchmarkResultSet rs = groupedByName.get(benchName).get(0);
                BenchmarkResult br = rs.getResults().get(0);

                Label lbDate = new Label();
                String date = df.format(br.getDate());
                lbDate.setText(getI18nMessage(BenchMessages.LAST_RUN, date));
               
                TextFlow flow = new TextFlow();
                Map<AlgorithmInfo, List<AlgorithmResult>> ars = br.groupByAlgorithm();
                if(ars != null) {
                    StringBuilder sb = new StringBuilder();
                    int inputs = 0;
                    
                    for (AlgorithmInfo ar : ars.keySet()) {
                        if(ar != null 
                                && ar != null
                                && !StringUtils.isEmpty(ar.getName())) {
                            sb.append("- ").append(ar.getName()).append("\n");
                            inputs = ars.get(ar).size();
                        }
                    }
                    sb.append("\n" + inputs + " inputs");
                    Text tx = new Text(sb.toString());
                    flow.getChildren().add(tx);
                }
                
                addTitledPane(br.getBenchmarkName(), i++, lbDate, flow);
               
            }
            Hyperlink newLink = new Hyperlink(getI18nLabel(I18n.NEW_LINK));
            newLink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Eventbus.post(new NavigationEvent(FXMLViews.NEW_BENCHMARK_VIEW));
                }
            });
            
            addTitledPane(getI18nLabel(I18n.NEW_BENCHMARK), i, newLink);
        }
       
    }
    /**
     * Reloads the view and the model.
     */
    protected void reload() {
        try {
            initView();
            initModel();
            modelToView();
        } catch (IOException ex) {
            String message = getI18nMessage(BenchMessages.INITIALIZING_CONTROLLER_ERROR, HomeController.class);
            logger.error(message, ex);
            publicMessage(message, Level.ERROR);
        }
    }
    /**
     * Adds a titled pane to main container.
     * @param title Title.
     * @param row Row num.
     * @param nodes Nodes to add.
     */
    protected void addTitledPane(String title, int row, Node... nodes) {
        VBox box = new VBox(nodes);
        box.setStyle("-fx-background-color:white");
        
        TitledPane titledPane = new TitledPane(title, box);
        titledPane.setFont(new Font(18));
        titledPane.setStyle("-fx-background-color:white");
        container.getChildren().add(row, titledPane);
        
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


    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

}
