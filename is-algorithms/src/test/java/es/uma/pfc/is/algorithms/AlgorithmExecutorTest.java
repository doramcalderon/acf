/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmExecutorTest {

    public AlgorithmExecutorTest() {
    }

    /**
     * Test of execute method, of class AlgorithmExecutor.
     */
    @Test
    public void testExecute() {
    }

    /**
     * Test of init method, of class AlgorithmExecutor.
     */
    @Test
    public void testInit() {
    }

    /**
     * Test of run method, of class AlgorithmExecutor.
     */
    @Test
    public void testRun() {
    }

    /**
     * Test of stop method, of class AlgorithmExecutor.
     */
    @Test
    public void testStop() {
    }

    /**
     * Test of input method, of class AlgorithmExecutor.
     */
    @Test
    public void testInput() throws IOException {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        String input = Files.createTempFile("temp", null).toString();
        exec = exec.input(input);

        assertNotNull(exec);
        assertEquals(input, exec.getInput());
    }

    /**
     * Test of input method, when the input path not exists.
     */
    @Test(expected = InvalidPathException.class)
    public void testBadInput_String() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);

        String input = "aa";
        exec.input(input);
        fail("Se esperaba InvalidPathException.");
    }

    /**
     * Test of output method, of class AlgorithmExecutor.
     */
    @Test
    public void testOutput() throws IOException {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        String output = Files.createTempFile("temp", null).toString();
        exec = exec.output(output);

        assertNotNull(exec);
        assertEquals(output, exec.getOutput());
    }
      

    /**
     * Test of enable method, of class AlgorithmExecutor.
     */
    @Test
    public void testEnable() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");
        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        
        exec.enable(AlgorithmOptions.Mode.HISTORY);
        
        assertTrue(exec.getOptions().getOption(AlgorithmOptions.Mode.HISTORY.toString()));
    }

    /**
     * Test of disable method, of class AlgorithmExecutor.
     */
    @Test
    public void testDisable() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        exec.disable(AlgorithmOptions.Mode.HISTORY);
        assertFalse(exec.getOptions().getOption(AlgorithmOptions.Mode.HISTORY.toString()));
    }

    /**
     * Test of option method, of class AlgorithmExecutor.
     */
    @Test
    public void testOption() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");
        AlgorithmExecutor exec = new AlgorithmExecutor(alg);

        exec.option("config", 1);

        assertEquals(new Integer(1), exec.getOptions().<Integer>getOption("config"));
    }

}
