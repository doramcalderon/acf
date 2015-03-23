
package es.uma.pfc.implications.generator.io;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.lattice.io.ImplicationalSystemWriterFactory;
import fr.kbertet.util.ComparableSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Dora Calderón
 */
public class GeneratorImplicationalSystemIOTest {
    
    @BeforeClass
    public static void init() throws IOException {
        ImplicationalSystemWriterProlog.register();
        if(!Files.exists(Paths.get("tests"))) {
            Files.createDirectory(Paths.get("tests"));
        }
    }

    @AfterClass
    public static void tearDown() throws IOException {
        ImplicationalSystemWriterFactory.unregister("pl");
    }
    /**
     * Test of write method, of class ImplicationalSystemWriterProlog.
     */
    @Test
    public void testWritePl_List() throws Exception {
        List<ImplicationalSystem> systems = new ArrayList();
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        TreeSet<Rule> rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
        systems.add(system);
        system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
        systems.add(system);
        
        GeneratorImplicationalSystemIO.save(systems, "tests/test.pl");
        
        Path path = Paths.get("tests/test_1.pl");
        assertTrue(Files.exists(path));
        
        path = Paths.get("tests/test_2.pl");
        assertTrue(Files.exists(path));
    }
    /**
     * Test of write method, of class ImplicationalSystemWriterProlog.
     */
    @Test
    public void testWriteText_List() throws Exception {
        List<ImplicationalSystem> systems = new ArrayList();
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        TreeSet<Rule> rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
        systems.add(system);
        system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
        systems.add(system);
        
        GeneratorImplicationalSystemIO.save(systems, "tests/test.txt");
        
        Path path = Paths.get("tests/test_1.txt");
        assertTrue(Files.exists(path));
        
        path = Paths.get("tests/test_2.txt");
        assertTrue(Files.exists(path));
    }
    
      private TreeSet<Rule> getImplications() {
        TreeSet<Rule> implications = new ComparableSet();
        TreeSet premise1 = new TreeSet(Arrays.asList("a"));
        TreeSet conclusion1 = new TreeSet(Arrays.asList("b"));
        TreeSet premise2 = new TreeSet(Arrays.asList("b"));
        TreeSet conclusion2 = new TreeSet(Arrays.asList("c"));
        implications.add(new Rule(premise1, conclusion1));
        implications.add(new Rule(premise2, conclusion2));
        return implications;
    }
    private TreeSet<Comparable> getNodes() {
        TreeSet<Comparable> attributes = new ComparableSet();
        attributes.addAll(Arrays.asList("a","b","c","d","e"));
        return attributes;
    }
}
