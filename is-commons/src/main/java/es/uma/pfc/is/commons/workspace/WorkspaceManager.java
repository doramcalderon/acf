

package es.uma.pfc.is.commons.workspace;

import com.sun.javafx.runtime.SystemProperties;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workspaces manager.
 * @author Dora Calderón
 */
public class WorkspaceManager {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceManager.class);
     /**
     * Input default directory.
     */
    public static final String DEFAULT_INPUT_PATH = "input";
    /**
     * Output default directory.
     */
    public static final String DEFAULT_OUTPUT_PATH = "output";
    
    /**
     * Single instance.
     */
    private static WorkspaceManager me;
    
    private Properties config;
    private static final String configPath = Paths.get(System.getProperty("user.home"), ".isbench", "isbench.properties").toString();
    
    private Workspace current;

    /**
     * Constructor.
     */
    private WorkspaceManager() {
        try {
            config = new Properties();
            config.load(new FileInputStream(configPath));
        } catch (IOException ex) {
            LOGGER.error("Error initializing the config file.");
        }
    }
    
    
    
    /**
     * 
     * @return Single instance of WorkspaceManager.
     */
    public static WorkspaceManager get() {
        if(me == null) {
            me = new WorkspaceManager();
        }
        return me;
    }
    /**
     * Create a workspace.
     * @param ws Workspace info.
     * @param current If sets this workspace to the current.
     */
    public void create(Workspace ws, boolean current) {
        try {
            FileUtils.createDirIfNoExists(ws.getLocation());
            FileUtils.createDirIfNoExists(Paths.get(ws.getLocation(), DEFAULT_INPUT_PATH).toString());
            FileUtils.createDirIfNoExists(Paths.get(ws.getLocation(), DEFAULT_OUTPUT_PATH).toString());
            savePreferences(ws.getPreferences(), ws.getLocation());
            saveWorkspace(ws, current);
        } catch (IOException ex) {
            LOGGER.error("Error creating workspace.", ex);
        }
    }
    
    protected void setCurrentWorkspace() {
        current = new Workspace();
        String currentWs = config.getProperty("workspace.current");
        String path = config.getProperty(currentWs);
        current.setLocation(path);
        current.setName(currentWs);
        current.setPreferences(new Preferences(Paths.get(path, "preferences.properties")));
        
    }
    public Workspace currentWorkspace() {
        return current;
    }
    
    public void addPreference(String name, String value) {
        try {
            current.getPreferences().setPreference(name, value);
            savePreferences(current.getPreferences(), current.getLocation());
        } catch (IOException ex) {
            LOGGER.error("Error initializing the preferences.", ex);
        }
    }
    
    /**
     * Saves the preferences in afile.
     * @param prefs
     * @param wsLocation
     * @throws IllegalArgumentException if location is null.
     * @throws IOException if ther is an error saving the file.
     */
    public void savePreferences(Preferences prefs, String wsLocation) throws IOException {
        if(wsLocation == null) {
            throw new IllegalArgumentException("location can't be null.");
        }
        Preferences p = prefs;
        if (prefs == null) {
            p = new Preferences();
        }
        if(p.size() == 0) {
            p = createDefaultPreferences();
        }
        p.store(new FileOutputStream(Paths.get(wsLocation, "preferences.properties").toFile()),"");
    }
    
    /**
     * Create a default preferences.
     * @return Prefrences.
     */
    public Preferences createDefaultPreferences() {
        Preferences p = new Preferences();
        Locale lc = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        p.setPreference("language", lc.toString());
        return p;
    }
    
    /**
     * Save workspace in config file.
     * @param ws Workspace.
     * @param current If sets this workspace to the current.
     * @throws IOException 
     */
    protected void saveWorkspace(Workspace ws, boolean current) throws IOException {
        
        String wsNameProperty = "workspace." + ws.getName();
        config.setProperty(wsNameProperty, ws.getLocation());
        if(current) {
            config.setProperty("workspace.change", wsNameProperty);
        }
        config.store(new FileOutputStream(new File(configPath)), "");
    }
}
