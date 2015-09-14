
package es.uma.pfc.is.bench.home;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.NavigationEvent;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class HomeController extends Controller implements Initializable {
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }    
    
    @FXML
    public void handleRunAction(ActionEvent event) {
        Eventbus.post(new NavigationEvent(FXMLViews.RUN_BENCHMARK_VIEW));
    }
    
    @FXML
    public void handleNewAction(ActionEvent event) {
        Eventbus.post(new NavigationEvent(FXMLViews.NEW_BENCHMARK_VIEW));
    }
    
}
