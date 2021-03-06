package es.uma.pfc.is.bench;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.benchmarks.execution.FileViewerListener;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import es.uma.pfc.is.bench.events.MessageEvent;
import es.uma.pfc.is.bench.events.NavigationEvent;
import es.uma.pfc.is.bench.events.OpenFileEvent;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.bench.i18n.I18n;
import es.uma.pfc.is.bench.uitls.Animations;
import es.uma.pfc.is.bench.uitls.Dialogs;
import es.uma.pfc.is.bench.view.FXMLViews;
import static es.uma.pfc.is.bench.view.FXMLViews.ABOUT_VIEW;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller of Root Vieww.
 *
 * @author Dora Calderón
 */
public class MainLayoutController extends Controller {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(MainLayoutController.class);

    private static final int HOME_PANE_INDEX = 0;
    private static final int BENCHMARKS_PANE_INDEX = 1;
    private static final int RESULTS_PANE_INDEX = 2;
    private static final int GENERATOR_PANE_INDEX = 3;

    /**
     * Spanish language menu id.
     */
    private static final String MENU_LANGUAGE_ES = "menuLanguageES";
    /**
     * English language menu id.
     */
    private static final String MENU_LANGUAGE_EN = "menuLanguageEN";

    /**
     * Map whit buttons and panes relations. The key is the button id and the value, the index of pane in the stack
     * pane.
     */
    private Map<String, Integer> panes = new HashMap();

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lbStateBar;
    @FXML
    private Pane stateBarPane;
    
    @FXML
    private RadioMenuItem menuLanguageEN, menuLanguageES;
    @FXML
    private Tab tabBenchmarks;
    @FXML
    private TabPane mainTabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.initialize(location, resources);
            initView();
            initListeners();
        } catch (IOException ex) {
            logger.error("Error initializing MainLayoutController", ex);
        }

    }

    @Override
    protected void initView() throws IOException {
        Locale locale = WorkspaceManager.get().getLocale();
        ToggleGroup tGroup = new ToggleGroup();
        
        menuLanguageES.setToggleGroup(tGroup);
        menuLanguageEN.setToggleGroup(tGroup);
        
        menuLanguageES.setSelected(locale.getLanguage().equals("es"));
        menuLanguageEN.setSelected(locale.getLanguage().equals("en"));
    }

    @Override
    protected void initListeners() {
        Eventbus.register(this);
        Eventbus.register(new FileViewerListener());
    }

    @Override
    protected Pane getRootPane() {
        return rootPane;
    }

    @Subscribe
    public void newBenchmark(NavigationEvent event) {
        mainTabPane.getSelectionModel().select(tabBenchmarks);
    }

    /**
     * Show the message of MessageEvent in the state bar.
     *
     * @param event Event.
     */
    @Subscribe
    public void showMessage(MessageEvent event) {
        Platform.runLater(() -> {
            lbStateBar.setText(event.getMessage());
            lbStateBar.setVisible(true);
            stateBarPane.setOpacity(0.65);
            stateBarPane.setVisible(true);
            switch (event.getLevel()) {
                case INFO:
                    stateBarPane.setBackground(new Background(
                            new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case SUCCEEDED:
                    stateBarPane.setBackground(new Background(
                            new BackgroundFill(Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case WARNING:
                    stateBarPane.setBackground(new Background(
                            new BackgroundFill(Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case ERROR:
                    stateBarPane.setBackground(new Background(
                            new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;

            }
            Animations.fadeInOut(stateBarPane, 2000);
        });
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
            logger.error("Error opening workspaces vindow.", ex);
        }
    }

    /**
     * Changes to the language selected.
     *
     * @param event Event.
     */
    @FXML
    public void handleLanguageAction(ActionEvent event) {
        RadioMenuItem item = (RadioMenuItem) event.getSource();
        Locale locale;
        if (MENU_LANGUAGE_ES.equals(item.getId()) && item.isSelected()) {
            locale = new Locale("es", "ES");
        } else {
            locale = new Locale("en", "GB");
        }
        WorkspaceManager.get().setLocalePreference(locale);

        String title = getI18nMessage(BenchMessages.LANGUAGE_CHANGE_TITLE);
        String message = getI18nMessage(BenchMessages.LANGUAGE_CHANGE);
        Optional<ButtonType> response = showAlert(Alert.AlertType.CONFIRMATION, title, message);
    }

    /**
     * Handler of User Guide menu action event.
     *
     * @param event
     */
    @FXML
    public void handleUserGuide(ActionEvent event) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL docUrl = cl.getResource("doc/userguide.pdf");
        
            if (docUrl != null) {
                File userGuide = new File(docUrl.toURI());
                Eventbus.post(new OpenFileEvent(userGuide));
            } else {
                throw new RuntimeException("userguide.pdf not found.");
            }
        } catch (URISyntaxException | RuntimeException ex ) {
            String message = getI18nMessage(BenchMessages.OPEN_USERGUIDE_ERROR);
            logger.error(message, ex);
            publicMessage(message, MessageEvent.Level.ERROR);
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
            logger.error("Error opening the about view.", ex);
        }
    }
}
