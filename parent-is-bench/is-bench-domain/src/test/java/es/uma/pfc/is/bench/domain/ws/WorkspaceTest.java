
package es.uma.pfc.is.bench.domain.ws;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class WorkspaceTest {
    

    /**
     * Test of setName method, of class Workspace.
     */
    @Test
    public void testSetName() {
        String name = "MyWS";
        Workspace ws = new Workspace();
        ws.setName(name);
        assertEquals("MyWS", ws.getName());
    }
    /**
     * Test of setName method, of class Workspace.
     */
    @Test
    public void testSetNameWithBlanks() {
        String name = "My WS";
        Workspace ws = new Workspace();
        ws.setName(name);
        assertEquals("MyWS", ws.getName());
    }

    
}
