package es.uma.pfc.is.bench;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.events.NavigationEvent;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Animations;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import static es.uma.pfc.is.bench.view.FXMLViews.ABOUT_VIEW;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Root Vieww.
 *
 * @author Dora Calderón
 */
public class MainLayoutController extends Controller {

    private static final int HOME_PANE_INDEX = 0;
    private static final int BENCHMARKS_PANE_INDEX = 1;
    private static final int RESULTS_PANE_INDEX = 2;
    private static final int GENERATOR_PANE_INDEX = 3;

    /**
     * Map whit buttons and panes relations. The key is the button id and the value, the index of pane in the stack
     * pane.
     */
    private Map<String, Integer> panes = new HashMap();

    @FXML
    private Menu preferencesMenu;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label lbStateBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.initialize(location, resources);
            initView();
            initListeners();
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    @Override
    protected void initListeners() {
        Eventbus.register(this);
    }
    
    @Subscribe
    public void newBenchmark(NavigationEvent event) {
        String viewTo = event.getView();
        switch(viewTo) {
            case FXMLViews.NEW_BENCHMARK_VIEW:
//                btnBenchmarks.setSelected(true);
                break;
            case FXMLViews.RUN_BENCHMARK_VIEW:
//                btnBenchmarks.setSelected(true);
                break;
            default:
                break;
        }
        
    }

    /**
     * Show the message of MessageEvent in the state bar.
     *
     * @param event Event.
     */
    @Subscribe
    public void showMessage(MessageEvent event) {
        Platform.runLater(() -> {
            lbStateBar.setVisible(true);
            lbStateBar.setOpacity(1.0);
            lbStateBar.setText(event.getMessage());
            switch (event.getLevel()) {
                case INFO:
                    lbStateBar.setTextFill(Paint.valueOf("BLUE"));
                    break;
                case SUCCEEDED:
                    lbStateBar.setTextFill(Paint.valueOf("GREEN"));
                    break;
                case WARNING:
                    lbStateBar.setTextFill(Paint.valueOf("YELLOW"));
                    break;
                case ERROR:
                    lbStateBar.setTextFill(Paint.valueOf("RED"));
                    break;
                    
            }
            Animations.fadeOut(lbStateBar, 5000);
        });
    }

    /**
     * Selects the pane with the index in the stack pane.
     *
     * @param index
     */
    protected void selectPane(int index) {
//        for (Node node : centerContainer.getChildren()) {
//            node.setVisible((index == centerContainer.getChildren().indexOf(node)));
//        }
    }

    /**
     * Abre el cuadro de diálogo para el registro de algoritmos.
     *
     * @param event Action Event.
     */
    @FXML
    public void handleMenuAlgorithms(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handler of Preferences Menu action event.
     *
     * @param event Event.
     */
    @FXML
    public void handleWorkspacesAction(ActionEvent event) {
        try {
            TabPane configView = FXMLLoader.load(MainLayoutController.class.getResource(FXMLViews.USER_CONFIG_VIEW), getBundle());
            String title = getI18nLabel(I18n.WORKSPACE_CONFIG_TITLE);
            Dialogs.showModalDialog(title, configView, rootPane.getScene().getWindow());

        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handler of About menu action event.
     *
     * @param event
     */
    @FXML
    public void handleAbout(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(MainLayoutController.class.getResource(ABOUT_VIEW), getBundle());
            Scene aboutScene = new Scene(pane);

            Stage aboutStage = new Stage();
            aboutStage.initOwner(rootPane.getScene().getWindow());
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.setTitle("About");
            aboutStage.centerOnScreen();
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
