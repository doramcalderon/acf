
package es.uma.pfc.is.commons.files;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class FileUtilsTest {
    
  /**
     * Test the {@link FileUtils#getFileName(java.lang.String, int) }.
     */
    @Test
    public void testGetFileName() {
        String name = "test.txt";
        int index = 1;
        
        String expectedFileName = "test_1.txt";
        String fileName = FileUtils.getFileName(name, index);
        
        assertEquals(expectedFileName, fileName);
    }
    
    /**
     * Test the {@link FileUtils#getFileName(java.lang.String, int) }.
     */
    @Test
    public void testGetFileName_Null() {
        String filename = FileUtils.getFileName(null, 0);
        assertTrue(filename.isEmpty());
    }
    
    /**
     * Test the {@link FileUtils#getFileName(java.lang.String, int) }.
     */
    @Test
    public void testGetFileName_Empty() {
        String filename = FileUtils.getFileName("", 0);
        assertTrue(filename.isEmpty());
    }
    /**
     * Test of getExtension method, of class FileUtils.
     */
    @Test
    public void testGetExtension() {
    }

    /**
     * Test of splitNameAndExtension method, of class FileUtils.
     */
    @Test
    public void testSplitNameAndExtension() {
         String name = "test.txt";
         
         String [] nameExtension = FileUtils.splitNameAndExtension(name);
         
         assertEquals("test", nameExtension[0]);
         assertEquals("txt", nameExtension[1]);
    }

    /**
     * Test of getName method, of class FileUtils.
     */
    @Test
    public void testGetName() {
         String file = "test.txt";
         
         String name = FileUtils.getName(file);
         
         assertEquals("test", name);
    }
    
}
