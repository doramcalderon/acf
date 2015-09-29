package es.uma.pfc.is.javafx;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.bench.MainLayoutController;
import es.uma.pfc.is.bench.algorithms.results.ResultsViewerController;
import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import es.uma.pfc.is.bench.benchmarks.execution.FileViewerController;
import es.uma.pfc.is.bench.events.AlgorithmResultSelection;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.eventbus.Eventbus;
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
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * File TreeTableCell factory.<br/>
 * Shows the file if double click occurred.
 *
 * @author Dora Calderón
 */
public class AlgorithmResultCellFactory implements Callback<TreeTableColumn, TreeTableCell> {

    /**
     * Resource Bundle.
     */
    private ResourceBundle rb;
    /**
     * Root pane.
     */
    private Pane rootPane;

    public AlgorithmResultCellFactory(ResourceBundle bundle, Pane rootPane) {
        this.rb = bundle;
        this.rootPane = rootPane;
    }

    @Override
    public TreeTableCell call(TreeTableColumn p) {
        TreeTableCell cell = new TreeTableCell<TreeResultModel, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
            }

            private String getString() {
                return getItem() == null ? "" : Paths.get(getItem()).getFileName().toString();
            }
        };

        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            TreeTableCell<TreeResultModel, String> c
                    = (TreeTableCell<TreeResultModel, String>) event.getSource();
            if (event.getClickCount() > 1) {

                show(c.getTreeTableRow());
            } else {
                TreeResultModel item = c.getTreeTableRow().getItem();
                TreeResultModel parentItem = c.getTreeTableRow().getTreeItem().getParent().getValue();
                String algorithmName = parentItem.nameProperty().get();

                AlgorithmInfo algInfo = new AlgorithmInfo();
                algInfo.setName(algorithmName);
                
                AlgorithmResult r = new AlgorithmResult(item.inputProperty().get(),
                        item.outputProperty().get(),
                        algInfo);
                r.setExecutionTime(item.executionTimeProperty().get());
                Eventbus.post(new AlgorithmResultSelection(r));
            }
        });
        return cell;
    }

    /**
     * Shows a file content into a text area in a modal window.
     *
     * @param row Result row to show.
     */
    protected void show(TreeTableRow<TreeResultModel> row) {
        TreeResultModel parentItem = row.getTreeItem().getParent().getValue();
        String algorithmName = parentItem.nameProperty().get();

        AlgorithmInfo algInfo = new AlgorithmInfo();
        algInfo.setName(algorithmName);

        TreeResultModel item = row.getItem();
        AlgorithmResult r = new AlgorithmResult(item.inputProperty().get(),
                item.outputProperty().get(),
                algInfo);
        r.setExecutionTime(item.executionTimeProperty().get());
        r.setLogFile(item.logProperty().get());

        try {
            FXMLLoader loader = new FXMLLoader(ResultsViewerController.class.getResource(FXMLViews.RESULTS_VIEWER_VIEW), rb);
            loader.setControllerFactory((Class<?> param) -> new ResultsViewerController(r));
            Parent fileViewer = loader.load();
            Dialogs.showModalDialog(BenchMessages.get().getMessage(BenchMessages.RESULTS_VIEWER_TITLE, algorithmName),
                    fileViewer, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows a file content into a text area in a modal window.
     *
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
