
package es.uma.pfc.is.algorithms.util;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de {@link SystemProperties}.
 * @author Dora Calderón
 */
public class SystemPropertiesTest {
    
    public SystemPropertiesTest() {
    }

    @After
    public void after() {
        System.clearProperty(SystemProperties.PERFORMANCE_FILE);
        System.clearProperty(SystemProperties.STATISTICS_FILE);
        System.clearProperty(SystemProperties.TRACE_FILE);
    }
    
    /**
     * Test of setOutputFile method, of class SystemProperties.
     */
//    @Test
    public void testSetOutputPerformanceFile() {
        SystemProperties.setOutputFile(Mode.HISTORY, "C:\\", "performance.log");
        String value = System.getProperty(SystemProperties.PERFORMANCE_FILE);
        assertEquals("C:\\performance.log", value);
    }
    /**
     * Test of setOutputFile method, of class SystemProperties.
     */
//    @Test
    public void testSetOutputStatisticsFile() {
        SystemProperties.setOutputFile(Mode.HISTORY, "C:\\", "stat.log");
        String value = System.getProperty(SystemProperties.STATISTICS_FILE);
        assertEquals("C:\\stat.log", value);
    }
    /**
     * Test of setOutputFile method, of class SystemProperties.
     */
    @Test
    public void testSetOutputTraceFile() {
        SystemProperties.setOutputFile(Mode.HISTORY, "C:\\", "trace.log");
        String value = System.getProperty(SystemProperties.TRACE_FILE);
        assertEquals("C:\\trace.log", value);
    }
    
}
