package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.testutils.TestUtils;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Execution of Direct Optimal Basis with different implicational systems.
 * @author Dora Calderón
 */
public class DirectOptimalBasis2Test {

    public DirectOptimalBasis2Test() {
    }

    
    @Test
    public void testExecute_1() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("do_ej1.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do_ej1_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }

    
    @Test
    public void testExecute_2() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("do_ej_olomouc_nunit_resul.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do_ej_olomouc_nunit_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }

    
    @Test
    public void testExecute_3() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej_saedian3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej_saedian3_result.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        alg.enable(AlgorithmOptions.Mode.HISTORY);
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
       
    @Test
    public void testExecute_4() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej2.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej2_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test
    public void testExecute_5() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej3_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test
    public void testExecute_6() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej5.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej5_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test
    public void testExecute_7() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("exp15-1.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("exp15-1_resul.txt");

        DirectOptimalBasis2 alg = new DirectOptimalBasis2();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }

}
