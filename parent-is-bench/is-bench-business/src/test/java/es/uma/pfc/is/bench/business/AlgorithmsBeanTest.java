
package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
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
    private final String algorithmsPath = Paths.get(System.getProperty("user.dir"), "target", "algorithsm.xml").toString();
    
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
        
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.exists(alg));
        
    }

    /**
     * Test of exists method, of class AlgorithmsBean.
     */
    @Test
    public void testExists_False() {
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
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
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
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.existsName("Direct Optimal"));
    }
     
    @Test
    public void testExistsName_False() {
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
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
        bean.setPersistence(persistence);
       
        
        assertFalse(bean.existsName("Direct"));
    }

    /**
     * Test of existsShortName method, of class AlgorithmsBean.
     */
    @Test
    public void testExistsShortName() {
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
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
        bean.setPersistence(persistence);
        
        assertTrue(bean.existsShortName("DO"));
    }
    @Test
    public void testExistsShortName_False() {
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
        
        AlgorithmsPersistence persistence = mock(AlgorithmsPersistence.class);
        when(persistence.getAlgorithmsCatalog()).thenReturn(algsConfigured);
        
        AlgorithmsBean bean = new AlgorithmsBean(algorithmsPath);
        bean.setPersistence(persistence);
        
        assertFalse(bean.existsShortName("DOnnn"));
    }
    
}
