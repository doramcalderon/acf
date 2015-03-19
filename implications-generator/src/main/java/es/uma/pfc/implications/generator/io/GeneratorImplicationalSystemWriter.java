/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.io;

import com.google.common.base.Strings;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.io.ImplicationalSystemWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ImplicationalSystemWriter que permite guardar n sistemas de implicaciones a la vez.
 * @since 1.0.0
 * @author Dora Calderón
 */
public abstract class GeneratorImplicationalSystemWriter implements ImplicationalSystemWriter {
    /**
     * Guarda n sistemas en n ficheros con el nombre filename_1, filename_2, ..., filename_n.
     * @param systems Sistemas de implicaciones.
     * @param fileName Prefijo del nombre de los ficheros. 
     * @throws IOException Error en la lectura / escritura de los ficheros.
     */
    public void write(List<ImplicationalSystem> systems, String fileName) throws IOException {
        if (systems != null) {
            int i = 1;
            for (ImplicationalSystem system : systems) {
                File f = new File(getFileName(fileName, i));
                write(system, new BufferedWriter(new FileWriter(f)));
                i++;
            }
        }
    }

    /**
     * Devuelve el nombre añadiéndole el sufijo "_index".
     * @param name Nombre.
     * @param index Índice.
     * @return Nombre con el sufijo _index.
     */
    protected String getFileName(String name, int index) {
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
            

}
