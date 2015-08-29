
package es.uma.pfc.is.commons.workspace;

import es.uma.pfc.is.commons.strings.StringUtils;

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

    public Workspace(String name) {
        this();
        this.name = name;
    }

    
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
     * Name.<br/>
     * If the name contains blanks, these are removed.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = (StringUtils.isEmpty(name)) ? name : name.replaceAll(" ", "");
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

    @Override
    public String toString() {
        return location;
    }
    
    
    
}
