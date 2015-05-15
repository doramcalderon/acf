
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 *
 * @author Dora Calderón
 */
public class ISBenchLoggerTest {
    
    public ISBenchLoggerTest() {
    }

    /**
     * Test of isPerformanceEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsPerformanceEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertTrue(logger.isPerformanceEnabled());
        
    }
    /**
     * Test of isPerformanceEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsPerformanceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.PERFORMANCE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertFalse(logger.isPerformanceEnabled());
        
    }

    /**
     * Test of isStatisticsEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsStatisticsEnabled() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertTrue(logger.isStatisticsEnabled());
    }

    /**
     * Test of isStatisticsEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsStatisticsNotEnabled() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertFalse(logger.isStatisticsEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsHistoryEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.TRACE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertTrue(logger.isHistoryEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsTraceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.TRACE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertFalse(logger.isHistoryEnabled());
    }

    /**
     * Test of startTime method, of class ISBenchLogger.
     */
//    @Test
//    public void testStartTime() {
//        AlgorithmOptions options = new AlgorithmOptions();
//        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
//        ISBenchLogger logger = new ISBenchLogger(options);
//        
//        logger.startTime(new Date(System.currentTimeMillis()));
//        logger.endTime(new Date(System.currentTimeMillis()));
//        // TODO guardar la consola en un fichero
//    }

    /**
     * Test of endTime method, of class ISBenchLogger.
     */
    @Test
    public void testEndTime() {
    }

    /**
     * Test of setOptions method, of class ISBenchLogger.
     */
    @Test
    public void testSetOptions() {
    }

    /**
     * Test of getMessage method, of class ISBenchLogger.
     */
    @Test
    public void testGetMessage() {
    }

   

    /**
     * Test of writeMessage method, of class ISBenchLogger.
     */
    @Test
    public void testWriteMessage_String() {
    }

    /**
     * Test of writeMessage method, of class ISBenchLogger.
     */
    @Test
    public void testWriteMessage_String_OutputStream() {
    }

    /**
     * Test of startTime method, of class ISBenchLogger.
     */
    @Test
    public void testStartTime() {
    }

   

    /**
     * Test of getOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testGetOutputs() {
    }

    /**
     * Test of clearOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testClearOutputs() {
    }

    /**
     * Test of initOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testInitOutputs() {
    }

    /**
     * Comprueba que se escribe una línea en el archivo history.txt y en ninguno más.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testHistory() throws FileNotFoundException, IOException {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.TRACE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        logger.history("History message {}", 1);
        
        File log = new File("history.txt");
        assertTrue(log.exists());
        
        BufferedReader reader = new BufferedReader(new FileReader(log));
        String line = reader.readLine();
        assertNotNull(line);
        assertTrue(line.length() > 0);
        reader.close();
        
        log = new File("stats.csv");
        assertTrue(log.exists());
        reader = new BufferedReader(new FileReader(log));
        int i = reader.read();
        assertEquals(-1, i);
        reader.close();
    }

    /**
     * Test of statistics method, of class ISBenchLogger.
     */
    @Test
    public void testStatistics() {
    }
    
}
