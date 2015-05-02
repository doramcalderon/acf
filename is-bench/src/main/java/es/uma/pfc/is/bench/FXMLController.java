package es.uma.pfc.is.bench;

import com.sun.istack.internal.logging.Logger;
import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.output.Console;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
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
    
    @FXML
    private TextArea txtPerformanceArea;
    
    
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
            Algorithm alg = model.getSelectedAlgorithm();
            alg.input(model.getInput())
                .output(new Console(txtPerformanceArea))
                .output(model.getOutput())
                .enable(AlgorithmOptions.Mode.PERFORMANCE);
            new AlgorithmExecutor().execute(alg);
            alg.reset();
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
        fileChooser.setInitialDirectory(UserConfig.get().getDefaultInputDir());
        
        Window mainStage = rootPane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if(selectedFile != null) {
            txtInput.setText(selectedFile.getPath());
        }
    }
     
    @FXML
    public void handleSelectOutputAction(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Output");
        dirChooser.setInitialDirectory(UserConfig.get().getDefaultOutputDir());
        
        Window mainStage = rootPane.getScene().getWindow();
        File selectedDir = dirChooser.showDialog(mainStage);
        if(selectedDir != null) {
            txtOutput.setText(selectedDir.getPath());
        }
    }
}
