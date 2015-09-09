
package es.uma.pfc.is.bench.domain.ws;

import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * User preferences.
 * @author Dora Calderón
 */
public class Preferences  extends Properties implements PreferencesNames {
    
    /**
     * Logger.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Preferences.class);
    
    /**
     * Preferences list.
     */
    private List<Preference> preferencesList;

    public Preferences() {
        preferencesList = new ArrayList<>();
    }
    
    /**
     * Constructor.
     * @param p Path.
     */
    public Preferences(Path p) {
        this();
        if(p != null) {
            try {
                File preferencesFile = FileUtils.createIfNoExists(p.toString());
                this.load(new FileInputStream(preferencesFile));
                
                preferencesList = new ArrayList<>();
                this.entrySet().forEach(s -> preferencesList.add(new Preference((String) s.getKey(), (String) s.getValue())));
            } catch (IOException ex) {
                LOGGER.error("Error initializing the preferences.", ex);
            }
        }
    }
    
    
    /**
     * Value of a preference.
     * @param preference Preference name.
     * @return Value of a preference.
     */
    public String getPreference(String preference) {
        return this.getProperty(preference);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        return setPreference(String.valueOf(key), String.valueOf(value));
    }
    
    /**
     * Sets the value of a preference. If value is null, remove the preference if exists.
     * @param key Name of preference.
     * @param value Value.
     */
    public String setPreference(String key, String value) {
        Object r;
        Preference pref = new Preference(key, value);
        if(value == null) {
            r = super.remove(key);
            preferencesList.remove(pref);
        } else {
            r = super.put(key, value);
            preferencesList.add(pref);
        }
        return String.valueOf(r);
    }

    public List<Preference> getPreferencesList() {
        return preferencesList;
    }

    
}
