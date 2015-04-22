package es.uma.pfc.is.bench;

import com.sun.istack.internal.logging.Logger;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmException;
import es.uma.pfc.is.algorithms.DirectOptimalBasis;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FXMLController implements Initializable {
    
    /**
     * Modelo.
     */
    private BenchModel model;
    
    /**
     * Cuadro de texto que recoge el fichero con los sistemas implicacionales que servirán como entrada.
     */
    @FXML
    private TextField txtInput;
    /**
     * Cuadro de texto que recoge el directorio donde se guardarán las salidas.
     */
    @FXML
    private TextField txtOutput;
    
    /**
     * Lista de algoritmos.
     */
    @FXML
    private ListView<Algorithm> algorithmsList;
    /**
     * Botón Run.
     */
    @FXML
    private Button btnRun;
    
    @FXML
    private BorderPane rootPane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRun.setDisable(Boolean.TRUE);
        initModel();
        modelToView();
        initListeners();
    }  
    /**
     * Crea los listeners necesarios.
     */
    protected void initListeners() {
        algorithmsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Algorithm>() {

            @Override
            public void changed(ObservableValue<? extends Algorithm> observable, Algorithm oldValue, Algorithm newValue) {
                btnRun.setDisable(newValue == null);
            }
        });
    }
    
    protected void initModel() {
        model = new BenchModel();
        model.addAlgorithm(new DirectOptimalBasis());
        
    }
    
    protected void modelToView() {
        algorithmsList.getItems().addAll(model.getAlgorithms());
    }
    /**
     * Actualiza el modelo con los valores de la vista.
     */
    protected void viewToModel() {
        model.setSelectedAlgorithm(algorithmsList.getSelectionModel().getSelectedItem());
        model.setInput(txtInput.getText());
        model.setOutput(txtOutput.getText());
    }
    
    /**
     * Manejador del evento ActionEvent del botón <i>Run</i>.<br/>
     * Ejecuta el algoritmo seleccionado.
     * @param event Evento.
     */
    @FXML
    public void handleRunAction(ActionEvent event) {
        viewToModel();
        try {
            model.getSelectedAlgorithm().execute(model.getInput());
        } catch (AlgorithmException ex) {
            Logger.getLogger(FXMLController.class).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        
    }
    
     
    @FXML
    public void handleSelectInputAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Input");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Prolog File", "*.pl"));
        
        Window mainStage = rootPane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if(selectedFile != null) {
            txtInput.setText(selectedFile.getPath());
        }
    }
}
