/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.algorithms.results;

import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.benchmarks.execution.FileViewerController;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.services.FileReaderService;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
public class ResutlsViewerController extends Controller {
    private AlgorithmResult algorithmResult;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextArea txtInputViewer, txtOutputViewer, txtLog;
    @FXML
    private ProgressIndicator loadingInputIndicator, loadingOutputIndicator, loadingLogIndicator;
    @FXML
    private Label lbTimeMessage;
    
    /**
     * Constructor.
     */
    public ResutlsViewerController() {
    }

    /**
     * Constructor.
     * @param algorithmResult Algorithm result to show.
     */
    public ResutlsViewerController(AlgorithmResult algorithmResult) {
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
        } catch (IOException ex) {
            Logger.getLogger(ResutlsViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    protected void initView() throws IOException {
        File inputFile = getInputFile();
         if(inputFile != null && inputFile.exists()) {
            FileReaderService readerService = new FileReaderService();
            readerService.setFile(inputFile);
            loadingInputIndicator.visibleProperty().bind(readerService.runningProperty());
            txtInputViewer.textProperty().bindBidirectional(readerService.contentFileProperty());
            readerService.restart();
        }
         
        File outputFile = getOutputFile();
         if(outputFile != null && outputFile.exists()) {
            FileReaderService readerService = new FileReaderService();
            readerService.setFile(outputFile);
            loadingOutputIndicator.visibleProperty().bind(readerService.runningProperty());
            txtOutputViewer.textProperty().bindBidirectional(readerService.contentFileProperty());
            readerService.restart();
        }
         
        File logFile = getLogFile();
         if(logFile != null && logFile.exists()) {
            FileReaderService readerService = new FileReaderService();
            readerService.setFile(logFile);
            loadingLogIndicator.visibleProperty().bind(readerService.runningProperty());
            txtLog.textProperty().bindBidirectional(readerService.contentFileProperty());
            readerService.restart();
        }
         lbTimeMessage.setText(getI18nMessage(BenchMessages.ALGORITHM_EXECUTION_TIME, "Algoritmo", algorithmResult.getExecutionTime()));
    }
    
    
    /**
     * Returns the input file.
     * @return File.
     */
    protected File getInputFile() {
        File file = null;
        if(algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getInputFile())) {
            file = new File(algorithmResult.getInputFile());
        }
        return file;
    }
    /**
     * Returns the output file.
     * @return File.
     */
    protected File getOutputFile() {
        File file = null;
        if(algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getOutputFile())) {
            file = new File(algorithmResult.getOutputFile());
        }
        return file;
    }
    /**
     * Returns the output file.
     * @return File.
     */
    protected File getLogFile() {
        File file = null;
        if(algorithmResult != null && !StringUtils.isEmpty(algorithmResult.getLogFile())) {
            file = new File(algorithmResult.getLogFile());
        }
        return file;
    }
 
    /**
     * Shows the result log file.
     * @param event Action event.
     */
    @FXML
    protected void handleLogAction(ActionEvent event) {
        File logFile = new File(algorithmResult.getLogFile());
        String title = algorithmResult.getLogFile();
        
        try {
            FXMLLoader loader = new FXMLLoader(ResutlsViewerController.class.getResource(FXMLViews.FILE_VIEWER_VIEW), getBundle());
            loader.setControllerFactory((Class<?> param) -> new FileViewerController(logFile));
            Parent fileViewer = loader.load();
            Dialogs.showModalDialog(title, fileViewer, getRootPane().getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
