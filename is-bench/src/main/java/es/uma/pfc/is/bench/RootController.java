package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.benchmarks.ActionsManager;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import static es.uma.pfc.is.bench.view.FXMLViews.ABOUT_VIEW;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Root Vieww.
 *
 * @author Dora Calderón
 */
public class RootController extends Controller {

    @FXML
    private Menu preferencesMenu;

    @FXML
    private BorderPane rootPane;
    
    @FXML TabPane rootTabPane;
    
    @FXML
    private Tab generatorTab;
    
    @FXML
    private Tab benchmarksTab;
    
    @FXML
    private Tab runBenchmarkTab;
    
    @FXML
    private Tab createTab;
    
    @FXML
    private Tab resultsTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        initView(resources);
        initListeners();
    }

    /**
     * Initialize the view.
     * @param resources Resource Bundle.
     */
    protected void initView(ResourceBundle resources) {
        try {
            initializeTabs(resources);
            rootTabPane.getSelectionModel().select(benchmarksTab);
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void initializeTabs(ResourceBundle resources) throws IOException {
        Pane benchmarksForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.BENCHMARKS_DASHBOARD_VIEW), resources);
        benchmarksTab.setContent(benchmarksForm);
        
        Pane runBenchmarkForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.RUN_BENCHMARK_VIEW), resources);
        runBenchmarkTab.setContent(runBenchmarkForm);
        
//        Pane newBenchForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.NEW_ALGORITHM_VIEW), resources);
//        createTab.setContent(newBenchForm);
        
        Pane resultsView = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.RESULTS_VIEW), resources);
        resultsTab.setContent(resultsView);
        
        Pane generatorForm = FXMLLoader.load(ISBenchApp.class.getResource("/" + es.uma.pfc.implications.generator.view.FXMLViews.IMPLICATIONS_VIEW), 
                                                 ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", Locale.getDefault()));
         generatorTab.setContent(generatorForm);
    }
    
    protected void initListeners() {
        ActionsManager.get().getActionProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String action = (String) newValue;
                switch(action) {
                    case ActionsManager.NEW:
                        rootTabPane.getSelectionModel().select(createTab);
                        break;
                    case ActionsManager.RUN:
                        rootTabPane.getSelectionModel().select(runBenchmarkTab);
                        break;
                }
                ActionsManager.get().action("");
            }
        });
    }
    
    /**
     * Initialize the benchmarks tab content.
     * @param resources ResourceBundle.
     * @throws IOException 
     */
    protected void initializeBenchmarksTab(ResourceBundle resources) throws IOException {
        Pane benchmarksForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.RUN_BENCHMARK_VIEW), resources);
        benchmarksTab.setContent(benchmarksForm);
    }
    
    /**
     * Initialize the generator tab content.
     * @param resources ResourceBundle.
     * @throws IOException 
     */
    protected void initializeGeneratorTab(ResourceBundle resources) throws IOException {
        Pane generatorForm = FXMLLoader.load(ISBenchApp.class.getResource("/" + es.uma.pfc.implications.generator.view.FXMLViews.IMPLICATIONS_VIEW), 
                                                 ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.labels", Locale.getDefault()));
         generatorTab.setContent(generatorForm);
    }
    
    /**
     * Abre el cuadro de diálogo para el registro de algoritmos.
     * @param event Action Event.
     */
    @FXML
    public void handleMenuAlgorithms(ActionEvent event) {
        try {
            Parent algorithmsPane = FXMLLoader.load(RootController.class.getResource(FXMLViews.ALGORITHMS_VIEW), getBundle());
            String title = getI18nLabel(I18n.ALGORITHMS_DIALOG_TITLE);
            Dialogs.showModalDialog(title, algorithmsPane, rootPane.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
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
            TabPane configView = FXMLLoader.load(RootController.class.getResource(FXMLViews.USER_CONFIG_VIEW), getBundle());
            String title = getI18nLabel(I18n.WORKSPACE_CONFIG_TITLE);
            Dialogs.showModalDialog(title, configView, rootPane.getScene().getWindow());

        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbout(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(RootController.class.getResource(ABOUT_VIEW), getBundle());
            Scene aboutScene = new Scene(pane);
            
            Stage aboutStage = new Stage();
            aboutStage.initOwner(rootPane.getScene().getWindow());
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.setTitle("About");
            aboutStage.centerOnScreen();
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
