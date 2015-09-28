package es.uma.pfc.is.bench.benchmarks.execution;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.algorithms.results.ResultsViewerController;
import es.uma.pfc.is.bench.events.ViewFileActionEvent;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Listens the ViewFileActionEvent and shows a fiel into the file viewer.
 *
 * @author Dora Calderón
 */
public class FileViewerListener {

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
                Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
