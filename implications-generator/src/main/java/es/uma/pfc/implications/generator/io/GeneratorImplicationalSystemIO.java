/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.io;

import com.google.common.base.Strings;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.io.ImplicationalSystemWriter;
import fr.kbertet.lattice.io.ImplicationalSystemWriterFactory;
import java.io.IOException;
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
                ImplicationalSystemWriter writer =  ImplicationalSystemWriterFactory.get(getExtension(fileName));
                for (ImplicationalSystem system : systems) {
                    system.save(getFileName(fileName, i));
                    i++;
                }
            }
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
            String[] nameSplits = name.split("\\.", 2);
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
            String[] nameSplits = fileName.split("\\.", 2);
            int splitsCount = nameSplits.length;
            
            if(splitsCount > 1) {
                extension = nameSplits[1];
            }
            
        }
        return extension;
    }
}
