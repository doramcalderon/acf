package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.testutils.TestUtils;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeSet;
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
    public void testReduce() {
        ImplicationalSystem system = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<>();
        elements.addAll(Arrays.asList(0,1,2,3,4));
        
        system.addAllElements(elements);
        Rule r = new Rule();
        r.addAllToPremise(Arrays.asList(0,1));
        r.addAllToConclusion(Arrays.asList(1,2));
        system.addRule(r);
        
        DirectOptimalBasis alg = new DirectOptimalBasis();
        alg.reduce(system);
        
        assertEquals(5, system.sizeElements());
        assertEquals(1, system.sizeRules());
        assertTrue(system.getRules().first().getPremise().containsAll(Arrays.asList(0, 1)));
        assertFalse(system.getRules().first().getConclusion().contains(1));
        assertTrue(system.getRules().first().getConclusion().contains(2));
    }
    @Test
    public void testReduce_EmptyConclusion() {
        ImplicationalSystem system = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<>();
        elements.addAll(Arrays.asList(0,1,2,3,4));
        
        system.addAllElements(elements);
        Rule r = new Rule();
        r.addAllToPremise(Arrays.asList(0,1));
        r.addAllToConclusion(Arrays.asList(1));
        system.addRule(r);
        
        DirectOptimalBasis alg = new DirectOptimalBasis();
        alg.reduce(system);
        
        assertEquals(5, system.sizeElements());
        assertEquals(0, system.sizeRules());
    }
    
    @Test
    public void testExecute_1() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("do_ej1.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do_ej1_resul.txt");

        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }

    
    @Test @Ignore
    public void testExecute_2() throws IOException {
        ImplicationalSystem inputSystem = TestUtils.getSystemFromFile("do2_ej_olomouc_nunit.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("do2_ej_olomouc_nunit_resul.txt");

        DirectOptimalBasis alg = (DirectOptimalBasis) new DirectOptimalBasis();
        AlgorithmOptions o = new AlgorithmOptions();
        o.addOption(AlgorithmOptions.Options.OUTPUT.toString(), Paths.get(System.getProperty("user.dir"), "do2_ej_olomouc_nunit.log").toString());
        o.enable(AlgorithmOptions.Mode.TRACE);
        alg.getLogger().setOptions(o);
        
        ImplicationalSystem algResult = alg.execute(inputSystem);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }

    
    @Test
    public void testExecute_3() throws IOException {
        ImplicationalSystem inputSystem = TestUtils.getSystemFromFile("ej_saedian3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej_saedian3_result.txt");

        DirectOptimalBasis alg = (DirectOptimalBasis) new DirectOptimalBasis();
        AlgorithmOptions o = new AlgorithmOptions();
        o.addOption(AlgorithmOptions.Options.OUTPUT.toString(), Paths.get(System.getProperty("user.dir"), "ej_saedian3.log").toString());
        o.enable(AlgorithmOptions.Mode.TRACE);
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

        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test 
    public void testExecute_5() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej3.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej3_resul.txt");

        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem algResult = alg.execute(system);

        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    @Test
    public void testExecute_6() throws IOException {
        ImplicationalSystem system = TestUtils.getSystemFromFile("ej5.txt");
        ImplicationalSystem expectedSystem = TestUtils.getSystemFromFile("ej5_resul.txt");

        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem algResult = alg.execute(system);
        
        assertTrue("Expected: \n" + expectedSystem + "\n Alg result:\n" + algResult, 
                    ImplicationalSystems.equals(expectedSystem, algResult));
    }
    

}
