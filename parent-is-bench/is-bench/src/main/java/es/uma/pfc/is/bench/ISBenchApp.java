package es.uma.pfc.is.bench;

import com.google.common.eventbus.Subscribe;
import es.uma.pfc.is.bench.config.WorkspaceManager;
import es.uma.pfc.is.bench.events.OpenFileEvent;
import es.uma.pfc.is.bench.view.FXMLViews;
import es.uma.pfc.is.commons.eventbus.Eventbus;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISBenchApp extends Application {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ISBenchApp.class);

    private Stage primaryStage;
    private StackPane root;
    private ResourceBundle bundle;

    public static final String SPLASH_IMAGE = "images/lattice_splash.png";

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                SPLASH_IMAGE
        ));
        loadProgress = new ProgressBar();
        AnchorPane anchProgress = new AnchorPane();
        anchProgress.getChildren().add(loadProgress);
        AnchorPane.setBottomAnchor(loadProgress, new Double(0));
        AnchorPane.setLeftAnchor(loadProgress, new Double(0));
        AnchorPane.setRightAnchor(loadProgress, new Double(0));
        AnchorPane.setTopAnchor(loadProgress, new Double(0));
        //loadProgress.setPrefWidth(splash.getFitWidth());
        progressText = new Label("Loading . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, anchProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                updateMessage("Loading  . . .");
                loadResources(WorkspaceManager.get().getLocale());
                loadRootLayout();

                updateMessage("App loaded.");

                return null;
            }

        };

        showSplash(initStage, loadTask, () -> showStage());
        new Thread(loadTask).start();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.setScene(splashScene);
        initStage.show();
    }

    public interface InitCompletionHandler {

        public void complete();
    }

//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        
//        
//        
//        
//        Eventbus.register(this);
//        loadResources(WorkspaceManager.get().getLocale());
//        loadRootLayout();
//        showStage(primaryStage);
//
//    }
    /**
     * Opens a file with the associated system program.
     *
     * @param event Event.
     */
    @Subscribe
    public void openFile(OpenFileEvent event) {
        try {
            getHostServices().showDocument(event.getFile().toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            logger.error("Error openning " + ((event != null) ? event.getFile() : "null"), ex);
        }
    }

    /**
     * Load resource bundles.
     *
     * @param locale Locale.
     */
    protected void loadResources(Locale locale) {
        bundle = ResourceBundle.getBundle("bundles.labels", locale);
    }

    /**
     * Load root layout.
     */
    protected void loadRootLayout() {
        try {
            root = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.ROOT_VIEW), bundle);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Show the main window with de main form.
     *
     * @param primaryStage Stage.
     */
    protected void showStage() {
        Eventbus.register(this);
        this.primaryStage = new Stage(StageStyle.DECORATED);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IS Bench - " + WorkspaceManager.get().currentWorkspace().getName());
        primaryStage.show();
    }

    @FXML
    public void close(ActionEvent event) {
        primaryStage.close();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
