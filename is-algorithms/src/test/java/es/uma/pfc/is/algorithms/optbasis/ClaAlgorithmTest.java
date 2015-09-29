/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.testutils.TestUtils;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Dora Calderón
 */
public class ClaAlgorithmTest {
    
    public ClaAlgorithmTest() {
    }

     @Test
    public void testExecute_1() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("do_ej1.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do_ej1_resul.txt");

        ClaAlgorithm alg = new ClaAlgorithm();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    
    @Test @Ignore("Bucle infinito")
    public void testExecute_2() throws IOException {
        ImplicationalSystem inputSystem = TestUtils.getSystemFromFile("do2_ej_olomouc_nunit.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do2_ej_olomouc_nunit_resul.txt");

        ClaAlgorithm alg = new ClaAlgorithm();
        AlgorithmOptions o = new AlgorithmOptions();
        o.addOption(AlgorithmOptions.Options.OUTPUT.toString(), Paths.get(System.getProperty("user.dir"), "do2_ej_olomouc_nunit.log").toString());
        o.enable(AlgorithmOptions.Mode.HISTORY);
        alg.getLogger().setOptions(o);
        
        ImplicationalSystem algResult = alg.execute(inputSystem);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    
     @Test
    public void testExecute_3() throws IOException {
        ImplicationalSystem inputSystem = TestUtils.getSystemFromFile("ej_saedian3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej_saedian3_result.txt");

        ClaAlgorithm alg = (ClaAlgorithm) new ClaAlgorithm();
        AlgorithmOptions o = new AlgorithmOptions();
        o.addOption(AlgorithmOptions.Options.OUTPUT.toString(), Paths.get(System.getProperty("user.dir"), "ej_saedian3.log").toString());
        o.enable(AlgorithmOptions.Mode.HISTORY);
        o.enable(AlgorithmOptions.Mode.PERFORMANCE);
        alg.getLogger().setOptions(o);
        ImplicationalSystem algResult = alg.execute(inputSystem);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
       
    @Test
    public void testExecute_4() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej2.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej2_resul.txt");

        ClaAlgorithm alg = new ClaAlgorithm();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test 
    public void testExecute_5() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej3_resul.txt");

        ClaAlgorithm alg = new ClaAlgorithm();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test
    public void testExecute_6() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej5.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej5_resul.txt");

        ClaAlgorithm alg = new ClaAlgorithm();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    
}
