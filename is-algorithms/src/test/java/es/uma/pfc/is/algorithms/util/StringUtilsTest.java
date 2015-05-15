
package es.uma.pfc.is.algorithms.util;

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
    
}
