
package es.uma.pfc.implications.generator.io;

import es.uma.pfc.is.commons.files.FileUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementa los métodos para guardar sistemas implicacionales en archivos.
 * @author Dora Calderón
 */
public class GeneratorImplicationalSystemIO {
    /**
      * Guarda n sistemas en n ficheros con el nombre filename_1, filename_2, ..., filename_n.<br/>
      * Si la lista sólo contiene un elemento, se guarda el sistema en el archivo con el nombre pasado en el parámetro {@code fileName}.
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
            FileUtils.createDirIfNoExists(path.getParent().toString());
            Files.createFile(path);
        }
    }

}
