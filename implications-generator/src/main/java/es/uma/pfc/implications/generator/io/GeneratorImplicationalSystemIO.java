/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.io;

import com.google.common.base.Strings;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class GeneratorImplicationalSystemIO {
    /**
      * Guarda n sistemas en n ficheros con el nombre filename_1, filename_2, ..., filename_n.
     * @param systems Sistemas de implicaciones.
     * @param fileName Prefijo del nombre de los ficheros. 
     * @throws IOException Error en la lectura / escritura de los ficheros.
     */
    public static void save(List<ImplicationalSystem> systems, String fileName) throws IOException {
        if (systems != null) {
            if (systems.size() == 1) {
                systems.get(0).save(fileName);
            } else {
                int i = 1;
                String fileNameWithIndex;
                for (ImplicationalSystem system : systems) {
                    fileNameWithIndex = getFileName(fileName, i);
                    checkFile(fileNameWithIndex);
                    system.save(fileNameWithIndex);
                    i++;
                }
            }
        }
    }
    /**
     * Comprueba si existe el fichero con nombre {@code fileName}, y si no existe, lo crea.
     * @param fileName Nombre del fichero.
     * @throws IOException Error en la lectura / escritura del fichero.
     */
    protected static void checkFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
    
     /**
     * Devuelve el nombre añadiéndole el sufijo "_index".
     * @param name Nombre.
     * @param index Índice.
     * @return Nombre con el sufijo _index.
     */
    protected static String getFileName(String name, int index) {
        String fileName = "";
        if(!Strings.isNullOrEmpty(name)) {
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
    
    protected static String getExtension(String fileName) {
        String extension = "";
        if(!Strings.isNullOrEmpty(fileName)) {
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
    protected static String[] splitNameAndExtension(String fileName) {
        String[] nameSplits = null;
        if(!Strings.isNullOrEmpty(fileName)) {
            int indexLastPoint = fileName.lastIndexOf(".");
            if(indexLastPoint >= 0) {
                nameSplits = new String[]{fileName.substring(0, indexLastPoint), fileName.substring(indexLastPoint)};
            } else {
                nameSplits = new String[]{};
            }
        }
        return nameSplits;
    }
}
