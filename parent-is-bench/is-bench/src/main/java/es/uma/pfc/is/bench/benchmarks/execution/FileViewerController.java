
package es.uma.pfc.is.bench.benchmarks.execution;

import es.uma.pfc.is.bench.Controller;
import es.uma.pfc.is.bench.services.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FilewViewer.fxml controller class.
 *
 * @author Dora Calderón
 */
public class FileViewerController extends Controller {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(FileViewerController.class);

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
            logger.error("Error inititalizing the FileViewer view.", ex);
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
