/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.testutils;

import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @since @author Dora Calderón
 */
public class TestUtils {

    public static ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String path = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", file).toString();
        return new ImplicationalSystem(path);
    }
}
