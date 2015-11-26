package es.uma.pfc.is.bench.uitls;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import org.slf4j.LoggerFactory;

/**
 *
 * @since @author Dora Calderón
 */
public class Animations {

    public static void fadeOut(Node node) {
        fadeOut(node, 500);
    }
    public static void fadeOut(Node node, double duration) {
        double opacity = node.getOpacity();
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(node.getOpacity());
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();

        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                node.setVisible(false);
                node.setOpacity(opacity);
            }
        });
    }
    public static void fadeIn(Node node, double duration) {
        double opacity = node.getOpacity();
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(0.0);
        ft.setToValue(opacity);
        ft.setCycleCount(1);
        ft.play();

        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                node.setVisible(false);
                node.setOpacity(opacity);
            }
        });
    }
    public static void fadeInOut(Node node, double duration) {
        double opacity = node.getOpacity();
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(0.0);
        ft.setToValue(opacity);
        ft.setCycleCount(1);
        ft.play();

        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException ex) {
                    LoggerFactory.getLogger(Animations.class).error("", ex);
                }
                fadeOut(node, duration);
            }
        });
    }
}
