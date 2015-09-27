
package es.uma.pfc.is.bench.algorithms.results;

import es.uma.pfc.is.bench.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableView;
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
    private TreeTableView tableResults;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            initView();
            initModel();
            modelToView();
        } catch (IOException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    protected void initView() throws IOException {
        super.initView(); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    protected void initModel() {
        super.initModel(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void modelToView() {
        super.modelToView(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }
    
    
}
