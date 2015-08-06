/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dora Calderón
 */
public class GenericAlgorithmTest {
    
    public GenericAlgorithmTest() {
    }

     
    
    
    
    /**
     * Test of execute method, of class GenericAlgorithm.
     */
    @Test
    public void testExecute_ImplicationalSystem() {
    }

    public class GenericAlgorithmImpl extends GenericAlgorithm {
            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getName() {
                return "GenericAlgImpl";
            }

        @Override
        public String getShortName() {
            return "do";
        }
            
                
    }
    
    
    @Test
    public void testDefaultOutputPath() {
        Algorithm alg = new GenericAlgorithmImpl();
        String defaultOutput = alg.getDefaultOutputFileName();
        assertEquals("do_output.txt", defaultOutput);
    }

  
}
