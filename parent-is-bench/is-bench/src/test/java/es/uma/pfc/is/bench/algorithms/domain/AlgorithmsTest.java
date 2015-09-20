
package es.uma.pfc.is.bench.algorithms.domain;


import es.uma.pfc.is.bench.domain.Algorithms;
import es.uma.pfc.is.algorithms.optbasis.DirectOptimalBasis2;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import java.io.File;
import javax.xml.bind.JAXB;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class AlgorithmsTest {
    private static String path = System.getProperty("user.dir") + "/src/test/resources/";
    
    public AlgorithmsTest() {
    }
    
     @Test
    public void toFromXML() {
        Algorithms algs = new Algorithms();
        
        AlgorithmEntity alg = new AlgorithmEntity();
        alg.setName("Direct Optimal");
        alg.setShortName("DO");
        alg.setType(DirectOptimalBasis2.class);
        algs.add(alg);
        
        alg = new AlgorithmEntity();
        alg.setName("Direct Optimal 2");
        alg.setShortName("DO2");
        alg.setType(DirectOptimalBasis2.class);
        algs.add(alg);
        
        File xmlFile = new File(path + "algorithms.xml");
        
        javax.xml.bind.JAXB.marshal(algs, xmlFile);
        
        Algorithms unmarshalAlg = JAXB.unmarshal(xmlFile, Algorithms.class);
        
        
         assertNotNull(unmarshalAlg);
         assertArrayEquals(algs.getAlgorithms().toArray(), unmarshalAlg.getAlgorithms().toArray());
        

    }

    /**
     * Test of getAlgorithms method, of class Algorithms.
     */
    @Test
    public void testGetAlgorithms() {
    }

    /**
     * Test of setAlgorithms method, of class Algorithms.
     */
    @Test
    public void testSetAlgorithms() {
    }

    /**
     * Test of add method, of class Algorithms.
     */
    @Test
    public void testAdd() {
    }
    
}
