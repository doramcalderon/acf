
package es.uma.pfc.is.bench.domain.ws;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class PreferencesTest {
    
 

    /**
     * Test of getPreference method, of class Preferences.
     */
    @Test
    public void testGetSetPreference() {
        Preferences p = new Preferences();
        p.setPreference("language", "ES");
        
        String prefValue = p.getPreference("language");
        assertEquals("ES", prefValue);
        assertTrue(p.getPreferencesList().contains(new Preference("language", "ES")));
        
    }

    /**
     * Test of setPreference method, of class Preferences.
     */
    @Test
    public void testSetNullExistPreference() {
        Preferences p = new Preferences();
        p.setPreference("language", "ES");
        p.setPreference("language", null);
        
        String prefValue = p.getPreference("language");
        assertNull(prefValue);
        assertFalse(p.getPreferencesList().contains(new Preference("language", "ES")));
    }


    /**
     * Test of getPreferencesList method, of class Preferences.
     */
    @Test
    public void testGetPreferencesList() {
        Preferences p = new Preferences();
        p.setPreference("language", "ES");
        
        
        List<Preference> preferencesList = p.getPreferencesList();
        assertNotNull(preferencesList);
        Preference langPref = preferencesList.get(0);
        assertNotNull(langPref);
        assertEquals("language", langPref.getName());
        assertEquals("ES", langPref.getValue());
    }
    
}
