/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain.ws;

import es.uma.pfc.is.bench.domain.ws.Workspace;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class WorkspaceTest {
    
    public WorkspaceTest() {
    }

    /**
     * Test of getName method, of class Workspace.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of setName method, of class Workspace.
     */
    @Test
    public void testSetName() {
        String name = "MyWS";
        Workspace ws = new Workspace();
        ws.setName(name);
        assertEquals("MyWS", ws.getName());
    }
    /**
     * Test of setName method, of class Workspace.
     */
    @Test
    public void testSetNameWithBlanks() {
        String name = "My WS";
        Workspace ws = new Workspace();
        ws.setName(name);
        assertEquals("MyWS", ws.getName());
    }

    /**
     * Test of getPreferences method, of class Workspace.
     */
    @Test
    public void testGetPreferences() {
    }

    /**
     * Test of setPreferences method, of class Workspace.
     */
    @Test
    public void testSetPreferences() {
    }

    /**
     * Test of getLocation method, of class Workspace.
     */
    @Test
    public void testGetLocation() {
    }

    /**
     * Test of setLocation method, of class Workspace.
     */
    @Test
    public void testSetLocation() {
    }
    
}
