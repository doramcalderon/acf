
package es.uma.pfc.is.commons.workspace;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 * User preferences.
 * @author Dora Calderón
 */
public class Preferences  extends Properties {
    /**
     * Logger.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Preferences.class);

    public Preferences() {
    }
    
    public Preferences(Path p) {
        this();
        if(p != null) {
            try {
                this.load(new FileInputStream(p.toFile()));
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
    /**
     * Sets the value of a preference. If value is null, remove the preference if exists.
     * @param key Name of preference.
     * @param value Value.
     */
    public void setPreference(String key, String value) {
        if(value == null) {
            this.remove(key);
        } else {
            this.put(key, value);
        }
    }

}
