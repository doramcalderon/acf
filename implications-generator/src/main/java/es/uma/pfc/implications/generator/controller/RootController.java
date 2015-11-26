
package es.uma.pfc.implications.generator.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Root layout ocntroller.
 * @author Dora Calderón
 */
public class RootController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleHelpOption(ActionEvent event) {
            WebView helpViewer = new WebView();
        String html = Thread.currentThread().getContextClassLoader().getResource("es/uma/pfc/implications/generator/help/help.xhtml").toExternalForm();
        
        helpViewer.getEngine().load(html);
            Stage st = new Stage();
            st.setScene(new Scene(helpViewer));
            st.show();
    }
    

}
