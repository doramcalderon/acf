/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
    private StringBuilder sb;

    /**
     * Constructor.
     *
     * @param textArea Salida.
     */
    public Console(TextArea textArea) {
        this.textArea = textArea;
        this.sb = new StringBuilder();
    }

    @Override
    public void write(final int i) throws IOException {
        sb.append((char) i);
        
    }

    @Override
    public void flush() throws IOException {
        Platform.runLater(new Runnable() {

            public void run() {
                textArea.appendText(sb.toString());
            }
        });
    }

    
    
    
}
