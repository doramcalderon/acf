/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.implications.generator.io;

import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.lattice.io.ImplicationalSystemWriterFactory;
import fr.kbertet.util.ComparableSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class ImplicationalSystemWriterPrologTest {
    
    public ImplicationalSystemWriterPrologTest() {
        ImplicationalSystemWriterProlog.register();
    }
    
    

    /**
     * Comprueba que se registra con la extensión "pl" en ImplicationalSystemWriterFactory.
     */
    @Test
    public void testRegister() {
        ImplicationalSystemWriterProlog.register();
        assertNotNull(ImplicationalSystemWriterFactory.get("pl"));
    }

    /**
     * Test of write method, of class ImplicationalSystemWriterProlog.
     */
    @Test
    public void testWrite() throws Exception {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        TreeSet<Rule> rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
      
        File f = File.createTempFile("test", ".pl");
        BufferedWriter file = new BufferedWriter(new FileWriter(f));
        ImplicationalSystemWriterProlog.getInstance().write(system, file);
        
        assertTrue(f.exists());
    }


    /**
     * Test of getSystemInPrologFormat method, of class ImplicationalSystemWriterProlog.
     */
    @Test
    public void testGetSystemInPrologFormat() {
        final String newLine = System.lineSeparator();
        ImplicationalSystem system = new ImplicationalSystem();
        system.addAllElements(getNodes());
        TreeSet<Rule> rules = getImplications();
        for(Rule r : rules) {
            system.addRule(r);
        }
        
        String expSystempParsed = ":-dynamic(implication/2),dynamic(attributes/1),dynamic(dfimplied/1)." + newLine + newLine +
                                    "attributes([a,b,c,d,e])." + newLine +
                                    "implication([b],[c])." + newLine +
                                    "implication([a],[b])." + newLine;
        String systemParsed = ImplicationalSystemWriterProlog.getInstance().getSystemInPrologFormat(system);
        assertEquals(expSystempParsed, systemParsed);
        
    }

    /**
     * Comprueba que se parsean los nodos de un atributo con el formato {@code attributes([a,b,c].)}.
     */
    @Test
    public void testParseNodes() {
        SortedSet<Comparable> attributes = getNodes();
        
        String attributesParsedExpected = "attributes([a,b,c,d,e]).";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseNodes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
    }

    /**
     * Comprueba que se parsean los nodos de un atributo con el formato {@code attributes([a,b,c].)}.
     */
    @Test
    public void testParseOneNode() {
        SortedSet<Comparable> attributes = new ComparableSet();
        attributes.addAll(Arrays.asList("a"));
        
        String attributesParsedExpected = "attributes([a]).";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseNodes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
    }

    /**
     * Comprueba que se parsean los nodos de un atributo con el formato {@code attributes([a,b,c].)}.
     */
    @Test
    public void testParseEmptyNodes() {
        SortedSet<Comparable> attributes = new ComparableSet();
        
        String attributesParsedExpected = "attributes([]).";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseNodes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
    }

    /**
     * Comprueba que se parsean los nodos de un atributo con el formato {@code attributes([a,b,c].)}.
     */
    @Test
    public void testParseNullNodes() {
        SortedSet<Comparable> attributes = null;
        
        String attributesParsedExpected = "attributes([]).";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseNodes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
    }

    /**
     * Comprueba que se parsea las implicaciones de un sistema en el formato:<br/>
     * <p/>
     * {@code implication([a,b],[c]).}<br/>
     * {@code implication([a],[b,d]).}<br/>
     * {@code implication([b],[d]).}<br/>
     * {@code implication([d],[c]).}<br/>
     * <br/>
     * 
     */
    @Test
    public void testParseImplications() {        
        final String newLine = System.lineSeparator();
        SortedSet<Rule> implications = getImplications();
        
        String implicationsParsedExpected = "implication([b],[c])." + newLine
                                            + "implication([a],[b])." + newLine;
        String implicationsParsed = ImplicationalSystemWriterProlog.getInstance().parseImplications(implications);
        assertEquals(implicationsParsedExpected, implicationsParsed);
        
    }
    /**
     * Comprueba que se parsea las implicaciones de un sistema en el formato:<br/>
     * <p/>
     * {@code implication([a,b],[c]).}<br/>
     * <br/>
     * 
     */
    @Test
    public void testParseOneImplication() {        
        final String newLine = System.lineSeparator();
        SortedSet<Rule> implications = new ComparableSet();
        TreeSet premise1 = new TreeSet(Arrays.asList("a"));
        TreeSet conclusion1 = new TreeSet(Arrays.asList("b"));
        implications.add(new Rule(premise1, conclusion1));
        
        String implicationsParsedExpected = "implication([a],[b])." + newLine;
        String implicationsParsed = ImplicationalSystemWriterProlog.getInstance().parseImplications(implications);
        assertEquals(implicationsParsedExpected, implicationsParsed);
        
    }
    /**
     * Comprueba que cuando el conjunto de implicaciones es vacío, se devuelve una cadena vacía.
     */
    @Test
    public void testParseEmptyImplications() {        
        
        SortedSet<Rule> implications = new ComparableSet();
        String implicationsParsedExpected = "";
        String implicationsParsed = ImplicationalSystemWriterProlog.getInstance().parseImplications(implications);
        assertEquals(implicationsParsedExpected, implicationsParsed);
        
    }
    /**
     * Comprueba que cuando el conjunto de implicaciones es vacío, se devuelve una cadena vacía.
     */
    @Test
    public void testParseNullImplications() {        
        
        SortedSet<Rule> implications = null;
        String implicationsParsedExpected = "";
        String implicationsParsed = ImplicationalSystemWriterProlog.getInstance().parseImplications(implications);
        assertEquals(implicationsParsedExpected, implicationsParsed);
        
    }

    /**
     * Compruebas que se parsean los atributos en el formato {@code [a,b,c]}
     */
    @Test
    public void testParseAttributes() {
        SortedSet<Comparable> attributes = new ComparableSet();
        attributes.addAll(Arrays.asList("a","b","c","d","e"));
        
        String attributesParsedExpected = "[a,b,c,d,e]";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseAttributes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
        
    }
    /**
     * Compruebas que se parsean los atributos en el formato {@code [a,b,c]}
     */
    @Test
    public void testParseEmptyAttributes() {
        SortedSet<Comparable> attributes = new ComparableSet();
        attributes.addAll(new TreeSet());
        
        String attributesParsedExpected = "[]";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseAttributes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
        
    }
    /**
     * Compruebas que se parsean los atributos en el formato {@code [a,b,c]}
     */
    @Test
    public void testParseNullAttributes() {
        SortedSet<Comparable> attributes = null;
        String attributesParsedExpected = "[]";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseAttributes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
        
    }
    /**
     * Compruebas que se parsean los atributos en el formato {@code [a,b,c]}
     */
    @Test
    public void testParseOneAttribute() {
        SortedSet<Comparable> attributes = new ComparableSet();
        attributes.addAll(Arrays.asList("a"));
        String attributesParsedExpected = "[a]";
        String attributesParsed = ImplicationalSystemWriterProlog.getInstance().parseAttributes(attributes);
        assertEquals(attributesParsedExpected, attributesParsed);
        
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
