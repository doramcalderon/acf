/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.commons.workspace;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class PreferencesTest {
    
    public PreferencesTest() {
    }

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
     * Test of getPreference method, of class Preferences.
     */
    @Test
    public void testGetPreference() {
    }

    /**
     * Test of setPreference method, of class Preferences.
     */
    @Test
    public void testSetPreference() {
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
