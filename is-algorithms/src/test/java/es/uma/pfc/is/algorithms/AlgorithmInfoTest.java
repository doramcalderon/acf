package es.uma.pfc.is.algorithms;



import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis;
import java.io.File;
import javax.xml.bind.JAXB;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmInfoTest {

    private static String path = System.getProperty("user.dir") + "/src/test/resources/";

    public AlgorithmInfoTest() {
    }

    @Test
    public void fromXML() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class.getName());

        File xmlFile = new File(path + "algorithms.xml");

        javax.xml.bind.JAXB.marshal(alg, xmlFile);
        AlgorithmInfo unmarshalAlg = JAXB.unmarshal(xmlFile, AlgorithmInfo.class);

        assertEquals(alg.getName(), unmarshalAlg.getName());
    }

    @Test
    public void testEquals() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class.getName());
        
        AlgorithmInfo otherAlg = new AlgorithmInfo();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO");
        otherAlg.setType(DirectOptimalBasis.class.getName());

        assertEquals(alg, otherAlg);
    }

    @Test
    public void testEqualsWithNameSpaces() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal   ");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis.class.getName());
        
        AlgorithmInfo otherAlg = new AlgorithmInfo();
        otherAlg.setName("   Direct Optimal");
        otherAlg.setShortName("DO");
        otherAlg.setType(DirectOptimalBasis.class.getName());

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsNameIgnoreUppercase() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal");
        alg.setShortName("   DO");
        alg.setType(DirectOptimalBasis.class.getName());
        
        AlgorithmInfo otherAlg = new AlgorithmInfo();
        otherAlg.setName("Direct OPTIMAL");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class.getName());

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsWithShrotNameSpaces() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal");
        alg.setShortName("   DO");
        alg.setType(DirectOptimalBasis.class.getName());
        
        AlgorithmInfo otherAlg = new AlgorithmInfo();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class.getName());

        assertEquals(alg, otherAlg);
    }
    @Test
    public void testEqualsShrotNameIgnoreUppercase() {
        AlgorithmInfo alg = new AlgorithmInfo();
        alg.setName("Direct Optimal");
        alg.setShortName("dO");
        alg.setType(DirectOptimalBasis.class.getName());
        
        AlgorithmInfo otherAlg = new AlgorithmInfo();
        otherAlg.setName("Direct Optimal");
        otherAlg.setShortName("DO   ");
        otherAlg.setType(DirectOptimalBasis.class.getName());

        assertEquals(alg, otherAlg);
    }

    /**
     * Test of getName method, of class AlgorithmInfo.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of setName method, of class AlgorithmInfo.
     */
    @Test
    public void testSetName() {
    }

    /**
     * Test of getShortName method, of class AlgorithmInfo.
     */
    @Test
    public void testGetShortName() {
    }

    /**
     * Test of setShortName method, of class AlgorithmInfo.
     */
    @Test
    public void testSetShortName() {
    }

    /**
     * Test of getType method, of class AlgorithmInfo.
     */
    @Test
    public void testGetType() {
    }

    /**
     * Test of setType method, of class AlgorithmInfo.
     */
    @Test
    public void testSetType() {
    }

}
