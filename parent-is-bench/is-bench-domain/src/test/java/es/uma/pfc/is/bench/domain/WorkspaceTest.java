/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
}
