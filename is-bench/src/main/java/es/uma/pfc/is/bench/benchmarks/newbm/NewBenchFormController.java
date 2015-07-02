
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * NewBenchFormController class.
 *
 * @author Dora Calderón
 */
public class NewBenchFormController extends Controller {
    
    @FXML
    private TextField txtName;
    @FXML
    private ListView<Algorithm> algorithmsList;
    @FXML
    /**
     * Model.
     */
    private NewBenchFormModel model;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initModel();
        super.initialize(url, rb);
        initBinding();
        
    }    

    @Override
    protected void initModel() {
        model = new NewBenchFormModel();
        // TODO cargar lista de algoritmos
    }

    
    @Override
    protected void initBinding() {
        txtName.textProperty().bind(model.nameProperty());
        algorithmsList.itemsProperty().bindBidirectional(model.algorithmsListProperty());
    }
    
    
    
}
