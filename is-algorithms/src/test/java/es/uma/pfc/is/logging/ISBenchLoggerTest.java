
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
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
     * Test of isTraceEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsTraceEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(AlgorithmOptions.Mode.TRACE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertTrue(logger.isTraceEnabled());
    }

    /**
     * Test of isTraceEnabled method, of class ISBenchLogger.
     */
    @Test
    public void testIsTraceNotEnabled() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.disable(AlgorithmOptions.Mode.TRACE);
        ISBenchLogger logger = new ISBenchLogger(options);
        
        assertFalse(logger.isTraceEnabled());
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
     * Test of addOutput method, of class ISBenchLogger.
     */
    @Test
    public void testAddOutput() {
        ISBenchLogger logger = new ISBenchLogger();
        OutputStream output = mock(OutputStream.class);
        
        logger.addOutput(output);
        
        assertNotNull(logger.getOutputs());
        assertEquals(1, logger.getOutputs().size());
    }

    /**
     * Test of addOutput method, of class ISBenchLogger.
     */
    @Test
    public void testAddNullOutput() {
        ISBenchLogger logger = new ISBenchLogger();
                
        logger.addOutput(null);
        
        assertNotNull(logger.getOutputs());
        assertTrue(logger.getOutputs().isEmpty());
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
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs() {
        ISBenchLogger logger = new ISBenchLogger();
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(mock(OutputStream.class));
        
        logger.addAllOutputs(outputs);
        
        assertNotNull(logger.getOutputs());
        assertEquals(2, logger.getOutputs().size());
    }
    /**
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs_WithNullOutputs() {
        ISBenchLogger logger = new ISBenchLogger();
        
        logger.addAllOutputs(null);
        
        assertNotNull(logger.getOutputs());
        assertEquals(0, logger.getOutputs().size());
    }
    /**
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs_WithANullOutput() {
        ISBenchLogger logger = new ISBenchLogger();
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(null);
        
        logger.addAllOutputs(outputs);
        
        assertNotNull(logger.getOutputs());
        assertEquals(1, logger.getOutputs().size());
    }

    /**
     * Test of removeOutput method, of class ISBenchLogger.
     */
    @Test
    public void testRemoveOutput() {
        ISBenchLogger logger = new ISBenchLogger();
        OutputStream o = mock(OutputStream.class);
        logger.addOutput(o);
        logger.addOutput(mock(OutputStream.class));
        
        logger.removeOutput(o);
        
        assertNotNull(logger.getOutputs());
        assertEquals(1, logger.getOutputs().size());
    }

    /**
     * Test of removeOutput method, of class ISBenchLogger.
     */
    @Test
    public void testRemoveNullOutput() {
        ISBenchLogger logger = new ISBenchLogger();
        OutputStream o = mock(OutputStream.class);
        logger.addOutput(o);
        logger.addOutput(mock(OutputStream.class));
        
        logger.removeOutput(null);
        
        assertNotNull(logger.getOutputs());
        assertEquals(2, logger.getOutputs().size());
    }

    /**
     * Test of removeAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testRemoveAllOutputs() {
        ISBenchLogger logger = new ISBenchLogger();
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(mock(OutputStream.class));
        logger.addAllOutputs(outputs);
        
        logger.removeAllOutputs(outputs);
        
        assertNotNull(logger.getOutputs());
        assertEquals(0, logger.getOutputs().size());
    }

    /**
     * Test of getOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testGetOutputs() {
    }
    
}
