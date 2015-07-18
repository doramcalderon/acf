package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.benchmarks.ActionsManager;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import static es.uma.pfc.is.bench.view.FXMLViews.ABOUT_VIEW;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Root Vieww.
 *
 * @author Dora Calderón
 */
public class MainLayoutController extends Controller {
    private static final int HOME_PANE_INDEX = 0;
    private static final int RUN_BENCHMARK_PANE_INDEX = 1;
    
            
    @FXML
    private Menu preferencesMenu;

    @FXML
    private BorderPane rootPane;
    @FXML
    private StackPane centerContainer;
    @FXML
    ToggleButton btnHome, btnBenchmarks, btnResults;

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
    protected void initView() throws IOException {
        initButtons();
        centerContainer.getChildren().get(HOME_PANE_INDEX).setVisible(true);
        centerContainer.getChildren().get(RUN_BENCHMARK_PANE_INDEX).setVisible(false);
    }
    
    protected void initButtons() {
        ToggleGroup group = new ToggleGroup();
        btnHome.setToggleGroup(group);
        btnBenchmarks.setToggleGroup(group);
        btnResults.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle selectedToggle) -> {
            int selectedIndex = -1;
            if(selectedToggle != null) {
                String buttonId =  ((ToggleButton) selectedToggle).getId();
                switch (buttonId) {
                    case "btnHome":
                        selectedIndex = 0;
                        break;
                    case "btnBenchmarks":
                        selectedIndex = 1;
                        break;
                }
            }
            selectPane(selectedIndex);
        });
    }

  protected void initListeners() {
        ActionsManager.get().getActionProperty().addListener(new ChangeListener() {
            //TODO Hacer con EventBus
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String action = (String) newValue;
                switch(action) {
                    case ActionsManager.NEW:
                        selectPane(RUN_BENCHMARK_PANE_INDEX);
                        break;
                    case ActionsManager.RUN:
                        selectPane(RUN_BENCHMARK_PANE_INDEX);
                        break;
                }
                ActionsManager.get().action("");
            }
        });
    }
  
  protected void selectPane(int index) {
      for(Node node : centerContainer.getChildren()) {
                node.setVisible((index == centerContainer.getChildren().indexOf(node)));
            }
  }
    
    /**
     * Abre el cuadro de diálogo para el registro de algoritmos.
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
