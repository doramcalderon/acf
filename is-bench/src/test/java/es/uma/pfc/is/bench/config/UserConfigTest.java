
package es.uma.pfc.is.bench.config;

import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class UserConfigTest {
    @Test
    public void testGet() {
        UserConfig config = UserConfig.get();
        assertNotNull(config.getConfig());
        assertEquals(Paths.get(UserConfig.DEFAULT_WORKSPACE_PATH).toString(), config.getDefaultWorkspace());
    }

    /**
     * Test of getDefaultWorkspace method, of class UserConfig.
     */
    @Test
    public void testGetDefaultWorkspace() {
        String expected = UserConfig.get().getConfig().getProperty(UserConfig.DEFAULT_WORKSPACE); 
        assertEquals(expected, UserConfig.get().getDefaultWorkspace());
    }

    /**
     * Test of getDefaultWorkspace method, of class UserConfig.
     */
    @Test
    public void testSetDefaultWorkspace() {
        String defaultWs = "C:/.isbench/default";
        UserConfig.get().setDefaultWorkspace(defaultWs);
        assertEquals(defaultWs, UserConfig.get().getDefaultWorkspace());
    }

    /**
     * Test of setProperty method, of class UserConfig.
     */
    @Test
    public void testSetProperty() {
    }

    /**
     * Test of getProperty method, of class UserConfig.
     */
    @Test
    public void testGetProperty() {
    }
    
}
