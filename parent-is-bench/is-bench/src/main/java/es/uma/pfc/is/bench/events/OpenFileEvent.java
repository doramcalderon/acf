/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.events;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class OpenFileEvent {
    private File file;

    public OpenFileEvent(String file) {
        this(Paths.get(file));
    }
    public OpenFileEvent(Path path) {
        this.file = path.toFile();
    }

    public File getFile() {
        return file;
    }
    
    
}
