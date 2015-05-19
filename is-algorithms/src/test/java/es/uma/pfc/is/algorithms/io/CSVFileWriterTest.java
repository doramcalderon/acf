/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.csv.CSVFormat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class CSVFileWriterTest {
    
    public CSVFileWriterTest() {
    }

    /**
     * Test of header method, of class CSVFileWriter.
     */
    @Test
    public void testHeader() {
        CSVFileWriter writer = new CSVFileWriter("file.csv").header("Name", "Age");
        assertArrayEquals(new String[]{"Name", "Age"}, writer.getHeader());
    }
    @Test
    public void testNullHeader() {
        CSVFileWriter writer = new CSVFileWriter("file.csv").header();
        assertTrue(writer.getHeader().length == 0);
    }

    /**
     * Test of newLineSeparator method, of class CSVFileWriter.
     */
    @Test
    public void testNewLineSeparator() {
        CSVFileWriter writer = new CSVFileWriter("file.csv").newLineSeparator("-");
        assertEquals("-", writer.getNewLineSeparator());
        assertEquals(CSVFormat.DEFAULT.withRecordSeparator("-"), writer.getCsvFileFormat());
    }
    @Test
    public void testNullNewLineSeparator() {
        CSVFileWriter writer = new CSVFileWriter("file.csv").newLineSeparator(null);
        assertEquals(CSVFileWriter.NEW_LINE_SEPARATOR, writer.getNewLineSeparator());
        assertEquals(CSVFormat.DEFAULT.withRecordSeparator(CSVFileWriter.NEW_LINE_SEPARATOR), writer.getCsvFileFormat());
    }

    /**
     * Check if the method {@link CSVFileWriter#start() } creates the file and writes the header.
     * @throws java.lang.Exception
     */
    @Test
    public void testStart() throws Exception {
        CSVFileWriter writer = new CSVFileWriter("file.csv").header("Name", "Age");
        writer.start();
        writer.finish();
        
        File file = null;
        BufferedReader reader = null;
        try {
            file = new File("file.csv");
            assertTrue(file.exists());

            reader = new BufferedReader(new FileReader(file));
            String header = reader.readLine();
            assertEquals("Name,Age", header);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if(file != null && file.exists()) {
                file.delete();
            }
        }
        
    }
    

    /**
     * Check if the method {@link CSVFileWriter#printRecord(java.lang.Object...) } writes a line with data of one record.
     * @throws Exception 
     */
    @Test
    public void testPrintRecord() throws Exception {
        CSVFileWriter writer = new CSVFileWriter("file.csv").header("Name", "Age");
        writer.start();
        writer.printRecord("Jazmina", "12");
        writer.finish();
        
        File file = null;
        BufferedReader reader = null;
        try {
            file = new File("file.csv");
            assertTrue(file.exists());

            reader = new BufferedReader(new FileReader(file));
            String header = reader.readLine();
            String record = reader.readLine();
            assertEquals("Name,Age", header);
            assertEquals("Jazmina,12", record);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if(file != null && file.exists()) {
                file.delete();
            }
        }
    }

    
}
