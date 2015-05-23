
package es.uma.pfc.is.algorithms.io;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory of file writers.
 * @author Dora Calderón
 */
public class WriterFactory {

    /**
     * Create a Writer instance for a file.
     * @param <T> Writer type.
     * @param writerClass Class.
     * @param fileName File name.
     * @return  Writer.
     */
    public static <T extends Writer> T getWriter(Class<T> writerClass, String fileName) {
        T writer = null;
        if(writerClass != null && !StringUtils.isEmpty(fileName)) {
            try {
                
                writer = writerClass.newInstance().create(fileName);
                
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(WriterFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ioEx) {
                Logger.getLogger(WriterFactory.class.getName()).log(Level.SEVERE, null, ioEx);
            }
        }
        return writer;
    }
}
