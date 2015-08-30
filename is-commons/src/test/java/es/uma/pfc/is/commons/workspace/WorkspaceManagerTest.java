
package es.uma.pfc.is.commons.workspace;

import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class WorkspaceManagerTest {
    
    public WorkspaceManagerTest() {
    }
   

    /**
     * Test of get method, of class WorkspaceManager.
     */
    @Test
    public void testGet() {
    }

    /**
     * Test of create method, of class WorkspaceManager.
     * @throws java.io.IOException
     */
    @Test
    public void testCreate() throws IOException {
        Properties config = getConfig();
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        
        String location = Paths.get(System.getProperty("user.dir"), "target", "myws").toString();
        String name = "My WS";
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setLocation(location);
        
        WorkspaceManager manager = WorkspaceManager.get();
        manager.setConfig(config);
        manager.setConfigPath(configPath);
        manager.create(ws, false);
        
        assertTrue(Files.exists(Paths.get(location)));
        assertTrue(Files.exists(Paths.get(location, WorkspaceManager.DEFAULT_INPUT_PATH)));
        assertTrue(Files.exists(Paths.get(location, WorkspaceManager.DEFAULT_OUTPUT_PATH)));
    }
    /**
     * Test of create method, of class WorkspaceManager.
     */
    @Test
    public void testCreateAndCurrent() {
        Properties config = new Properties();
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        
        String location = Paths.get(System.getProperty("user.dir"), "target", "myws").toString();
        String name = "My WS";
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setLocation(location);
        
        WorkspaceManager manager = new WorkspaceManager();
        manager.setConfig(config);
        manager.setConfigPath(configPath);
        manager.create(ws, true);
        
        assertTrue(Files.exists(Paths.get(location)));
        assertTrue(Files.exists(Paths.get(location, WorkspaceManager.DEFAULT_INPUT_PATH)));
        assertTrue(Files.exists(Paths.get(location, WorkspaceManager.DEFAULT_OUTPUT_PATH)));
        assertEquals(name.replace(" ", ""), config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
    }
      /**
     * Test of saveWorkspace method, of class WorkspaceManager.
     */
    @Test
    public void testSaveWorkspace() throws Exception {
        Properties config = getConfig();
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
            
        String location = Paths.get(System.getProperty("user.dir"), "target", "myws").toString();
        String name = "MyWS";
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setLocation(location);
        
        WorkspaceManager manager = WorkspaceManager.get();
        manager.setConfig(config);
        manager.setConfigPath(configPath);
        manager.saveWorkspace(ws, false);
        
        assertEquals(location, config.get( name));
        assertEquals(name, config.getProperty(WorkspaceManager.WORKSPACE_CHANGE));
        assertFalse(name.equals(config.getProperty(WorkspaceManager.CURRENT_WORKSPACE)));
        
    }
    @Test
    public void testSaveWorkspaceAndSetCurrent() throws Exception {
        Properties config = getConfig();
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        
        String location = Paths.get(System.getProperty("user.dir"), "target", "myws").toString();
        String name = "MyWS";
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setLocation(location);
        
        WorkspaceManager manager = WorkspaceManager.get();
        manager.setConfig(config);
        manager.setConfigPath(configPath);
        manager.saveWorkspace(ws, true);
        
        assertEquals(location, config.get(name));
        assertEquals(name, config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
        
    }
    
    @Test
    public void testChange() {
        Properties config = new Properties();
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        
        Workspace ws = new Workspace();
        ws.setName("newWs");
        
        WorkspaceManager manager = WorkspaceManager.get();
        manager.setConfig(config);
        manager.change(ws);
        
        assertEquals("default", config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
        assertEquals( ws.getName(), config.getProperty(WorkspaceManager.WORKSPACE_CHANGE));
    }

    /**
     * Test of commitChange method, of class WorkspaceManager.
     * @throws java.io.IOException
     */
    @Test
    public void testCommitChange() throws IOException {
        Properties config = new Properties();
            
        String location = Paths.get(System.getProperty("user.dir"), "target", "myws").toString();
        String name = "MyWS";
        
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty( name, location);
        config.setProperty(WorkspaceManager.WORKSPACE_CHANGE ,  name);
        
        WorkspaceManager manager = WorkspaceManager.get();
        manager.setConfig(config);
        manager.commitChange();
        
        assertEquals( name, config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
        assertNull(config.getProperty(WorkspaceManager.WORKSPACE_CHANGE));
    }

    /**
     * Test of currentWorkspace method, of class WorkspaceManager.
     */
    @Test
    public void testCurrentWorkspace() throws IOException {
        String location = Paths.get(System.getProperty("user.dir"), "target", "default").toString();
        FileUtils.createDirIfNoExists(location);
        
        Properties config = new Properties();
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty("default", location);
        WorkspaceManager manager = new WorkspaceManager();
        manager.setConfig(config);
        
        Workspace current = manager.currentWorkspace();
        
        assertNotNull(current);
        assertEquals("default", current.getName());
    }

    @Test
    public void testRegisteredWorkspaces() {
        Properties config = new Properties();
        config.setProperty("default", "C:\\isbench\\default");
        config.setProperty("myws", "C:\\isbench\\myws");
        config.setProperty("workspace.current", "default");
        config.setProperty("workspace.change", "myws");
        
        WorkspaceManager wsManager = new WorkspaceManager();
        wsManager.setConfig(config);
        List<Workspace> wss = wsManager.registeredWorkspaces();
        
        assertNotNull(wss);
        assertEquals(2, wss.size());
        assertTrue(wss.stream().anyMatch(ws -> "default".equals(ws.getName())));
        assertTrue(wss.stream().anyMatch(ws -> "myws".equals(ws.getName())));
        
    }
    /**
     * Test of addPreference method, of class WorkspaceManager.
     */
    @Test
    public void testAddPreference() {
    }

    /**
     * Test of savePreferences method, of class WorkspaceManager.
     */
    @Test
    public void testSavePreferences() throws Exception {
    }

    /**
     * Test of createDefaultPreferences method, of class WorkspaceManager.
     */
    @Test
    public void testCreateDefaultPreferences() {
        Locale lc = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        Preferences prefs = new WorkspaceManager().get().createDefaultPreferences();
        
        assertNotNull(prefs);
        assertEquals(lc.toString(), prefs.getPreference(Preferences.LANGUAGE));
    }
  
    private Properties getConfig() throws IOException {
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        // clear the config file
        File configFile = FileUtils.createIfNoExists(configPath);
        try(FileWriter rw = new FileWriter(configFile)) {
            rw.write("");
            rw.flush();
        }
        
        Properties config = new Properties();
        config.load(new FileReader(configPath));
        return config;
}
  
}
