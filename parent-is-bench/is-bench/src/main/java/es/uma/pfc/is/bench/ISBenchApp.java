package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ISBenchApp extends Application {


    private Stage primaryStage;
    private BorderPane root;
    private ResourceBundle bundle;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
//        System.setProperty("log4j.configurationFile", "es/uma/pfc/is/bench/log4j2.xml");
//        System.setProperty("isbench.output.dir", ConfigManager.get().getDefaultOutputDir().getPath());
        Locale.setDefault(new Locale("en", "GB"));
        loadResources(Locale.getDefault());
        loadRootLayout();
        showStage(primaryStage);
    }
    
    /**
     * Load resource bundles.
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
     * @param primaryStage Stage.
     */
    protected void showStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IS Bench");
        primaryStage.show();
    }

    @FXML
    public void close(ActionEvent event) {
        primaryStage.close();
    }
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
