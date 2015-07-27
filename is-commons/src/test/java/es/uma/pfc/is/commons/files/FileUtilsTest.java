/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.commons.files;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class FileUtilsTest {
    
    public FileUtilsTest() {
    }

    /**
     * Test of createIfNoExists method, of class FileUtils.
     */
    @Test
    public void testCreateIfNoExists() throws Exception {
    }

    /**
     * Test of createDirIfNoExists method, of class FileUtils.
     */
    @Test
    public void testCreateDirIfNoExists() throws Exception {
    }

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
    
}
