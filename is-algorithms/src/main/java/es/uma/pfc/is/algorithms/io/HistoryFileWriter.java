

package es.uma.pfc.is.algorithms.io;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class HistoryFileWriter extends PrintStream {

    public HistoryFileWriter(String fileName) throws FileNotFoundException {
        super(fileName);
    }
    
}
