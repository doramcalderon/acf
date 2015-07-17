/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.uitls;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @since @author Dora Calderón
 */
public class Animations {

    public static void fadeOut(Pane pane) {
        double opacity = pane.getOpacity();
        FadeTransition ft = new FadeTransition(Duration.millis(500), pane);
        ft.setFromValue(pane.getOpacity());
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();

        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                pane.setVisible(false);
                pane.setOpacity(opacity);
            }
        });
    }
}
