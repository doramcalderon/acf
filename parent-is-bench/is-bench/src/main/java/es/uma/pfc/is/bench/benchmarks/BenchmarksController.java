
package es.uma.pfc.is.bench.benchmarks;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.NavigationEvent;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class BenchmarksController extends Controller {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(BenchmarksController.class);
    @FXML
    private TabPane benchmarksTabPane;
    @FXML
    private Tab tabNew;

    @FXML
    private Tab tabRun;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initModel();
            initBinding();
            initListeners();
            initValidation();
        } catch (IOException ex) {
            logger.error("Error initializing the BenchmarksController.", ex);
        }
        
    }    

    @Override
    protected void initView() throws IOException {
        showTab(new NavigationEvent(FXMLViews.RUN_BENCHMARK_VIEW));
    }
    

    @Override
    protected void initListeners() {
        Eventbus.register(this);
    }
    
    @Subscribe
    public void showTab(NavigationEvent event) {
        String viewTo = event.getView();
        if(FXMLViews.NEW_BENCHMARK_VIEW.equals(viewTo)) {
            benchmarksTabPane.getSelectionModel().select(tabNew);
        } else if (FXMLViews.RUN_BENCHMARK_VIEW.equals(viewTo)) {
            benchmarksTabPane.getSelectionModel().select(tabRun);
        }
    }
    
    
    
}
