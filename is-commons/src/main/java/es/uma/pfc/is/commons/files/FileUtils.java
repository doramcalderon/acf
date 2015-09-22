package es.uma.pfc.is.commons.files;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

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
                File parentFile = file.getParentFile();
                if(parentFile != null) {
                    parentFile.mkdirs();
                }
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
    /**
     * Crea las carpetas y archivo de un path si no existe.
     * @param path Path.
     * @return Fichero.
     * @throws IOException 
     */
    public static File createDirOrResetIfExists(String path) throws IOException {
        File configFile = null;
        
        if(path != null && !path.isEmpty()) {
            configFile = new File(path);
            if(configFile.exists()) {
                configFile.delete();
            }

            configFile.mkdirs();
            
        }
        return configFile;
    }
    
    
    /**
     * Devuelve el nombre añadiéndole el sufijo "_index".
     * @param name Nombre.
     * @param index Índice.
     * @return Nombre con el sufijo _index.
     */
    public static String getFileName(String name, int index) {
        String fileName = "";
        if(!StringUtils.isEmpty(name)) {
            String[] nameSplits = splitNameAndExtension(name);
            int splitsCount = nameSplits.length;
            
            if(splitsCount > 1) {
                fileName = nameSplits[0] + "_" + String.valueOf(index) + "." + nameSplits[1];
            } else {
                fileName = name + "_" + String.valueOf(index);
            }
            
        }
        return fileName;
    }
    
    public static String getExtension(String fileName) {
        String extension = "";
        if(!StringUtils.isEmpty(fileName)) {
            String[] nameSplits = splitNameAndExtension(fileName);
            int splitsCount = nameSplits.length;
            
            if(splitsCount > 1) {
                extension = nameSplits[1];
            }
            
        }
        return extension;
    }
    
    /**
     * Returns the filename splited in name and extension.
     * @param fileName File name.
     * @return Name and extension.
     */
    public static String[] splitNameAndExtension(String fileName) {
        String[] nameSplits = null;
        if(!StringUtils.isEmpty(fileName)) {
            int indexLastPoint = fileName.lastIndexOf(".");
            if(indexLastPoint >= 0) {
                nameSplits = new String[]{fileName.substring(0, indexLastPoint), fileName.substring(indexLastPoint + 1)};
            } else {
                nameSplits = new String[]{};
            }
        }
        return nameSplits;
    }
}
