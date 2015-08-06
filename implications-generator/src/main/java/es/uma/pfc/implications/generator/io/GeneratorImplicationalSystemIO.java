/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.io;

import com.google.common.base.Strings;
import es.uma.pfc.is.commons.files.FileUtils;
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
                FileUtils.createIfNoExists(fileName);
                systems.get(0).save(fileName);
            } else {
                int i = 1;
                String fileNameWithIndex;
                for (ImplicationalSystem system : systems) {
                    fileNameWithIndex = FileUtils.getFileName(fileName, i);
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

}
