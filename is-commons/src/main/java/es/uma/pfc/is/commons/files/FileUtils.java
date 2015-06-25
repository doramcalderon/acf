package es.uma.pfc.is.commons.files;

import java.io.File;
import java.io.IOException;

/**
 * Utilidades con ficheros.
 *
 * @author Dora Calderón
 */
public class FileUtils {

    /**
     * Crea las carpetas y archivo de un path si no existe.
     * @param path Path.
     * @return Fichero.
     * @throws IOException 
     */
    public static File createIfNoExists(String path) throws IOException {
        File file = null;
        
        if(path != null && !path.isEmpty()) {
            file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            
        }
        return file;
    }

    /**
     * Crea las carpetas y archivo de un path si no existe.
     * @param path Path.
     * @return Fichero.
     * @throws IOException 
     */
    public static File createDirIfNoExists(String path) throws IOException {
        File configFile = null;
        
        if(path != null && !path.isEmpty()) {
            configFile = new File(path);
            if (!configFile.exists()) {
                configFile.mkdirs();
            }
            
        }
        return configFile;
    }
}
