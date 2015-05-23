
package es.uma.pfc.is.algorithms;


import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.InvalidKeyException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de {@link AlgorithmOptions}
 * @author Dora Calderón
 */
public class AlgorithmOptionsTest {
    
    public AlgorithmOptionsTest() {
    }

    /**
     * Test of addOption method, of class AlgorithmOptions.
     */
    @Test
    public void testAddOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", 1);
        
        assertEquals(new Integer(1), algOptions.<Integer>getOption("config"));
    }
    /**
     * Test of addOption method, of class AlgorithmOptions.
     */
    @Test
    public void testAddNullOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", null);
        
        assertNull(algOptions.<Integer>getOption("config"));
    }
    /**
     * Test of addOption method, of class AlgorithmOptions.
     */
    @Test(expected = InvalidKeyException.class)
    public void testAddOptionNullKey() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption(null, 1);
        fail("Se esperaba NullPointerException");
    }

    /**
     * Test of addOption method, of class AlgorithmOptions.
     */
    @Test
    public void testGetOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", 1);
        int value = algOptions.<Integer>getOption("config");
        assertEquals(1, value);
    }

    /**
     * Test of removeOption method, of class AlgorithmOptions.
     */
    @Test
    public void testRemoveOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", 1);
        algOptions.addOption("config2", 2);
        
        algOptions.removeOption("config");
        
        assertEquals(1, algOptions.optionsSize());
    }

    /**
     * Test of removeOption method, of class AlgorithmOptions.
     */
    @Test
    public void testRemoveLastOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", 1);
        
        algOptions.removeOption("config");
        
        assertEquals(0, algOptions.optionsSize());
    }

    /**
     * Test of removeOption method, of class AlgorithmOptions.
     */
    @Test
    public void testRemoveNoExistOption() {
        AlgorithmOptions algOptions = new AlgorithmOptions();
        algOptions.addOption("config", 1);
        
        algOptions.removeOption("a");
        
        assertEquals(1, algOptions.optionsSize());
    }

    /**
     * Test of enable method, of class AlgorithmOptions.
     */
    @Test
    public void testEnable() {
        AlgorithmOptions options = new AlgorithmOptions();
        options.enable(Mode.HISTORY);
        assertEquals(Boolean.TRUE, options.getOption(Mode.HISTORY.toString()));
    }

    /**
     * Test of disable method, of class AlgorithmOptions.
     */
    @Test
    public void testDisable() {
         AlgorithmOptions options = new AlgorithmOptions();
        options.disable(Mode.HISTORY);
        assertEquals(Boolean.FALSE, options.getOption(Mode.HISTORY.toString()));
    }

    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test
    public void testIsEnabled() {
    }
    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test(expected = InvalidKeyException.class)
    public void testCheckMode_NullMode() {
        new AlgorithmOptions().checkMode(null);
        fail("Se esperaba InvalidKeyException");
    }
    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test
    public void testCheckMode() {
        new AlgorithmOptions().checkMode(Mode.PERFORMANCE);
        assertTrue(true);
    }
    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test(expected = InvalidKeyException.class)
    public void testCheckKey_NullKey() {
        new AlgorithmOptions().checkKey(null);
        fail("Se esperaba InvalidKeyException");
    }
    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test(expected = InvalidKeyException.class)
    public void testCheckKey_EmptyKey() {
        new AlgorithmOptions().checkKey("");
        fail("Se esperaba InvalidKeyException");
    }
    /**
     * Test of isEnabled method, of class AlgorithmOptions.
     */
    @Test
    public void testCheckKey() {
        new AlgorithmOptions().checkKey("key");
        assertTrue(true);
    }
    
    
}
