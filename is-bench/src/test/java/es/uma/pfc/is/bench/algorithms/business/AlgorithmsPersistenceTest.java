package es.uma.pfc.is.bench.algorithms.business;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.config.UserConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.JAXB;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmsPersistenceTest {

    private static final String testWorkspace = System.getProperty("user.dir") + "/src/test/resources";
    private static final String algorithmsFile = testWorkspace + "/algorithms.xml";
    private static final String initConfig = UserConfig.get().getDefaultWorkspace();
    private static AlgorithmsPersistence persistence;
    
    public AlgorithmsPersistenceTest() {
        UserConfig.get().setDefaultWorkspace(testWorkspace);
        persistence = AlgorithmsPersistence.get();
    }

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(algorithmsFile));
        UserConfig.get().setDefaultWorkspace(initConfig);
    }

    /**
     * Test of get method, of class AlgorithmsPersistence.
     */
    @Test
    public void testGet() {
    }

    /**
     * Test of create method, of class AlgorithmsPersistence.
     */
    @Test
    public void testCreate() {
        Algorithms algs = new Algorithms();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algs.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algs.add(alg);
        
        persistence.create(algs);
        Algorithms unmarshalAlg = JAXB.unmarshal(algorithmsFile, Algorithms.class);

        assertNotNull(unmarshalAlg);
        assertArrayEquals(algs.getAlgorithms().toArray(), unmarshalAlg.getAlgorithms().toArray());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_NullAlgorithms() {
        Algorithms algs = null;

        persistence.create(algs);
        fail("Expected IllegalArgumentException.");
    }

    /**
     * Test of insert method, of class AlgorithmsPersistence.
     */
    @Test
    public void testInsert_Algorithms() {
        Algorithms algsExpected = new Algorithms();

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsExpected.add(alg);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algsExpected.add(alg);

        Algorithms algs = new Algorithms();
        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algs.add(alg);
        persistence.create(algs);

        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis.class);
        algs = new Algorithms();
        algs.add(alg);

        persistence.insert(algs);
        Algorithms unmarshalAlg = JAXB.unmarshal(algorithmsFile, Algorithms.class);

        assertArrayEquals(algsExpected.getAlgorithms().toArray(), unmarshalAlg.getAlgorithms().toArray());

    }

    /**
     * Test of insert method, of class AlgorithmsPersistence.
     */
    @Test
    public void testInsert_AlgorithmEntity() {
    }

    /**
     * Test of getAlgorithms method, of class AlgorithmsPersistence.
     */
    @Test
    public void testDeleteAlgorithm() {
        Algorithms algsExpected = new Algorithms();
        

        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algsExpected.add(alg);

        Algorithms algs = new Algorithms();
        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        algs.add(alg);
        
        AlgorithmEntity alg2 = new AlgorithmEntity();
        alg2.setName("Direct Optimal 2");
        alg2.setShortName("DO2");
        alg2.setType(DirectOptimalBasis.class);
        algs.add(alg2);

        persistence.create(algs);
        
        persistence.delete(alg2);
        
        Algorithms unmarshalAlg = JAXB.unmarshal(algorithmsFile, Algorithms.class);
        assertArrayEquals(algsExpected.getAlgorithms().toArray(), unmarshalAlg.getAlgorithms().toArray());
    }

}