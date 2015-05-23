
package es.uma.pfc.is.algorithms.io;

import java.io.IOException;

/**
 *
 * @author Dora Calderón
 */
public interface Writer {
    /**
     * Create a writer for filename argument.
     * @param <T> Type.
     * @param fileName Name of file.
     * @return Writer.
     * @throws java.io.IOException
     */
    <T extends Writer> T create(String fileName) throws IOException;
    
    /**
     * Print a message with args.
     * @param message Message.
     * @param args Arguments.
     */
    void print(String message, Object ... args);
    
    /**
     * Finish the writer and close all streams.
     */
    void finish();
}
