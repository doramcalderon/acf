/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.output;

import java.io.IOException;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @since @author Dora Calderón
 */
public class Console extends OutputStream {

    /**
     * Control donde se volcará la salida.
     */
    private final TextArea textArea;

    /**
     * Constructor.
     *
     * @param textArea Salida.
     */
    public Console(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(final int i) throws IOException {
        Platform.runLater(new Runnable() {

            public void run() {
                textArea.appendText(String.valueOf((char) i));
            }
        });
    }
}
