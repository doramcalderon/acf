
package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.Workspace;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmsBeanTest {
    private final String workspacePath = Paths.get(System.getProperty("user.dir"), "target").toString();
    
    public AlgorithmsBeanTest() {
    }

    /**
     * Test of create method, of class AlgorithmsBean.
     */
    @Test
    public void testCreate() {
    }

    /**
     * Test of insert method, of class AlgorithmsBean.
     */
    @Test
    public void testInsert() {
    }

    /**
     * Test of delete method, of class AlgorithmsBean.
     */
    @Test
    public void testDelete() {
    }

    /**
     * Test of exists method, of class AlgorithmsBean.
     */
    @Test
    public void testExists() {
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();
        Workspace ws = new Workspace("");
        
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.exists(alg));
        
    }

    /**
     * Test of exists method, of class AlgorithmsBean.
     */
    @Test
    public void testExists_False() {
        Workspace ws = new Workspace("");
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("Direct Optimal 3");
        otherAlg.setShortName("DO3");
        otherAlg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        
        assertFalse(bean.exists(otherAlg));
        
    }

    /**
     * Test of existsName method, of class AlgorithmsBean.
     */
    @Test
    public void testExistsName() {
        Workspace ws = new Workspace(workspacePath);
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.existsName("Direct Optimal"));
    }
     
    @Test
    public void testExistsName_False() {
        Workspace ws = new Workspace(workspacePath);
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
       
        
        assertFalse(bean.existsName("Direct"));
    }

    /**
     * Test of existsShortName method, of class AlgorithmsBean.
     */
    @Test
    public void testExistsShortName() {
        Workspace ws = new Workspace(workspacePath);
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.existsShortName("DO"));
    }
    @Test
    public void testExistsShortName_False() {
        Workspace ws = new Workspace(workspacePath);
        Set<AlgorithmEntity> algsConfigured = new HashSet<>();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsConfigured.add(alg);
        ws.addAllAlgorithms(algsConfigured);
        
        WorkspacePersistence persistence = mock(WorkspacePersistence.class);
        when(persistence.getWorkspace(workspacePath)).thenReturn(ws);
        
        AlgorithmsBean bean = new AlgorithmsBean(workspacePath);
        bean.setPersistence(persistence);
        
        assertFalse(bean.existsShortName("DOnnn"));
    }
    
}
