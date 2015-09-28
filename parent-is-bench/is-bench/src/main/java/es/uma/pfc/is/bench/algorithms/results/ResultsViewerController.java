package es.uma.pfc.is.bench.algorithms.results;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.benchmarks.execution.FileViewerController;
import es.uma.pfc.is.bench.events.AlgorithmResultSelection;
import es.uma.pfc.is.bench.events.ViewFileActionEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class ResultsViewerController extends Controller {

    private AlgorithmResult algorithmResult;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextArea txtInputViewer, txtOutputViewer;
    @FXML
    private ProgressIndicator loadingInputIndicator, loadingOutputIndicator;
    @FXML
    private Label lbTimeMessage;
    @FXML
    private Button btnLog;

    /**
     * Constructor.
     */
    public ResultsViewerController() {
    }

    /**
     * Constructor.
     *
     * @param algorithmResult Algorithm result to show.
     */
    public ResultsViewerController(AlgorithmResult algorithmResult) {
        this.algorithmResult = algorithmResult;
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
            initBinding();
            initListeners();
        } catch (IOException ex) {
            Logger.getLogger(ResultsViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void initView() throws IOException {
        if (algorithmResult != null) {
            File inputFile = getInputFile();
            if (inputFile != null && inputFile.exists()) {
                FileReaderService readerService = new FileReaderService();
                readerService.setFile(inputFile);
                loadingInputIndicator.visibleProperty().bind(readerService.runningProperty());
                txtInputViewer.textProperty().bindBidirectional(readerService.contentFileProperty());
                readerService.restart();
            }

            File outputFile = getOutputFile();
            if (outputFile != null && outputFile.exists()) {
                FileReaderService readerService = new FileReaderService();
                readerService.setFile(outputFile);
                loadingOutputIndicator.visibleProperty().bind(readerService.runningProperty());
                txtOutputViewer.textProperty().bindBidirectional(readerService.contentFileProperty());
                readerService.restart();
            }

           
            String algorithmName = (algorithmResult.getAlgorithmInfo() != null)
                    ? algorithmResult.getAlgorithmInfo().getName() : "Algorithm"; //TODO crear label
            lbTimeMessage.setText(getI18nMessage(BenchMessages.ALGORITHM_EXECUTION_TIME,
                    algorithmName,
                    algorithmResult.getExecutionTime()));
        }
    }

    @Override
    protected void initBinding() {
        btnLog.disableProperty().bind(new BooleanBinding() {
            {super.bind(txtInputViewer.textProperty());}

            ;
            @Override
            protected boolean computeValue() {
                return (algorithmResult == null);
            }
        });
    }

    @Override
    protected void initListeners() {
        Eventbus.register(this);
    }

    @Subscribe
    public void showResultDetail(AlgorithmResultSelection result) {
        this.algorithmResult = result.getResult();
        if (algorithmResult == null) {
            clear();
        } else {
            reload();
        }
    }

    /**
     * Reloads the view.
     */
    protected void reload() {
        try {
            initView();
            initBinding();
            initListeners();
        } catch (IOException ex) {
            Logger.getLogger(ResultsViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Clear the textareas.
     */
    protected void clear() {
        txtInputViewer.clear();
        txtOutputViewer.clear();
        lbTimeMessage.setText("");
    }

    /**
     * Returns the input file.
     *
     * @return File.
     */
    protected File getInputFile() {
        File file = null;
        if (algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getInputFile())) {
            file = new File(algorithmResult.getInputFile());
        }
        return file;
    }

    /**
     * Returns the output file.
     *
     * @return File.
     */
    protected File getOutputFile() {
        File file = null;
        if (algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getOutputFile())) {
            file = new File(algorithmResult.getOutputFile());
        }
        return file;
    }

    /**
     * Returns the output file.
     *
     * @return File.
     */
    protected File getLogFile() {
        File file = null;
        if (algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getLogFile())) {
            file = new File(algorithmResult.getLogFile());
        }
        return file;
    }

    /**
     * Shows the result log file.
     *
     * @param event Action event.
     */
    @FXML
    protected void handleLogAction(ActionEvent event) {
        if(!StringUtils.isEmpty(algorithmResult.getLogFile())) {
            Eventbus.post(new ViewFileActionEvent(new File(algorithmResult.getLogFile()), 
                                                    algorithmResult.getLogFile(), getRootPane().getScene().getWindow(), 
                                                    getBundle()));
        }
    }
}
