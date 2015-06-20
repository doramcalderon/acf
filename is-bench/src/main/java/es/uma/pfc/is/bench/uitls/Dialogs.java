/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.uitls;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @since @author Dora Calderón
 */
public class Dialogs {

    public static void showModalDialog(String title, Parent content, Window parent) {

        Scene preferencesScene = new Scene(content);

        Stage preferencesStage = new Stage();
        preferencesStage.initOwner(parent);
        preferencesStage.initModality(Modality.WINDOW_MODAL);
        preferencesStage.setTitle(title);
        preferencesStage.centerOnScreen();
        preferencesStage.setScene(preferencesScene);
        preferencesStage.showAndWait();
    }
}
