
package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.io.PrintStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Dora Calderón
 */
public class ModeStreamsTest {
    
    public ModeStreamsTest() {
    }
 /**
     * Test of addOutput method, of class ISBenchLogger.
     */
    @Test
    public void testAddOutput() {
        ModeStreams modeStreams = new ModeStreams();
        OutputStream output = mock(OutputStream.class);
        
        modeStreams.add(Mode.PERFORMANCE, output);
        
        List<PrintStream> outputs = modeStreams.getMap().get(Mode.PERFORMANCE);
        assertNotNull(outputs);
        assertEquals(1, outputs.size());
    }

    /**
     * Test of addOutput method, of class ISBenchLogger.
     */
    @Test
    public void testAddNullOutput() {
        ModeStreams modeStreams = new ModeStreams();     
        modeStreams.add(Mode.PERFORMANCE, null);
        
        List<PrintStream> outputs = modeStreams.getMap().get(Mode.PERFORMANCE);
        assertNull(outputs);
    }
    
     /**
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs() {
        ModeStreams modeStreams = new ModeStreams();     
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(mock(OutputStream.class));
        
        modeStreams.addAll(Mode.HISTORY, outputs);
        
        List<PrintStream> traceOutputs = modeStreams.getMap().get(Mode.HISTORY);
        assertNotNull(traceOutputs);
        assertEquals(2, traceOutputs.size());
    }
    /**
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs_WithNullOutputs() {
        ModeStreams modeStreams = new ModeStreams();     
        
        modeStreams.addAll(Mode.HISTORY, null);
        List<PrintStream> traceOutputs = modeStreams.getMap().get(Mode.HISTORY);
        assertNull(traceOutputs);
    }
    /**
     * Test of addAllOutputs method, of class ISBenchLogger.
     */
    @Test
    public void testAddAllOutputs_WithANullOutput() {
        ModeStreams modeStreams = new ModeStreams();     
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(null);
        
        modeStreams.addAll(Mode.HISTORY, outputs);
        
        List<PrintStream> traceOutputs = modeStreams.getMap().get(Mode.HISTORY);
        assertNotNull(traceOutputs);
        assertEquals(1, traceOutputs.size());
    }

    /**
     * Test of removeOutput method, of class ISBenchLogger.
     */
    @Test
    public void testRemoveOutput() {
        ModeStreams modeStreams = new ModeStreams();     
        PrintStream o = mock(PrintStream.class);
        modeStreams.add(Mode.STATISTICS, o);
        modeStreams.add(Mode.STATISTICS, mock(PrintStream.class));
        
        modeStreams.remove(Mode.STATISTICS, o);
        
        List<PrintStream> statOutputs = modeStreams.getMap().get(Mode.STATISTICS);
        assertNotNull(statOutputs);
        assertEquals(1, statOutputs.size());
    }

    /**
     * Test of removeOutput method, of class ISBenchLogger.
     */
    @Test
    public void testRemoveNullOutput() {
        ModeStreams modeStreams = new ModeStreams();     
        PrintStream o = mock(PrintStream.class);
        modeStreams.add(Mode.PERFORMANCE, o);
        modeStreams.add(Mode.PERFORMANCE, mock(PrintStream.class));
        
        modeStreams.remove(Mode.PERFORMANCE, null);
        
        List<PrintStream> performanceOutputs = modeStreams.getMap().get(Mode.PERFORMANCE);
        assertNotNull(performanceOutputs);
        assertEquals(2, performanceOutputs.size());
    }

    /**
     * Test of removeAllOutputs method, of class ISBenchLogger.
     */
    @Test @Ignore
    public void testRemoveAllOutputs() {
        ModeStreams modeStreams = new ModeStreams();
        List<OutputStream> outputs = new ArrayList();
        outputs.add(mock(OutputStream.class));
        outputs.add(mock(OutputStream.class));
        modeStreams.addAll(Mode.PERFORMANCE, outputs);
        
        modeStreams.removeAll(Mode.PERFORMANCE, outputs);
        
        List<PrintStream> performanceOutputs = modeStreams.getMap().get(Mode.PERFORMANCE);
        assertNotNull(performanceOutputs);
        assertEquals(0, performanceOutputs.size());
    }
}
