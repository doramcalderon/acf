package es.uma.pfc.is.bench.output;

import es.uma.pfc.is.algorithms.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @since @author Dora Calderón
 */
public class ConsolePrintStream extends PrintStream {

    public ConsolePrintStream(Console console) {
        super(console, false);
    }

}
