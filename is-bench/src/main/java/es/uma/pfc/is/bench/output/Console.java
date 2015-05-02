/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.output;

import java.io.IOException;
import java.io.OutputStream;
import javafx.scene.control.TextArea;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class Console extends OutputStream {
    /**
     * Control donde se volcará la salida.
     */
    private TextArea textArea;

    /**
     * Constructor.
     * @param textArea Salida.
     */
    public Console(TextArea textArea) {
        this.textArea = textArea;
    }
    
    

    @Override
    public void write(int i) throws IOException {
        textArea.appendText(String.valueOf((char) i));
    }


    
}
