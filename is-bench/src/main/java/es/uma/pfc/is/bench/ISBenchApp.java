package es.uma.pfc.is.bench;

import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.bench.view.FXMLViews;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ISBenchApp extends Application {


    private Stage primaryStage;
    private BorderPane root;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("log4j.configurationFile", "es/uma/pfc/is/bench/log4j2.xml");
        System.setProperty("isbench.output.dir", UserConfig.get().getDefaultOutputDir().getPath());
        loadRootLayout();
        loadMainForm();
        showStage(primaryStage);
    }
    
    /**
     * Load root layout.
     */
    protected void loadRootLayout() {
        try {
            root = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.ROOT_VIEW));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Load the main form inside the root layout.
     */
    protected void loadMainForm() {
        try {
            Pane mainForm = FXMLLoader.load(ISBenchApp.class.getResource(FXMLViews.MAIN_VIEW));
            root.setCenter(mainForm);
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
