package es.uma.pfc.is.bench.benchmarks.execution;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.algorithms.results.ResultsViewerController;
import es.uma.pfc.is.bench.events.ViewFileActionEvent;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens the ViewFileActionEvent and shows a fiel into the file viewer.
 *
 * @author Dora Calderón
 */
public class FileViewerListener {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(FileViewerListener.class);


    /**
     * Constructor.
     */
    public FileViewerListener() {
    }

    /**
     * Opens a viewer showing a file content.
     * @param event Event.
     */
    @Subscribe
    public void handleViewFileAction(ViewFileActionEvent event) {
        if (event != null) {
            try {
                FXMLLoader loader = new FXMLLoader(ResultsViewerController.class.getResource(FXMLViews.FILE_VIEWER_VIEW), event.getI18nResources());
                loader.setControllerFactory((Class<?> param) -> new FileViewerController(event.getFile()));
                Parent fileViewer = loader.load();
                Dialogs.showModalDialog(event.getTitle(), fileViewer, event.getParent().getScene().getWindow());
            } catch (IOException ex) {
                logger.error("Error opening the file viewer window.", ex);
            }

        }
    }

}
