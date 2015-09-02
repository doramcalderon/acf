package es.uma.pfc.is.commons.workspace;

import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workspaces manager.
 *
 * @author Dora Calderón
 */
public class WorkspaceManager {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceManager.class);
    
    /**
     * Default workspace location.
     */
    public static final String DEFAULT_WORKSPACE_PATH = Paths.get(System.getProperty("user.home"),
            ".isbench", "default").toString();
    /**
     * Default workspace name.
     */
    public static final String DEFAULT_WORKSPACE_NAME = "default";
    /**
     * Input default directory.
     */
    public static final String DEFAULT_INPUT_PATH = "input";
    /**
     * Output default directory.
     */
    public static final String DEFAULT_OUTPUT_PATH = "output";
    /**
     * Current workspace property.
     */
    public static final String CURRENT_WORKSPACE = "workspace.current";
    /**
     * Current workspace property.
     */
    public static final String WORKSPACE_CHANGE = "workspace.change";
    /**
     * Single instance.
     */
    private static WorkspaceManager me;
    /**
     * General configuration.
     */
    private Properties config;
    
    /**
     * Configuration file path.
     */
    public String configPath = Paths.get(System.getProperty("user.home"),".isbench", "isbench.properties").toString();
    /**
     * Current workspace.
     */
    private Workspace current;

    /**
     * Constructor.
     */
    protected WorkspaceManager() {
        try {
            initialize();
        } catch (IOException ex) {
            LOGGER.error("Error initializing the config file.");
        }
    }

     /**
     * Initialize the workspacemanager.<br/>
     * If no exists the config file, creates it.<br/>
     * If the current workspace not is setted, creates the default workspace and sets it as the current.
     */
    protected final void initialize() throws IOException {
        initConfig();
        Workspace current = currentWorkspace();
        if (current == null) {
            createDefaultWorkspace();
        }
        commitPendingChanges();
    }
    
     /**
     * Loads the isbench.properties file and if no exists, creates it.
     *
     * @throws IOException Read / Write error.
     */
    protected void initConfig() throws IOException {
        config = new Properties();
        File configFile = FileUtils.createIfNoExists(configPath);
        config.load(new FileInputStream(configFile));
    }

    /**
     * Only for testing usage.
     *
     * @param config Config.
     */
    protected void setConfig(Properties config) {
        this.config = config;
        current = null;
    }
/**
     * Only for testing usage.
     *
     * @param configPath Config path.
     */
    protected void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
    /**
     *
     * @return Single instance of WorkspaceManager.
     */
    public static WorkspaceManager get() {
        if (me == null) {
            me = new WorkspaceManager();
        }
        return me;
    }

   

    /**
     * Create the default workspace.
     */
    public void createDefaultWorkspace() {
        String currentPath = config.getProperty(CURRENT_WORKSPACE);

        if (StringUtils.isEmpty(currentPath)) {
            create(new Workspace(DEFAULT_WORKSPACE_NAME, DEFAULT_WORKSPACE_PATH), true);
        }
    }

    /**
     * Create a workspace.
     *
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

    /**
     * Mark a workspace as current. The change will take effect with {@link #commitPendingChanges() }.
     *
     * @param ws Workspace.
     */
    public void change(Workspace ws) {
        if (ws != null && !StringUtils.isEmpty(ws.getName())) {
            config.setProperty(WORKSPACE_CHANGE, ws.getName());
        }
    }

    /**
     * The workspace change takes effect.
     */
    public void commitPendingChanges() throws IOException {
        String wsChange = config.getProperty(WORKSPACE_CHANGE);
        if (!StringUtils.isEmpty(wsChange)) {
            current = new Workspace();
            String path = config.getProperty(wsChange);
            current.setLocation(path);
            current.setName(wsChange);
            current.setPreferences(new Preferences(Paths.get(path, "preferences.properties")));

            config.setProperty(CURRENT_WORKSPACE, wsChange);
            config.remove(WORKSPACE_CHANGE);
            config.store(new FileOutputStream(new File(configPath)), "");
        }
    }

    /**
     * Current workspace.
     *
     * @return Workspace.
     */
    public Workspace currentWorkspace() {
        if (current == null) {
            String currentName = config.getProperty(CURRENT_WORKSPACE);
            if (!StringUtils.isEmpty(currentName)) {
                current = new Workspace();
                current.setName(currentName);
                current.setLocation(config.getProperty(currentName));
                current.setPreferences(new Preferences(Paths.get(current.getLocation(), "preferences.properties")));
            }
        }
        return current;
    }

    public List<Workspace> registeredWorkspaces() {
        List<Workspace> workspaces = new ArrayList<>();
        List<String> propertyNames = Collections.list((Enumeration<String>) config.propertyNames());

        if (propertyNames != null && !propertyNames.isEmpty()) {
            Workspace ws;
            for (String name : propertyNames) {
                if (!StringUtils.isEmpty(name)
                        && !WORKSPACE_CHANGE.equals(name) && !CURRENT_WORKSPACE.equals(name)) {
                    ws = new Workspace();
                    ws.setName(name);
                    ws.setLocation(config.getProperty(name));
                    ws.setPreferences(new Preferences(Paths.get(ws.getLocation(), "preferences.properties")));
                    workspaces.add(ws);
                }
            }
        }
        return workspaces;
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
     *
     * @param prefs
     * @param wsLocation
     * @throws IllegalArgumentException if location is null.
     * @throws IOException if ther is an error saving the file.
     */
    public void savePreferences(Preferences prefs, String wsLocation) throws IOException {
        if (wsLocation == null) {
            throw new IllegalArgumentException("location can't be null.");
        }
        Preferences p = prefs;
        if (prefs == null) {
            p = new Preferences();
        }
        if (p.size() == 0) {
            p = createDefaultPreferences();
        }
        p.store(new FileOutputStream(Paths.get(wsLocation, "preferences.properties").toFile()), "");
    }

    /**
     * Create a default preferences.
     *
     * @return Prefrences.
     */
    public Preferences createDefaultPreferences() {
        Preferences p = new Preferences();
        Locale lc = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        p.setPreference(Preferences.LANGUAGE, lc.toString());
        return p;
    }

    /**
     * Save workspace in config file.
     *
     * @param ws Workspace.
     * @param current If sets this workspace to the current.
     * @throws IOException
     */
    protected void saveWorkspace(Workspace ws, boolean current) throws IOException {

        String wsNameProperty = ws.getName();
        config.setProperty(wsNameProperty, ws.getLocation());
        if (current) {
            config.setProperty(CURRENT_WORKSPACE, wsNameProperty);
            config.remove(WORKSPACE_CHANGE);
        } else {
            config.setProperty(WORKSPACE_CHANGE, wsNameProperty);
        }
        config.store(new FileOutputStream(new File(configPath)), "");
    }
}
