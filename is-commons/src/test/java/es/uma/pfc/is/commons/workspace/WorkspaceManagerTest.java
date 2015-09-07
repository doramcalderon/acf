
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
        
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
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
        
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
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
        
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
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
        
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
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
        
        WorkspaceManager manager = new WorkspaceManager(null, config);
        manager.change(ws);
        
        assertEquals("default", config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
        assertEquals( ws.getName(), config.getProperty(WorkspaceManager.WORKSPACE_CHANGE));
    }

    /**
     * Test of commitPendingChanges method, of class WorkspaceManager.
     * @throws java.io.IOException
     */
    @Test
    public void testCommitChange() throws IOException {
        Properties config = new Properties();
            
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        String location = Paths.get(System.getProperty("user.dir"), "target", "isbench", "myws").toString();
        String name = "MyWS";
        
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty( name, location);
        config.setProperty(WorkspaceManager.WORKSPACE_CHANGE ,  name);
        
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
        manager.commitPendingChanges();
        
        assertEquals( name, config.getProperty(WorkspaceManager.CURRENT_WORKSPACE));
        assertNull(config.getProperty(WorkspaceManager.WORKSPACE_CHANGE));
    }

    /**
     * Test of currentWorkspace method, of class WorkspaceManager.
     */
    @Test
    public void testCurrentWorkspace() throws IOException {
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        String location = Paths.get(System.getProperty("user.dir"), "target", "default").toString();
        FileUtils.createDirIfNoExists(location);
        
        Properties config = new Properties();
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty("default", location);
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
        
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
        
        WorkspaceManager wsManager = new WorkspaceManager(null, config);
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
        String wsLocation = System.getProperty("user.dir");
        Locale lc = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        Preferences prefs = new WorkspaceManager().get().createDefaultPreferences(wsLocation);
        
        assertNotNull(prefs);
        assertEquals(lc.toString(), prefs.getPreference(Preferences.LANGUAGE));
        assertEquals(Paths.get(wsLocation, "algorithms.xml").toString(), prefs.getPreference(Preferences.ALGORITHMS_FILE));
        assertEquals(Paths.get(wsLocation, "input").toString(), prefs.getPreference(Preferences.DEFAULT_INPUT_DIR));
        assertEquals(Paths.get(wsLocation, "output").toString(), prefs.getPreference(Preferences.DEFAULT_OUTPUT_DIR));
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

    /**
     * Test of initialize method, of class WorkspaceManager.
     */
    @Test
    public void testInitialize() throws Exception {
    }

    /**
     * Test of initConfig method, of class WorkspaceManager.
     */
    @Test
    public void testInitConfig() throws Exception {
    }

    /**
     * Test of setConfig method, of class WorkspaceManager.
     */
    @Test
    public void testSetConfig() {
    }

    /**
     * Test of setConfigPath method, of class WorkspaceManager.
     */
    @Test
    public void testSetConfigPath() {
    }

    /**
     * Test of createDefaultWorkspace method, of class WorkspaceManager.
     */
    @Test
    public void testCreateDefaultWorkspace() {
    }

    /**
     * Test of commitPendingChanges method, of class WorkspaceManager.
     */
    @Test
    public void testCommitPendingChanges() throws Exception {
    }

    /**
     * Test of getPreference method, of class WorkspaceManager.
     */
    @Test
    public void testGetPreference() throws IOException {
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        String location = Paths.get(System.getProperty("user.dir"), "target", "isbench", "default").toString();
        FileUtils.createDirIfNoExists(location);
        
        Properties config = new Properties();
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty("default", location);
        
        Workspace current = new Workspace("default", location);
        WorkspaceManager manager = new WorkspaceManager(configPath, config);
        manager.setCurrentWorkspace(current);
        manager.addPreference(Preferences.LANGUAGE, "CA_es");
        String language = manager.getPreference(Preferences.LANGUAGE);
        
        assertEquals("CA_es", language);
        
    }

    /**
     * Test of getPreferenceAsFile method, of class WorkspaceManager.
     */
    @Test
    public void testGetPreferenceAsFile() {
        String location = Paths.get(System.getProperty("user.dir"), "target", "isbench", "default").toString();
        String configPath = Paths.get(System.getProperty("user.dir"), "target", "isbench", "isbench.properties").toString();
        
        Properties config = new Properties();
        config.setProperty(WorkspaceManager.CURRENT_WORKSPACE, "default");
        config.setProperty("default", location);
        
        Workspace current = new Workspace("default", location);

        WorkspaceManager manager = new WorkspaceManager(configPath, config);
        manager.setCurrentWorkspace(current);
        manager.addPreference(Preferences.ALGORITHMS_FILE, "algorithms.xml");
        
        File preferenceFile = manager.getPreferenceAsFile(Preferences.ALGORITHMS_FILE);
        
        assertNotNull(preferenceFile);
        assertEquals("algorithms.xml", preferenceFile.getName());
    }
  
}
