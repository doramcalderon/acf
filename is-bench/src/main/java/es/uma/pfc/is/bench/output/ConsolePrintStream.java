
package es.uma.pfc.is.bench.output;

import es.uma.pfc.is.algorithms.io.PrintStream;

/**
 *
 * @since @author Dora Calderón
 */
public class ConsolePrintStream extends PrintStream {

    public ConsolePrintStream(Console console) {
        super(console, false);
    }

        
        
    }
