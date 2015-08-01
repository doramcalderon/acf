package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.Workspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.JAXB;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class WorkspaceBeanTest {

    private final String path = Paths.get(System.getProperty("user.dir"), "target").toString();

    public WorkspaceBeanTest() {
    }

    /**
     * If the workspace not exists, this is created. Otherwise, the workspaces is not modified.
     */
    @Test
    public void testCreateIfNoExists() throws IOException {
        Path workspaceFilePath = Paths.get(path, "workspace.xml");
        Files.deleteIfExists(workspaceFilePath);
        
        Workspace ws = new Workspace(path);
        WorkspaceBean bean = new WorkspaceBean();
        
        bean.createIfNoExists(ws);
        ws.addAlgorithms(new AlgorithmEntity());
        bean.createIfNoExists(ws);
        
        Workspace unmarshWorkspace = JAXB.unmarshal(workspaceFilePath.toString(), Workspace.class);
        assertNotNull(unmarshWorkspace);
        assertEquals(ws.getPath(), unmarshWorkspace.getPath());
        assertNull(unmarshWorkspace.getAlgorithms());
        
    }

    /**
     * Test of create method, of class WorkspaceBean.
     */
    @Test
    public void testCreate() {
        Workspace ws = new Workspace(path);
        WorkspaceBean bean = new WorkspaceBean();
        
        bean.create(ws);
        
        Workspace unmarshWorkspace = JAXB.unmarshal(Paths.get(path, "workspace.xml").toString(), Workspace.class);
        assertNotNull(unmarshWorkspace);
        assertEquals(ws.getPath(), unmarshWorkspace.getPath());
        
    }

    /**
     * Test of getWorkspace method, of class WorkspaceBean.
     */
    @Test
    public void testGetWorkspace() {
        WorkspacePersistence.create(new Workspace(path));

        Workspace ws = new WorkspaceBean().getWorkspace(path);

        assertNotNull(ws);
        assertEquals(path, ws.getPath());

    }

    /**
     * Test of addAlgorithms method, of class WorkspaceBean.
     */
    @Test
    public void testAddAlgorithms() {
        Workspace ws = new Workspace(path);
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Alg 1");
        ws.addAlgorithms(alg);
        WorkspacePersistence.create(ws);

        AlgorithmEntity alg2 = new AlgorithmEntity();
        alg2.setName("Alg 2");
        AlgorithmEntity alg3 = new AlgorithmEntity();
        alg3.setName("Alg 3");
        AlgorithmEntity alg4 = new AlgorithmEntity();
        alg4.setName("Alg 4");

        AlgorithmEntity[] algorithms = new AlgorithmEntity[]{alg2, alg3, alg4};
        WorkspaceBean bean = new WorkspaceBean();
        bean.addAlgorithms(path, algorithms);

        Workspace unmarsahllWs = JAXB.unmarshal(Paths.get(path, "workspace.xml").toString(), Workspace.class);

        assertNotNull(unmarsahllWs);
        assertEquals(path, unmarsahllWs.getPath());
        assertEquals(4, unmarsahllWs.getAlgorithms().size());

    }

}
