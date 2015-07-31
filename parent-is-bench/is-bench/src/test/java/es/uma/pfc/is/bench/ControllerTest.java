/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench;

import java.util.Enumeration;
import java.util.ResourceBundle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class ControllerTest {
    
    public ControllerTest() {
    }

    

    /**
     * Test of getI18nLabel method, of class Controller.
     */
    @Test
    public void testGetI18nString() {
        Controller c = new Controller() {};
        c.setBundle(ResourceBundle.getBundle("messages"));
        String key = "title";
        
        String title = c.getI18nLabel(key);
        assertEquals("Title", title);
    }
    @Test
    public void testGetI18nStringNotFound() {
        Controller c = new Controller() {};
        c.setBundle(ResourceBundle.getBundle("messages"));
        String key = "window";
        
        String value = c.getI18nLabel(key);
        assertEquals(key, value);
    }

    public class ControllerImpl extends Controller {
    }
    
}
