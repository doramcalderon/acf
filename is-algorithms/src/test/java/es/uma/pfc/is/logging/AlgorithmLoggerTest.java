
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.AlgMessages;
import static es.uma.pfc.is.algorithms.AlgMessages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.AlgMessages.PERFORMANCE_INIT;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmLoggerTest {
    
    public AlgorithmLoggerTest() {
    }
    
    @After
   public void tearDown() throws IOException {
       Files.deleteIfExists(Paths.get("output_history.txt"));
   }


    /**
     * Test of isPerformanceEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsPerformanceEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        assertTrue(logger.isPerformanceEnabled());
        
        
    }
    /**
     * Test of isPerformanceEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsPerformanceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.PERFORMANCE);
        
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        
        assertFalse(logger.isPerformanceEnabled());
        
    }

    /**
     * Test of isStatisticsEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsStatisticsEnabled() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        
        assertTrue(logger.isStatisticsEnabled());
    }

    /**
     * Test of isStatisticsEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsStatisticsNotEnabled() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        
        assertFalse(logger.isStatisticsEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsHistoryEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.HISTORY);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        assertTrue(logger.isHistoryEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class AlgorithmLogger.
     */
    @Test
    public void testIsTraceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.HISTORY);
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.freeResources();
        assertFalse(logger.isHistoryEnabled());
    }

    /**
     * Test of startTime method, of class AlgorithmLogger.
     * @throws java.io.IOException
     */
    @Test
    public void testStartTime() throws IOException {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.configure("testlogback.xml");
        Date start = new Date(System.currentTimeMillis());
        logger.startTime();
        Date end = new Date(System.currentTimeMillis());
        logger.endTime();
        logger.freeResources();
        
        String expectedLine =  AlgMessages.get().getMessage(PERFORMANCE_INIT, logger.getDf().format(start));
        File history = null;
        BufferedReader reader = null;
        try {
            history = new File("output_history.log");
            assertTrue(history.exists());

            reader = new BufferedReader(new FileReader(history));
            String line = reader.readLine();
            assertEquals(expectedLine, line);
            
            expectedLine =  AlgMessages.get().getMessage(PERFORMANCE_END, logger.getDf().format(end));
            line = reader.readLine();
            assertEquals(expectedLine, line);
            
            expectedLine =  AlgMessages.get().getMessage(AlgMessages.PERFORMANCE_TOTAL, (end.getTime() - start.getTime()));
            line = reader.readLine();
            assertEquals(expectedLine, line);
            
            reader.close();

        } finally {
            if (reader != null) {
                reader.close();
            }
            if(history != null && history.exists()) {
                history.deleteOnExit();
            }            
        }
    }

    /**
     * Test of endTime method, of class AlgorithmLogger.
     */
    @Test
    public void testEndTime() {
    }

    /**
     * Test of setOptions method, of class AlgorithmLogger.
     */
    @Test
    public void testSetOptions() {
    }

    /**
     * Test of getMessage method, of class AlgorithmLogger.
     */
    @Test
    public void testGetMessage() {
    }

   

    /**
     * Test of writeMessage method, of class AlgorithmLogger.
     */
    @Test
    public void testWriteMessage_String() {
    }

    /**
     * Test of writeMessage method, of class AlgorithmLogger.
     */
    @Test
    public void testWriteMessage_String_OutputStream() {
    }


   

    /**
     * Test of getOutputs method, of class AlgorithmLogger.
     */
    @Test
    public void testGetOutputs() {
    }

    /**
     * Test of clearOutputs method, of class AlgorithmLogger.
     */
    @Test
    public void testClearOutputs() {
    }

    /**
     * Test of initOutputs method, of class AlgorithmLogger.
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
        options.enable(AlgorithmOptions.Mode.HISTORY);
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        options.addOption(Options.OUTPUT.toString(), "output.log");
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.configure("testlogback.xml");
        
        logger.history("History message {}", 1);
        logger.freeResources();
        
        File history = null;
        BufferedReader reader = null;
        try {
            history = new File("output_history.log");
            assertTrue(history.exists());

            reader = new BufferedReader(new FileReader(history));
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.length() > 0);
            reader.close();

        } finally {
            if (reader != null) {
                reader.close();
            }
            if(history != null && history.exists()) {
                history.deleteOnExit();
            }            
        }
    }

    /**
     * Test of statistics method, of class AlgorithmLogger.
     */
    @Test
    public void testStatistics() throws IOException {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        
        AlgorithmLogger logger = new AlgorithmLogger("alg", options);
        logger.configure("testlogback.xml");
        String header = "rule,old size,new size";
        
        logger.statistics("a->b","b->a","3","2");
        logger.freeResources();
        File stats = null;
        BufferedReader reader = null;
        try {
            stats = new File("output.csv");
            assertTrue(stats.exists());
            reader = new BufferedReader(new FileReader(stats));
            
            String line = reader.readLine();
            assertEquals(header, line);
            line = reader.readLine();
            assertEquals("a->b,b->a,3,2", line);
        } finally {
            if (reader != null) {
                reader.close();
            }

            if(stats != null && stats.exists()) {
                stats.deleteOnExit();
            }
            
        }
    }
    
}
