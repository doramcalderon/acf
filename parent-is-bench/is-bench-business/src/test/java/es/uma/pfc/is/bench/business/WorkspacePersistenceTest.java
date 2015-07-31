/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.Workspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.JAXB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class WorkspacePersistenceTest {

    public WorkspacePersistenceTest() {
    }

    /**
     * Test of create method, of class WorkspacePersistence.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testCreate() throws IOException {
        String path = Paths.get(System.getProperty("user.dir"), "target").toString();
        Path wsPath = Paths.get(path, "workspace.xml");
        try {
            Workspace ws = new Workspace();
            ws.setPath(path);

            WorkspacePersistence.create(ws);
            Workspace unmarshallWs = JAXB.unmarshal(wsPath.toFile(), Workspace.class);

            assertNotNull(unmarshallWs);
            assertEquals(path, ws.getPath());
        } finally {
            Files.deleteIfExists(wsPath);
        }
    }

    /**
     * Test of getWorkspace method, of class WorkspacePersistence.
     */
    @Test
    public void testGetWorkspace() throws IOException {
        String path = Paths.get(System.getProperty("user.dir"), "target").toString();
        Path wsPath = Paths.get(path, "workspace.xml");
        try {
            Workspace ws = new Workspace();
            ws.setPath(path);

            WorkspacePersistence.create(ws);
            Workspace unmarshallWs = WorkspacePersistence.getWorkspace(path);

            assertNotNull(unmarshallWs);
            assertEquals(path, ws.getPath());
        } finally {
            Files.deleteIfExists(wsPath);
        }
    }

}
