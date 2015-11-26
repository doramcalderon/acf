package es.uma.pfc.implications.generator;

import es.uma.pfc.implications.generator.model.ImplicationsModel;
import fr.kbertet.lattice.ImplicationalSystem;
import fr.kbertet.lattice.Rule;
import fr.kbertet.util.ComparableSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test for ImplicationsFactory methods.
 * @author Dora Calderón.
 */
public class ImplicationsFactoryTest {
    
    public ImplicationsFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getImplicationalSystem method, of class ImplicationsFactory.
     */
    @Test
    public void testGetImplicationalSystem_int_int() {
        int nodesNumber = 10;
        int rulesNumber = 5;
        
        ImplicationalSystem result = ImplicationsFactory.getImplicationalSystem(nodesNumber, rulesNumber);
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);
    }

    /**
     * Comprueba que se genera un conjunto de implicaciones aleatorio con el número de nodos y reglas indicado.
     */
    @Test
    public void testGetImplicationalSystem_ImplicationsModel() {
        
        int nodesNumber = 10;
        int rulesNumber = 5;
        ImplicationsModel model = new ImplicationsModel(nodesNumber, rulesNumber);
        
        
        ImplicationalSystem result = ImplicationsFactory.getImplicationalSystem(model);
        
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);        
        
    }

    /**
     * Comprueba que se genera un conjunto de implicaciones aleatorio, en las que dichas implicaciones no
     * supera la longitud indicada, tanto en la premisa como en la conclusión.
     */
    @Test
    public void testGetImplicationalSystem_ImplicationsModel_WithMax() {
        
        int nodesNumber = 10;
        int rulesNumber = 5;
        int maxPremisesLength = 3;
        int maxConclusionsLength = 4;
        ImplicationsModel model = new ImplicationsModel(nodesNumber, rulesNumber);
        model.setMaxPremiseLength(maxPremisesLength);
        model.setMaxConclusionLength(maxConclusionsLength);
        
        ImplicationalSystem result = ImplicationsFactory.getImplicationalSystem(model);
        
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);
        
        for(Rule rule : result.getRules()) {
            assertTrue(rule.getPremise().size() <= maxPremisesLength);
            assertTrue(rule.getConclusion().size() <= maxConclusionsLength);
        }
    }

    /**
     * Comprueba que se genera un conjunto de implicaciones aleatorio, en el que el número de atributos de las premisas
     * y las conclusiones se encuentran en el rango establecido.
     */
    @Test
    public void testGetImplicationalSystem_ImplicationsModel_WithMin() {
        
        int nodesNumber = 10;
        int rulesNumber = 5;
        int minPremisesLength = 1;
        int maxPremisesLength = 3;
        int minConclusionsLength = 4;
        int maxConclusionsLength = 4;
        
        ImplicationsModel model = new ImplicationsModel(nodesNumber, rulesNumber);
        model.setMinPremiseLength(minPremisesLength);
        model.setMaxPremiseLength(maxPremisesLength);
        model.setMinConclusionLength(minConclusionsLength);
        model.setMaxConclusionLength(maxConclusionsLength);
        
        ImplicationalSystem result = ImplicationsFactory.getImplicationalSystem(model);
        
        assertNotNull(result);
        assertTrue(result.sizeElements() == nodesNumber);
        assertTrue(result.sizeRules() == rulesNumber);
        
        for(Rule rule : result.getRules()) {
            int premiseSize = rule.getPremise().size();
            assertTrue(premiseSize >= minPremisesLength && premiseSize <= maxPremisesLength);
            
            int conclusionSize = rule.getConclusion().size();
            assertTrue(conclusionSize >= minConclusionsLength && conclusionSize <= maxConclusionsLength);
        }
    }
    
    @Test
    public void testConlusion() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addElement("a");
        system.addElement("b");
        system.addElement("d");
        system.addElement("e");
        system.addElement("f");
        system.addElement("g");
        
        Integer minLength = 2;
        Integer maxLength = 5;
        
        ComparableSet conclusions = ImplicationsFactory.getConclusion(system, minLength, maxLength);
        
        assertNotNull(conclusions);
        assertTrue(conclusions.size() >= 2 && conclusions.size() <= 5);
    }
    @Test
    public void testPremise() {
        ImplicationalSystem system = new ImplicationalSystem();
        system.addElement("a");
        system.addElement("b");
        system.addElement("d");
        system.addElement("e");
        system.addElement("f");
        system.addElement("g");
        
        Integer minLength = 2;
        Integer maxLength = 5;
        
        ComparableSet premises = ImplicationsFactory.getPremise(system, minLength, maxLength);
        
        assertNotNull(premises);
        assertTrue(premises.size() >= 2 && premises.size() <= 5);
    }
    
    @Test
    public void testGetRandomInt() {
        int maxValue = 5;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random >= 0 && random <= maxValue);
    }
    @Test
    public void testGetRandomIntMaxValueZero() {
        int maxValue = 0;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random == 0);
    }
//    @Test
    public void testGetRandomIntLessThanZero() {
        int maxValue = -2;
        int random = ImplicationsFactory.getRandomInt(maxValue);
        assertTrue(random <= 0 && random > maxValue);
    }
    
    @Test
    public void testGetRandomIntMinMax() {
        int minValue = 2;
        int maxValue = 5;
        int random = ImplicationsFactory.getRandomInt(minValue, maxValue);
        assertTrue(random >= minValue && random <= maxValue);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomIntMinGreaterThanMax() {
        int minValue = 2;
        int maxValue = 0;
        ImplicationsFactory.getRandomInt(minValue, maxValue);
        fail("Se esperaba IllegalArgumentException");
    }
        }
