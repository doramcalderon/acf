
package es.uma.pfc.is.commons.io;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.util.Arrays;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Dora Calderón
 */
public class ImplicationalSystemReaderPrologTest {
    
    public ImplicationalSystemReaderPrologTest() {
    }

    /**
     * Test of read method, of class ImplicationalSystemReaderProlog.
     */
    @Test
    public void testRead() throws Exception {
    }

    /**
     * Test of parseImplications method, of class ImplicationalSystemReaderProlog.
     */
    @Test
    public void testParseImplication() throws Exception {
        String implication = "implication([3],[0,2]).";
        TreeSet<Comparable> premise = new TreeSet<>(Arrays.asList("3"));
        TreeSet<Comparable> conclusion = new TreeSet<>(Arrays.asList("0", "2"));
        Rule rule = new Rule(premise, conclusion);
        
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        ImplicationalSystem system = new ImplicationalSystem();
        
        readerPl.parseImplication(system, implication);
        
        assertNotNull(system);
        assertNotNull(system.getRules());
        assertEquals(1, system.getRules().size());
        assertTrue(system.getRules().contains(rule));
    }

    /**
     * Test of parseImplicationPart method, of class ImplicationalSystemReaderProlog.
     */
    @Test
    public void testParseImplicationPartOneElement() {
        String part = "[3]";
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        TreeSet<Comparable> partSet = readerPl.parseImplicationPart(part);
        
        assertNotNull(partSet);
        assertFalse(partSet.isEmpty());
        assertTrue(partSet.contains("3"));
        
    }
    @Test
    public void testParseImplicationPartEmpty() {
        String part = "[]";
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        TreeSet<Comparable> partSet = readerPl.parseImplicationPart(part);
        
        assertNotNull(partSet);
        assertTrue(partSet.isEmpty());
    }
    @Test
    public void testParseImplicationPart() {
        String part = "[1,4]";
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        TreeSet<Comparable> partSet = readerPl.parseImplicationPart(part);
        
        assertNotNull(partSet);
        assertFalse(partSet.isEmpty());
        assertTrue(partSet.contains("1"));
        assertTrue(partSet.contains("4"));
    }

    /**
     * Test of parseAttributes method, of class ImplicationalSystemReaderProlog.
     */
    @Test
    public void testParseAttributes() throws Exception {
        String line = "attributes([0,1,2,3,4]).";
        ImplicationalSystem system = new ImplicationalSystem();
        
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        readerPl.parseAttributes(system, line);
        
        assertNotNull(system);
        assertEquals(5, system.getSet().size());
        assertTrue(system.getSet().contains("0"));
        assertTrue(system.getSet().contains("1"));
        assertTrue(system.getSet().contains("2"));
        assertTrue(system.getSet().contains("3"));
        assertTrue(system.getSet().contains("4"));
    }
    @Test
    public void testParseEmptyAttributes() throws Exception {
        String line = "attributes([]).";
        ImplicationalSystem system = new ImplicationalSystem();
        
        ImplicationalSystemReaderProlog readerPl = new ImplicationalSystemReaderProlog();
        readerPl.parseAttributes(system, line);
        
        assertNotNull(system);
        assertEquals(0, system.getSet().size());
    }
    
}
