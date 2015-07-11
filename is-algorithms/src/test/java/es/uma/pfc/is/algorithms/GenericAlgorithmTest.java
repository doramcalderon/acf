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
    public void testExecute_AlgorithmOptions() {
    }

    /**
     * Test of execute method, of class GenericAlgorithm.
     */
    @Test
    public void testExecute_AlgorithmOptions_OutputStream() {
    }

    /**
     * Test of execute method, of class GenericAlgorithm.
     */
    @Test
    public void testExecute() {
    }

    /**
     * Test of input method, of class GenericAlgorithm.
     */
    @Test
    public void testInput_String() throws IOException {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        String input = Files.createTempFile("temp", null).toString();
        alg = alg.input(input);
        
        assertNotNull(alg);
        assertEquals(input, alg.getInput());
    }

    /**
     * Test of input method, of class GenericAlgorithm.
     */
    @Test(expected = InvalidPathException.class)
    public void testBadInput_String() {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        String input = "aa";
        alg.input(input);
        fail("Se esperaba InvalidPathException.");
    }

    /**
     * Test of input method, of class GenericAlgorithm.
     */
    @Test
    public void testInput_InputStream() {
    }

 
    /**
     * Test of traceOutput method, of class GenericAlgorithm.
     */
    @Test
    public void testOutput_OutputStream() throws IOException {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        Path path = Files.createTempFile("temp", null);
        OutputStream output = new FileOutputStream(path.toString());
        
        alg = alg.traceOutput(Mode.HISTORY, output);
        
        assertNotNull(alg);
        assertTrue(alg.getOutputs() != null && !alg.getOutputs().isEmpty());
        assertNotNull(alg.getOutputs().get(Mode.HISTORY).get(0));
    }

     @Test
    public void testOption() {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        alg = alg.option("config", 1);
        
        assertEquals(new Integer(1), alg.getOptions().<Integer>getOption("config"));
    }
    @Test
    public void testEnable() {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        alg.enable(AlgorithmOptions.Mode.HISTORY);
        assertTrue(alg.getOptions().getOption(AlgorithmOptions.Mode.HISTORY.toString()));
    }
    @Test
    public void testDisable() {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        alg.disable(AlgorithmOptions.Mode.HISTORY);
        assertFalse(alg.getOptions().getOption(AlgorithmOptions.Mode.HISTORY.toString()));
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
    }

    
    /**
     * Test of getDefaultOutputPath method, of class GenericAlgorithm.
     */
    @Test
    public void testDefaultOutputPath() {
        GenericAlgorithm alg = new GenericAlgorithmImpl();
        alg.setShortName("do");
        String defaultOutput = alg.getDefaultOutputPath();
        
        assertEquals("do_output.txt", defaultOutput);
    }


    
}
