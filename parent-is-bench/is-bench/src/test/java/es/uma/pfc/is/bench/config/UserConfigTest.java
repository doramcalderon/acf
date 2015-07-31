
package es.uma.pfc.is.bench.config;

import static es.uma.pfc.is.bench.config.UserConfig.CONFIG_PATH;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class UserConfigTest {
    @Test
    public void testGet() {
        UserConfig.me = null;
        UserConfig config = UserConfig.get();
        assertNotNull(config.getConfig());
    }
    
    @Test
    public void testInitDefaultWorkspace() throws IOException {
        Properties config = new Properties();
        File configFile = FileUtils.createIfNoExists(UserConfig.CONFIG_PATH);
        config.load(new FileInputStream(configFile));
        String defaultWsConfigured = config.getProperty(UserConfig.DEFAULT_WORKSPACE_PROPERTY, UserConfig.DEFAULT_WORKSPACE_PATH);
        
        UserConfig uc = new UserConfig();
        uc.initDefaultWorkspace();
        assertEquals(defaultWsConfigured, uc.getDefaultWorkspace());
        
    }

    /**
     * Test of getDefaultWorkspace method, of class UserConfig.
     */
    @Test
    public void testGetDefaultWorkspace() {
        String expected = UserConfig.get().getConfig().getProperty(UserConfig.DEFAULT_WORKSPACE_PROPERTY); 
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

    
    /**
     * Test of getAlgorithmsFile method, of class UserConfig.
     */
    @Test
    public void testGetAlgorithmsFile() {
        UserConfig.get().setProperty(UserConfig.DEFAULT_WORKSPACE_PROPERTY, System.getProperty("user.dir") + "/src/test/resources");
        File algorithms = UserConfig.get().getAlgorithmsFile();
        
        assertNotNull(algorithms);
        assertEquals("algorithms.xml", algorithms.getName());
    }

    
}
