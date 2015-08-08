/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
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
     * Test of addAlgorithms method, of class Workspace.
     */
    @Test
    public void testAddAlgorithm() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Alg 1");
       
        
        Workspace ws = new Workspace();
        ws.addAlgorithms(alg);
        
        assertEquals(1, ws.getAlgorithms().size());
        assertEquals(alg, ws.getAlgorithms().iterator().next());
    }

    /**
     * Checks that the algorithm isn't added when is null.
     */
    @Test
    public void testAddAlgorithm_Null() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Alg 1");
       
        
        Workspace ws = new Workspace();
        ws.addAlgorithms(alg);
        ws.addAlgorithms(null);
        
        assertEquals(1, ws.getAlgorithms().size());
        assertEquals(alg, ws.getAlgorithms().iterator().next());
    }
    
    @Test
    public void testPersistence() throws JAXBException, IOException {
        String path = Paths.get(System.getProperty("user.dir"), "target").toString();
        try {
            AlgorithmEntity alg = new AlgorithmEntity();
            alg.setName("Alg 1");
            AlgorithmEntity alg2 = new AlgorithmEntity();
            alg.setName("Alg 2");

            Workspace ws = new Workspace();
            ws.setPath(path);
            ws.addAlgorithms(alg);
            ws.addAlgorithms(alg2);

            String file = Paths.get(path, "workspace.xml").toString();
            JAXB.marshal(ws, file);

            Workspace unmarshallWs = JAXB.<Workspace>unmarshal(file, Workspace.class);
            assertNotNull(unmarshallWs);
            assertEquals(path, unmarshallWs.getPath());
            assertArrayEquals(ws.getAlgorithms().toArray(), unmarshallWs.getAlgorithms().toArray());
        } finally {
            Files.deleteIfExists(Paths.get(path, "workspace.xml"));
        }
    }

    /**
     * Test of getPath method, of class Workspace.
     */
    @Test
    public void testGetPath() {
    }

    /**
     * Test of setPath method, of class Workspace.
     */
    @Test
    public void testSetPath() {
    }

    /**
     * Test of getAlgorithms method, of class Workspace.
     */
    @Test
    public void testGetAlgorithms() {
    }

    /**
     * Test of setAlgorithms method, of class Workspace.
     */
    @Test
    public void testSetAlgorithms() {
    }

    /**
     * Test of addAlgorithms method, of class Workspace.
     */
    @Test
    public void testAddAlgorithms() {
    }

    /**
     * Test of addAllAlgorithms method, of class Workspace.
     */
    @Test
    public void testAddAllAlgorithms() {
    }

    /**
     * Test of removeAlgorithms method, of class Workspace.
     */
    @Test
    public void testRemoveAlgorithms() {
        AlgorithmEntity alg1 = new AlgorithmEntity();
        alg1.setName("Alg1");
        AlgorithmEntity alg2 = new AlgorithmEntity();
        alg2.setName("Alg2");
        
        Set<AlgorithmEntity> algs = new HashSet<>();
        algs.add(alg1);
        algs.add(alg2);
        
        Workspace ws = new Workspace();
        ws.setAlgorithms(algs);
        ws.removeAlgorithms("Alg1");
        
        assertEquals(1, ws.getAlgorithms().size());
        assertTrue(ws.getAlgorithms().contains(alg2));
    }
    @Test
    public void testRemoveAllAlgorithms() {
        AlgorithmEntity alg1 = new AlgorithmEntity();
        alg1.setName("Alg1");
        AlgorithmEntity alg2 = new AlgorithmEntity();
        alg2.setName("Alg2");
        
        Set<AlgorithmEntity> algs = new HashSet<>();
        algs.add(alg1);
        algs.add(alg2);
        
        Workspace ws = new Workspace();
        ws.setAlgorithms(algs);
        ws.removeAlgorithms("Alg1", "Alg2");
        
        assertEquals(0, ws.getAlgorithms().size());
    }
    
}
