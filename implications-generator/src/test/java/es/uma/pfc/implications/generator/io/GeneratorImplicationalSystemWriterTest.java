
package es.uma.pfc.implications.generator.io;

import fr.kbertet.lattice.ImplicationalSystem;
import java.io.BufferedWriter;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class GeneratorImplicationalSystemWriterTest {
    

    /**
     * Test of getFileName method, of class GeneratorImplicationalSystemWriter.
     */
    @Test
    public void testGetFileName() {
        GeneratorImplicationalSystemWriterImpl instance = new GeneratorImplicationalSystemWriterImpl();
        String name = "test.pl";
        int index = 10;
        String expectedName = "test_10.pl";
        String resultName = instance.getFileName(name, index);
        assertEquals(expectedName, resultName);
    }
    /**
     * Test of getFileName method, of class GeneratorImplicationalSystemWriter.
     */
    @Test
    public void testGetFileNameWithoutExtension() {
        GeneratorImplicationalSystemWriterImpl instance = new GeneratorImplicationalSystemWriterImpl();
        String name = "test";
        int index = 10;
        String expectedName = "test_10";
        String resultName = instance.getFileName(name, index);
        assertEquals(expectedName, resultName);
    }
    /**
     * Test of getFileName method, of class GeneratorImplicationalSystemWriter.
     */
    @Test
    public void testGetFileNameWithVariousExtension() {
        GeneratorImplicationalSystemWriterImpl instance = new GeneratorImplicationalSystemWriterImpl();
        String name = "test.pl.txt";
        int index = 10;
        String expectedName = "test_10.pl.txt";
        String resultName = instance.getFileName(name, index);
        assertEquals(expectedName, resultName);
    }
    /**
     * Test of getFileName method, of class GeneratorImplicationalSystemWriter.
     */
    @Test
    public void testGetNullFileName() {
        GeneratorImplicationalSystemWriterImpl instance = new GeneratorImplicationalSystemWriterImpl();
        String name = null;
        int index = 10;
        
        String resultName = instance.getFileName(name, index);
        
        assertNotNull(resultName);
        assertTrue(resultName.isEmpty());
    }

    public class GeneratorImplicationalSystemWriterImpl extends GeneratorImplicationalSystemWriter {

        @Override
        public void write(ImplicationalSystem system, BufferedWriter file) throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
