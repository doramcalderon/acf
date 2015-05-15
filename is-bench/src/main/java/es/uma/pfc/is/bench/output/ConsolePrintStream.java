/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.output;

import java.io.PrintStream;

/**
 *
 * @since @author Dora Calderón
 */
public class ConsolePrintStream extends PrintStream {

    public ConsolePrintStream(Console console) {
        super(console);
    }
    

    
}
