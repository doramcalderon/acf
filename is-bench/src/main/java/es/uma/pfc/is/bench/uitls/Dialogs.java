/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.uitls;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

    /**
     * Create an alert.
     * @param type Alert type.
     * @param title Title.
     * @param message Message.
     * @param parent Parent window.
     * @return Alert.
     */
    public static Alert alert(AlertType type, String title, String message, Window parent) {
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(parent);
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().setHeaderText(title);
//        alert.showAndWait()
//            .filter(response -> response == ButtonType.OK)
//            .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
    }
}
