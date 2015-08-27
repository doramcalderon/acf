
package es.uma.pfc.is.commons.workspace;

/**
 * Workspace.
 * @author Dora Calderón
 */
public class Workspace {
    /**
     * Name.
     */
    private String name;
    /**
     * Preferences.
     */
    private Preferences preferences;
    /**
     * Location.
     */
    private String location;

    /**
     * Constructor.
     */
    public Workspace() {
        preferences = new Preferences();
    }

    /**
     * Name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Preferences.
     * @return the preferences
     */
    public Preferences getPreferences() {
        return preferences;
    }

    /**
     * Preferences.
     * @param preferences the preferences to set
     */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Location.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Location.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    
    
}
