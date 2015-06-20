
package es.uma.pfc.is.bench.algorithms;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.tasks.AlgorithmsSaveService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Algorithms FXML Controller class.
 *
 * @author Dora Calderón
 */
public class AlgorithmsController extends Controller  {
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField txtAlgName;
    @FXML
    private TextField txtAlgShortName;
    @FXML
    private TextField txtAlgClass;
    @FXML
    private Button btnClassesSearch;

    private AlgorithmsModel model;

    /**
     * Initializes the controller class.
     * @param url URL.
     * @param rb Resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        initModel();
        initBindings();
        
    }    
    /**
     * Initialize the model.
     */
    protected void initModel() {
        model = new AlgorithmsModel();
    }
    
    /**
     * Initialize the bindings.
     */
    protected void initBindings() {
        txtAlgName.textProperty().bindBidirectional(model.getNameProperty());
        txtAlgShortName.textProperty().bindBidirectional(model.getShortNameProperty());
        txtAlgClass.textProperty().bindBidirectional(model.getClassNameProperty());
    }
    
    @Override
    public Pane getRootPane() {
        return gridPane;
    }
    
    
    /**
     * Show the Classes search.
     * @param action Action Event.
     */
    @FXML
    public void handleClassesSearch(ActionEvent action) {
        
    }
    
    @FXML
    public void handleCancelAction(ActionEvent action) {
        close();
    }
    
    @FXML
    public void handleSaveAction(ActionEvent action) {
        save();
    }
    
    protected void save() {
        AlgorithmsSaveService saveService = new AlgorithmsSaveService(model);
        saveService.setOnSucceeded(new EventHandler() {

            @Override
            public void handle(Event event) {
                close();
            }
        });
        saveService.restart();
    }
   
}
