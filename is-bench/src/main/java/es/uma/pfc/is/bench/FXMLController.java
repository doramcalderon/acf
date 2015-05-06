package es.uma.pfc.is.bench;


import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmExecutor;
import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.output.Console;
import es.uma.pfc.is.bench.uitls.Chooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**
     * Inicializa el modelo.
     */
    protected void initModel() {
        model = new BenchModel();
        model.addAlgorithm(new DirectOptimalBasis());
        
    }
    
    /**
     * Actualiza la vista con los valores del modelo.
     */
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
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        
    }
    
    /**
     * Abre el cuadro diálogo para seleccionar la entrada del algoritmo.
     * @param event Evento.
     */
    @FXML
    public void handleSelectInputAction(ActionEvent event) {
        Window mainStage = rootPane.getScene().getWindow();
        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN, 
                                                    "Select Input", UserConfig.get().getDefaultInputDir(), 
                                                    new FileChooser.ExtensionFilter("Text File", "*.txt"),
                                                    new FileChooser.ExtensionFilter("Prolog File", "*.pl"));
        if(selectedFile != null) {
            txtInput.setText(selectedFile.getPath());
        }
    }
     
    /**
     * Abre el cuadro de diálogo para seleccionar el destino de los resultados del algoritmo.
     * @param event Evento.
     */
    @FXML
    public void handleSelectOutputAction(ActionEvent event) {
        
        Window mainStage = rootPane.getScene().getWindow();
        
        File selectedDir = Chooser.openDirectoryChooser(mainStage, "Select Output", 
                                                        UserConfig.get().getDefaultOutputDir());
        if(selectedDir != null) {
            txtOutput.setText(selectedDir.getPath());
        }
    }
}
