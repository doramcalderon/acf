
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests de {@link DirectOptimalBasis}.
 * @author Dora Calderón
 */
public class DirectOptimalBasisTest {
    
    public DirectOptimalBasisTest() {
    }

    /**
     * Test of execute method, of class DirectOptimalBasis.
     */
    @Test
    public void testExecute() {
    }

    /**
     * Test of simplificate method, of class DirectOptimalBasis.
     */
    @Test
    public void testSimplificate() throws IOException {
        ImplicationalSystem system = getSystemFromFile("do_ej1.txt");
        ImplicationalSystem expectedSystem = getSystemFromFile("do_ej1_resul.txt");
        
        system = new DirectOptimalBasis().simplificate(system);
        
        assertEquals(expectedSystem.sizeRules(), system.sizeRules());
        for (Rule rule : system.getRules()) {
            assertTrue(expectedSystem.containsRule(rule));
        }
    }

    /**
     * Test of strongSimplificate method, of class DirectOptimalBasis.
     */
    @Test
    public void testStrongSimplificate() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_strong_smpl.txt");
        ImplicationalSystem expectedSystem = getSystemFromFile("test_strong_smpl_resul.txt");
        
        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem simpSystem = alg.strongSimplificate(system);
        System.out.println(simpSystem);
        
        assertNotNull(simpSystem);
        assertTrue(simpSystem.getRules().containsAll(expectedSystem.getRules()));
    }

    /**
     * Test of optimize method, of class DirectOptimalBasis.
     */
    @Test 
    public void testOptimize_ImplicationalSystem() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_optimize.txt");
        ImplicationalSystem systemExpected = getSystemFromFile("test_optimize_result.txt");
        
        ImplicationalSystem optimizedSystem = new DirectOptimalBasis().optimize(system);
        
        assertTrue(ImplicationalSystems.equals(systemExpected, optimizedSystem));
        
    }


    @Test
    public void testReduce() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_1.txt");
        
        ImplicationalSystem reducedSystem = new DirectOptimalBasis().reduce(system);
        
        assertNotNull(reducedSystem);
        assertEquals(new Integer(4), (Integer) reducedSystem.sizeRules());
        
        Rule r = new Rule();
        r.addToPremise("d");
        r.addToConclusion("c");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("c");
        r.addToConclusion("a");
        r.addToConclusion("b");
        r.addToConclusion("d");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("c");
        r.addToPremise("e");
        r.addToConclusion("a");
        r.addToConclusion("b");
        assertTrue(reducedSystem.containsRule(r));
        
        r = new Rule();
        r.addToPremise("a");
        r.addToConclusion("d");
        assertTrue(reducedSystem.containsRule(r));
    }
    
    @Test
    public void testReduceNullSystem() throws IOException {
        ImplicationalSystem reducedSystem = new DirectOptimalBasis().reduce(null);    
        assertNull(reducedSystem);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Utilidades">
    
    private ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
                    + File.separator + "resources" + File.separator;
        return new ImplicationalSystem(dir + file);        
    }
    
//</editor-fold>
}
