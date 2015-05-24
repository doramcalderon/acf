
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.Messages;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_INIT;
import es.uma.pfc.is.algorithms.util.StringUtils;
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

/**
 *
 * @author Dora Calderón
 */
public class ISBenchLoggerTest {
    
    public ISBenchLoggerTest() {
    }
    
    @After
   public void tearDown() throws IOException {
       Files.deleteIfExists(Paths.get("output_history.txt"));
   }


    /**
     * Test of isPerformanceEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsPerformanceEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        ISBenchLogger logger = new ISBenchLogger(options);
        logger.freeResources();
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
        logger.freeResources();
        
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
        logger.freeResources();
        
        assertTrue(logger.isStatisticsEnabled());
    }

    /**
     * Test of isStatisticsEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsStatisticsNotEnabled() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        
        ISBenchLogger logger = new ISBenchLogger(options);
        logger.freeResources();
        
        assertFalse(logger.isStatisticsEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsHistoryEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.HISTORY);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        ISBenchLogger logger = new ISBenchLogger(options);
        logger.freeResources();
        assertTrue(logger.isHistoryEnabled());
    }

    /**
     * Test of isHistoryEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsTraceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.HISTORY);
        ISBenchLogger logger = new ISBenchLogger(options);
        logger.freeResources();
        assertFalse(logger.isHistoryEnabled());
    }

    /**
     * Test of startTime method, of class ISBenchLogger.
     */
    @Test
    public void testStartTime() throws FileNotFoundException, IOException {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.PERFORMANCE);
        options.addOption(Options.OUTPUT.toString(), "output.txt");
        ISBenchLogger logger = new ISBenchLogger(options);
        Date start = new Date(System.currentTimeMillis());
        logger.startTime(start);
        Date end = new Date(System.currentTimeMillis());
        logger.endTime(end);
        logger.freeResources();
        
        String expectedLine =  StringUtils.replaceArgs(logger.getMessage(PERFORMANCE_INIT), "{}", logger.getDf().format(start));
        File history = null;
        BufferedReader reader = null;
        try {
            history = new File("output_history.txt");
            assertTrue(history.exists());

            reader = new BufferedReader(new FileReader(history));
            String line = reader.readLine();
            assertEquals(expectedLine, line);
            
            expectedLine =  StringUtils.replaceArgs(logger.getMessage(PERFORMANCE_END), "{}", logger.getDf().format(end));
            line = reader.readLine();
            assertEquals(expectedLine, line);
            
            expectedLine =  StringUtils.replaceArgs(logger.getMessage(Messages.PERFORMANCE_TOTAL), "{}", (end.getTime() - start.getTime()));
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
        options.enable(AlgorithmOptions.Mode.HISTORY);
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        options.addOption(Options.OUTPUT.toString(), "output.log");
        ISBenchLogger logger = new ISBenchLogger(options);
        
        logger.history("History message {}", 1);
        logger.freeResources();
        
        File history = null;
        BufferedReader reader = null;
        try {
            history = new File("output_history.txt");
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
     * Test of statistics method, of class ISBenchLogger.
     */
    @Test
    public void testStatistics() throws IOException {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.STATISTICS);
        
        ISBenchLogger logger = new ISBenchLogger(options);
        String header = "Rule1,Rule,Old size,New Size";
        logger.createStatisticLog("output", "Rule1", "Rule", "Old size", "New Size");
        
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
