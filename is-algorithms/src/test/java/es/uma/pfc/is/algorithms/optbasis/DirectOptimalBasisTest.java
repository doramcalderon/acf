
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Ignore;
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
        ImplicationalSystem system = getSystemFromFile("test_1_simplified.txt");
        ImplicationalSystem expectedSystem = getSystemFromFile("test_1_strong_smplified.txt");
        
        DirectOptimalBasis alg = new DirectOptimalBasis();
        ImplicationalSystem simpSystem = alg.strongSimplificate(system);
        System.out.println(simpSystem);
        
        assertTrue(ImplicationalSystems.equals(expectedSystem, simpSystem));
    }

    /**
     * Test of optimize method, of class DirectOptimalBasis.
     */
    @Test @Ignore
    public void testOptimize_ImplicationalSystem() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_1_strong_smplified.txt");
        ImplicationalSystem systemExpected = getSystemFromFile("test_1_optimized.txt");
        
        ImplicationalSystem optimizedSystem = new DirectOptimalBasis().optimize(system);
        
        assertTrue(ImplicationalSystems.equals(systemExpected, optimizedSystem));
        
    }


    @Test
    public void testReduce() throws IOException {
        ImplicationalSystem system = getSystemFromFile("test_1.txt");
        ImplicationalSystem expectedSystem = getSystemFromFile("test_1_reduced.txt");
        ImplicationalSystem reducedSystem = new DirectOptimalBasis().reduce(system);
        
        assertNotNull(reducedSystem);
        assertTrue(ImplicationalSystems.equals(expectedSystem, reducedSystem));
    }
    
    @Test
    public void testReduceNullSystem() throws IOException {
        ImplicationalSystem reducedSystem = new DirectOptimalBasis().reduce(null);    
        assertNull(reducedSystem);
    }
    
    @Test
    public void testSimplify() throws IOException {
        ImplicationalSystem input = getSystemFromFile("test_1_reduced.txt");
        ImplicationalSystem expectedSystem = getSystemFromFile("test_1_simplified.txt");
        
        ImplicationalSystem simplifiedSystem = new DirectOptimalBasis().simplify(input);
        
        assertTrue(ImplicationalSystems.equals(expectedSystem, simplifiedSystem));
    }
    
    //<editor-fold defaultstate="collapsed" desc="Utilidades">
    
    private ImplicationalSystem getSystemFromFile(String file) throws IOException {
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
                    + File.separator + "resources" + File.separator;
        return new ImplicationalSystem(dir + file);        
    }
    
//</editor-fold>
}
