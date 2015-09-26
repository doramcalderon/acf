/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.services.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

/**
 * FilewViewer.fxml controller class.
 *
 * @author Dora Calderón
 */
public class FileViewerController extends Controller {
    @FXML
    private TextArea txtFileViewer;
    
    @FXML
    private ProgressIndicator loadingProgressIndicator;
    /**
     * File to show.
     */
    private File file;

    public FileViewerController(File file) {
        this.file = file;
    }

   
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        try {
            initView();
        } catch (IOException ex) {
            Logger.getLogger(FileViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    protected void initView() throws IOException {
        if(file != null && file.exists()) {
            FileReaderService readerService = new FileReaderService();
            readerService.setFile(file);
            loadingProgressIndicator.visibleProperty().bind(readerService.runningProperty());
            txtFileViewer.textProperty().bindBidirectional(readerService.contentFileProperty());
            readerService.restart();
        }
    }
    
    /**
     * Sets the file to show.
     * @param file File.
     */
    public void setFile(File file) {
        this.file = file;
        
    }
    
    
}
