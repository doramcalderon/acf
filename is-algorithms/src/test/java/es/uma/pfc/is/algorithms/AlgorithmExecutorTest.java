/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
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
     * Test of inputs method, of class AlgorithmExecutor.
     */
    @Test
    public void testInputs() throws IOException {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        String input = Files.createTempFile("temp", null).toString();
        exec = exec.inputs(input);

        assertNotNull(exec);
        assertEquals(input, exec.getInputs()[0]);
    }

    @Test
    public void testInputs_N() throws IOException {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);
        String input = Files.createTempFile("temp", null).toString();
        String input2 = Files.createTempFile("temp2", null).toString();
        String[] inputsExpected = new String[]{input, input2};
        exec = exec.inputs(input, input2);

        assertNotNull(exec);
        Assert.assertArrayEquals(inputsExpected, exec.getInputs());
    }

    /**
     * Test of inputs method, when the inputs path not exists.
     */
    @Test(expected = InvalidPathException.class)
    public void testBadInput_String() {
        Algorithm alg = mock(Algorithm.class);
        when(alg.getName()).thenReturn("Alg 1");

        AlgorithmExecutor exec = new AlgorithmExecutor(alg);

        String input = "aa";
        exec.inputs(input);
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
