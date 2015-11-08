
package es.uma.pfc.implications.generator.io;

import es.uma.pfc.is.commons.files.FileUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implements the methods to save implicational systems in files.
 * @author Dora Calderón
 */
public class GeneratorImplicationalSystemIO {
    /**
     * Saves n systems in  n files with the names filename_1, filename_2, ..., filename_n.<br/>
     * If the list only contains one element, this is saved in the file with the name {@code fileName}.
     * @param systems Implicational systems.
     * @param fileName File names prefix.
     * @throws IOException Read / write error.
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
     * Checks if the file with name {@code fileName} exists.<br/>
     * If no exists, this is created.
     * @param fileName File name.
     * @throws IOException Read / write error.
     */
    protected static void checkFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            FileUtils.createDirIfNoExists(path.getParent().toString());
            Files.createFile(path);
        }
    }

}
