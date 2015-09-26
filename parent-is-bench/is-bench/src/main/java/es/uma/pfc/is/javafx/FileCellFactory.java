package es.uma.pfc.is.javafx;

import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.benchmarks.execution.BenchmarkResultsModel;
import es.uma.pfc.is.bench.benchmarks.execution.FileViewerController;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * File TreeTableCell factory.<br/>
 * Shows the file if double click occurred.
 *
 * @author Dora Calderón
 */
public class FileCellFactory implements Callback<TreeTableColumn, TreeTableCell> {
    /**
     * Resource Bundle.
     */
    private ResourceBundle rb;
    /**
     * Root pane.
     */
    private Pane rootPane;
    
    public FileCellFactory(ResourceBundle bundle, Pane rootPane) {
        this.rb = bundle;
        this.rootPane = rootPane;
    }
    
    

    @Override
    public TreeTableCell call(TreeTableColumn p) {
        TreeTableCell cell = new TreeTableCell<BenchmarkResultsModel, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
            }

            private String getString() {
                return getItem() == null ? "" : getItem();
            }
        };

        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                TreeTableCell c = (TreeTableCell) event.getSource();
                showHistory(c.getText(), Paths.get(c.getText()).toFile());
            }
        });
        return cell;
    }
    
    /**
     * Shows a file content into a text area in a modal window.
     * @param title Title.
     * @param file File to show.
     */
    protected void showHistory(String title, File file) {

        try {
            FXMLLoader loader = new FXMLLoader(FileViewerController.class.getResource(FXMLViews.FILE_VIEWER_VIEW), rb);
            loader.setControllerFactory((Class<?> param) -> new FileViewerController(file));
            Parent fileViewer = loader.load();
            Dialogs.showModalDialog(title, fileViewer, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}