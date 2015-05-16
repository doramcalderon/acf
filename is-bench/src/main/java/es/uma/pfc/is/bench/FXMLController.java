package es.uma.pfc.is.bench;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.output.Console;
import es.uma.pfc.is.bench.output.ConsolePrintStream;
import es.uma.pfc.is.bench.tasks.AlgorithmExecService;
import es.uma.pfc.is.bench.uitls.Chooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FXMLController extends Controller {

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
    @FXML
    private TextArea txtHistoryArea;
    @FXML
    private TextArea txtStatisticsArea;

    @FXML
    private CheckBox chkTime, chkHistory, chkStatistics;

    @FXML
    private ProgressIndicator piExecution;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
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
        if (chkTime.isSelected()) {
            addModeToModel(Mode.PERFORMANCE, txtPerformanceArea);
        }
        if (chkHistory.isSelected()) {
            addModeToModel(Mode.TRACE, txtHistoryArea);
        }
        if (chkStatistics.isSelected()) {
            addModeToModel(Mode.STATISTICS, txtStatisticsArea);
        }

        model.setInput(txtInput.getText());
        model.setOutput(txtOutput.getText());
    }

    /**
     * Añade al modelo la activación de un modo de ejecución.
     *
     * @param mode Modo.
     * @param textArea TextArea donde se mostrará la traza.
     */
    protected void addModeToModel(Mode mode, TextArea textArea) {
        model.getSelectedAlgorithm().enable(mode);
        model.addTraceOutput(mode, new ConsolePrintStream(new Console(textArea)));
    }

    /**
     * Manejador del evento ActionEvent del botón <i>Run</i>.<br/>
     * Ejecuta el algoritmo seleccionado.
     *
     * @param event Evento.
     */
    @FXML
    public void handleRunAction(ActionEvent event) {
        clearTraces();
        viewToModel();
        try {
            final Algorithm alg = model.getSelectedAlgorithm();
            alg.input(model.getInput())
                    .traceOutputs(model.getTraceOutputs())
                    .output(model.getOutput());

            AlgorithmExecService service = new AlgorithmExecService(alg);
            service.setOnFinished(new EventHandler<WorkerStateEvent>() {

                public void handle(WorkerStateEvent event) {
                    alg.reset();
                }
            });
            piExecution.visibleProperty().bind(service.runningProperty());
            service.restart();

        } catch (AlgorithmException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * ActionEvent handler of Run button.<br/>
     * Clear all traces.
     * @param event Event.
     */
    @FXML
    public void handleClearAll(ActionEvent event) {
        clear();
    }

    /**
     * Abre el cuadro diálogo para seleccionar la entrada del algoritmo.
     *
     * @param event Evento.
     */
    @FXML
    public void handleSelectInputAction(ActionEvent event) {
        Window mainStage = rootPane.getScene().getWindow();
        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN,
                getI18nString(I18n.SELECT_INPUT_DIALOG_TITLE), UserConfig.get().getDefaultInputDir(),
                new FileChooser.ExtensionFilter(getI18nString(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nString(I18n.PROLOG_FILE), "*.pl"));
        if (selectedFile != null) {
            txtInput.setText(selectedFile.getPath());
        }
    }

    /**
     * Abre el cuadro de diálogo para seleccionar el destino de los resultados del algoritmo.
     *
     * @param event Evento.
     */
    @FXML
    public void handleSelectOutputAction(ActionEvent event) {

        Window mainStage = rootPane.getScene().getWindow();

        File selectedFile = Chooser.openFileChooser(mainStage, Chooser.FileChooserMode.OPEN,
                getI18nString(I18n.SELECT_OUTPUT_DIALOG_TITLE), UserConfig.get().getDefaultOutputDir(),
                new FileChooser.ExtensionFilter(getI18nString(I18n.TEXT_FILE), "*.txt"),
                new FileChooser.ExtensionFilter(getI18nString(I18n.PROLOG_FILE), "*.pl"));
        if (selectedFile != null) {
            txtOutput.setText(selectedFile.getPath());
        }
    }

    @FXML
    public void clearTime(ActionEvent event) {
        txtPerformanceArea.clear();
    }

    @FXML
    public void clearHistory(ActionEvent event) {
        txtHistoryArea.clear();
    }

    @FXML
    public void clearStatistics(ActionEvent event) {
        txtStatisticsArea.clear();
    }

    /**
     * Clear the textareas content.
     */
    protected void clearTraces() {
        txtPerformanceArea.clear();
        txtHistoryArea.clear();
        txtStatisticsArea.clear();
    }
    /**
     * Clear the model and the view.
     */
    protected void clear() {
        model.clear();
        txtInput.clear();
        txtOutput.clear();
        clearTraces();
        this.algorithmsList.getSelectionModel().clearSelection();
    }
}
