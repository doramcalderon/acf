package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.DirectOptimalBasis;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class FXMLController implements Initializable {
    
    /**
     * Modelo.
     */
    private BenchModel model;
    
    @FXML
    private Label label;
    
    @FXML
    private ListView<Algorithm> algorithmsList;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initModel();
        modelToView();
    }  
    
    protected void initModel() {
        model = new BenchModel();
        model.addAlgorithm(new DirectOptimalBasis());
        
    }
    
    protected void modelToView() {
        algorithmsList.getItems().addAll(model.getAlgorithms());
    }
    protected void viewToModel() {
        
    }
}
