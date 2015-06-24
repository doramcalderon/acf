package es.uma.pfc.is.bench.algorithms.domain;

import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import java.io.File;
import javax.xml.bind.JAXB;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmEntityTest {

    private static String path = System.getProperty("user.dir") + "/src/test/resources/";

    public AlgorithmEntityTest() {
    }

    @Test
    public void fromXML() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);

        File xmlFile = new File(path + "algorithms.xml");

        javax.xml.bind.JAXB.marshal(alg, xmlFile);
        AlgorithmEntity unmarshalAlg = JAXB.unmarshal(xmlFile, AlgorithmEntity.class);

        assertEquals(alg, unmarshalAlg);
    }

    @Test
    public void testEquals() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO");
        otherAlg.setType(DirectOptimalBasis.class);

        assertEquals(alg, otherAlg);
    }

    @Test
    public void testEqualsWithNameSpaces() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal   ");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("   Direct Optimal");
        otherAlg.setShortName("DO");
        otherAlg.setType(DirectOptimalBasis.class);

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsNameIgnoreUppercase() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("   DO");
        alg.setType(DirectOptimalBasis.class);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("Direct OPTIMAL");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class);

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsWithShrotNameSpaces() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("   DO");
        alg.setType(DirectOptimalBasis.class);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class);

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsShrotNameIgnoreUppercase() {
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("dO");
        alg.setType(DirectOptimalBasis.class);
        
        AlgorithmEntity otherAlg = new AlgorithmEntity();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class);

        assertEquals(alg, otherAlg);
    }

    /**
     * Test of getName method, of class AlgorithmEntity.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of setName method, of class AlgorithmEntity.
     */
    @Test
    public void testSetName() {
    }

    /**
     * Test of getShortName method, of class AlgorithmEntity.
     */
    @Test
    public void testGetShortName() {
    }

    /**
     * Test of setShortName method, of class AlgorithmEntity.
     */
    @Test
    public void testSetShortName() {
    }

    /**
     * Test of getType method, of class AlgorithmEntity.
     */
    @Test
    public void testGetType() {
    }

    /**
     * Test of setType method, of class AlgorithmEntity.
     */
    @Test
    public void testSetType() {
    }

}
