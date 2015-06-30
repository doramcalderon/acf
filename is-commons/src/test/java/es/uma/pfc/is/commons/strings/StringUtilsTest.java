
package es.uma.pfc.is.commons.strings;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class StringUtilsTest {
    

    /**
     * Test of replaceArgs method, of class StringUtils.
     */
    @Test
    public void testReplaceArgs() {
        String message ="Hola {} {} !";
        String token = "{}";
        String messageExpected = "Hola mundo hola !";
        
        String messageReplaced = StringUtils.replaceArgs(message, token, "mundo", "hola");
        
       assertEquals(messageExpected, messageReplaced);
    }

    /**
     * Test of isEmpty method, of class StringUtils.
     */
    @Test
    public void testIsEmpty() {
    }

    /**
     * Test of trim method, of class StringUtils.
     */
    @Test
    public void testTrim() {
    }

    /**
     * Test of containsIgnoreCase method, of class StringUtils.
     */
    @Test
    public void testContainsIgnoreCase() {
        String str = "UnoDos";
        String substr = "nodos";
        assertTrue(StringUtils.containsIgnoreCase(str, substr));
    }
    @Test
    public void testNoContainsIgnoreCase() {
        String str = "UnoDos";
        String substr = "Rosa";
        assertFalse(StringUtils.containsIgnoreCase(str, substr));
    }
    @Test
    public void testContainsIgnoreCase_Prefix() {
        String str = "UnoDos";
        String substr = "uno";
        assertTrue(StringUtils.containsIgnoreCase(str, substr));
    }
    @Test
    public void testContainsIgnoreCase_Sufix() {
        String str = "UnoDos";
        String substr = "dos";
        assertTrue(StringUtils.containsIgnoreCase(str, substr));
    }
    @Test
    public void testContainsIgnoreCase_NullString() {
        String str = null;
        String substr = "dos";
        assertFalse(StringUtils.containsIgnoreCase(str, substr));
    }
    @Test
    public void testContainsIgnoreCase_EmptyString() {
        String str = "";
        String substr = "dos";
        assertFalse(StringUtils.containsIgnoreCase(str, substr));
    }
    
}
