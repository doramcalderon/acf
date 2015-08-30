
package es.uma.pfc.is.bench.config;

import static es.uma.pfc.is.bench.config.ConfigManager.CONFIG_PATH;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Dora Calderón
 */
@Ignore
public class UserConfigTest {
    @Test
    public void testGet() {
        ConfigManager.me = null;
        ConfigManager config = ConfigManager.get();
        assertNotNull(config.getConfig());
    }
    
    @Test
    public void testInitDefaultWorkspace() throws IOException {
        Properties config = new Properties();
        File configFile = FileUtils.createIfNoExists(ConfigManager.CONFIG_PATH);
        config.load(new FileInputStream(configFile));
        String defaultWsConfigured = config.getProperty(ConfigManager.DEFAULT_WORKSPACE_PROPERTY, ConfigManager.DEFAULT_WORKSPACE_PATH);
        
        ConfigManager uc = new ConfigManager();
        uc.initDefaultWorkspace();
        assertEquals(defaultWsConfigured, uc.getDefaultWorkspace());
        
    }

    /**
     * Test of getDefaultWorkspace method, of class ConfigManager.
     */
    @Test
    public void testGetDefaultWorkspace() {
        String expected = ConfigManager.get().getConfig().getProperty(ConfigManager.DEFAULT_WORKSPACE_PROPERTY); 
        assertEquals(expected, ConfigManager.get().getDefaultWorkspace());
    }

    /**
     * Test of getDefaultWorkspace method, of class ConfigManager.
     */
    @Test
    public void testSetDefaultWorkspace() {
        String defaultWs = "C:/.isbench/default";
        ConfigManager.get().setDefaultWorkspace(defaultWs);
        assertEquals(defaultWs, ConfigManager.get().getDefaultWorkspace());
    }

    /**
     * Test of setProperty method, of class ConfigManager.
     */
    @Test
    public void testSetProperty() {
    }

    /**
     * Test of getProperty method, of class ConfigManager.
     */
    @Test
    public void testGetProperty() {
    }

    
}
