
package es.uma.pfc.is.bench.algorithms;

import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of {@link AlgorithmsModel}
 * @author Dora Calderón
 */
public class AlgorithmsModelTest {
    
    public AlgorithmsModelTest() {
    }

    /**
     * Test of getName method, of class AlgorithmsModel.
     */
    @Test
    public void testGetSetName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        alg.setName("Alice");
        assertEquals("Alice", alg.getName());
    }
    @Test
    public void testGetNullName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        assertEquals(null, alg.getName());
    }


    /**
     * Test of getShortName method, of class AlgorithmsModel.
     */
    @Test
    public void testGetSetShortName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        alg.setShortName("Al");
        assertEquals("Al", alg.getShortName());
    }
     @Test
    public void testGetNullShortName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        assertEquals(null, alg.getShortName());
    }


    /**
     * Test of getClassName method, of class AlgorithmsModel.
     */
    @Test
    public void testGetSetClassName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        alg.setClassName("es.uma.pf.AlgImpl");
        assertEquals("es.uma.pf.AlgImpl", alg.getClassName());
    }

   @Test
    public void testGetNullClassName() {
        AlgorithmsModel alg = new AlgorithmsModel();
        assertEquals(null, alg.getClassName());
    }

    @Test
    public void testNameBinding() {
        AlgorithmsModel alg = new AlgorithmsModel();
        
        SimpleStringProperty binding = new SimpleStringProperty("Alice");
        binding.bind(alg.getNameProperty());
        
        assertEquals(alg.getName(), binding.getValue());
    }
    

    @Test
    public void testNameBidirectBinding() {
        AlgorithmsModel alg = new AlgorithmsModel();
        
        SimpleStringProperty binding = new SimpleStringProperty("Alice");
        binding.bindBidirectional(alg.getNameProperty());
        
        assertEquals(alg.getName(), binding.getValue());
    }
    
}
