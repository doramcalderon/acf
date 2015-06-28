
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.ISBenchApp;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class NewBenchmarkController extends Controller {
    @FXML
    private AnchorPane rootListPane;
    @FXML
    private AnchorPane rootFormPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
        } catch (IOException ex) {
            Logger.getLogger(NewBenchmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    protected void initView() throws IOException {
        Pane listPane = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.ALGORITHMS_LIST_VIEW), this.getBundle());
        rootListPane.getChildren().add(listPane);

        
        Pane newFormPane = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.NEW_ALGORITHM_FORM), this.getBundle());
        rootFormPane.getChildren().add(newFormPane);
    }
    
    
    
}
