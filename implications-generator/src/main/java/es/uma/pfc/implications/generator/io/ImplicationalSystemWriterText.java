/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.implications.generator.io;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.io.ImplicationalSystemWriterFactory;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ImplicationalSystemWriterText extends GeneratorImplicationalSystemWriter {
    /**
     * Instancia única.
     */
    private static ImplicationalSystemWriterText instance;
    /**
     * Writer de java-lattice.
     */
    private fr.kbertet.lattice.io.ImplicationalSystemWriterText textWriter;

    private ImplicationalSystemWriterText() {
        textWriter = fr.kbertet.lattice.io.ImplicationalSystemWriterText.getInstance();
    }
    
    
      /**
     * Register this class for writing .txt files.
     */
    public static void register() {
        ImplicationalSystemWriterFactory.register(ImplicationalSystemWriterProlog.getInstance(), "txt");
    }

    /**
     * Devuelve una instancia única de ImplicationalSystemWriterProlog.
     *
     * @return  Instancia única.
     */
    public static ImplicationalSystemWriterText getInstance() {
        if (instance == null) {
            instance = new ImplicationalSystemWriterText();
        }
        return instance;
    }

    @Override
    public void write(ImplicationalSystem system, BufferedWriter file) throws IOException {
        textWriter.write(system, file);
    }

}
