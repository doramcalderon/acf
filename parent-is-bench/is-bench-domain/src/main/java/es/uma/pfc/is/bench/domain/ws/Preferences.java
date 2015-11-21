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
 * Contains the user preferences.
 * These are serialized to properties file in the workspace.
 */
public class Preferences extends Properties implements PreferencesNames {

    /**
     * Logger.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Preferences.class);

    /**
     * Preferences list.
     */
    private List<Preference> preferencesList;

    /**
     * Constructor.
     */
    public Preferences() {
        preferencesList = new ArrayList<>();
    }

    /**
     * Builds an instance based on an existent preferences file.
     * @param p Ruta absoluta del archivo de preferencias.
     */
    public Preferences(Path p) {
        this();
        if (p != null) {
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

    /**
     * Creates or modify a preference.
     * @param name Name.
     * @param value Value.
     * @return Previous value of modified preference.
     */
    @Override
    public synchronized Object put(Object name, Object value) {
        return setPreference(String.valueOf(name), String.valueOf(value));
    }

    /**
     * Sets the value of a preference. If value is null, remove the preference if exists.
     * @param name Name of preference.
     * @param value Value.
     */
    public String setPreference(String name, String value) {
        Object r;
        Preference pref = new Preference(name, value);
        if (value == null) {
            r = super.remove(name);
            preferencesList.remove(pref);
        } else {
            r = super.put(name, value);
            preferencesList.add(pref);
        }
        return String.valueOf(r);
    }
    /**
     * Sets the value of a preference if no exists. If value is null, remove the preference if exists.
     * @param name Name of preference.
     * @param value Value.
     */
    public String setIfNoExist(String name, String value) {
        String p = getPreference(value);
        if(p == null) {
            p = setPreference(name, value);
        }
        return p;
    }
    /**
     * Returns the preferences list.
     * @return Preferences list.
     */
    public List<Preference> getPreferencesList() {
        return preferencesList;
    }

}
