
package es.uma.pfc.implications.generator.model;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class ImplicationsModelTest {

    /**
     * Test of getNodes method, of class ImplicationsModel.
     */
    @Test
    public void testGetSetNodes() {
        Integer nodes = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setNodes(nodes);

        assertEquals(nodes, model.getNodes());
    }

   

    /**
     * Test of getImplications method, of class ImplicationsModel.
     */
    @Test
    public void testGetSetImplications() {
        Integer implications = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setImplications(implications);

        assertEquals(implications, model.getImplications());
    }

    /**
     * Test of getMaxPremiseLength method, of class ImplicationsModel.
     */
    @Test
    public void testGetSetMaxPremiseLength() {
        Integer maxPremiseLength = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setMaxPremiseLength(maxPremiseLength);

        assertEquals(maxPremiseLength, model.getMaxPremiseLength());
    }

    @Test
    public void testValidatePremiseLength() {
        Integer minPremiseLength = 7;
        Integer maxPremiseLength = 10;

        ImplicationsModel model = new ImplicationsModel(5, 4);
        model.setMinPremiseLength(minPremiseLength);
        model.setMaxPremiseLength(maxPremiseLength);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.OK, validation.getResult());
    }

    @Test
    public void testFailValidatePremiseLength() {
        Integer minPremiseLength = 10;
        Integer maxPremiseLength = 7;

        ImplicationsModel model = new ImplicationsModel(5, 4);
        model.setMinPremiseLength(minPremiseLength);
        model.setMaxPremiseLength(maxPremiseLength);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.INVALID_PREMISE_LENGTH, validation.getResult());
    }

    /**
     * Test of getMinPremiseLength method, of class ImplicationsModel.
     */
    @Test
    public void testGetSetMinPremiseLength() {
        Integer minPremiseLength = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setMinPremiseLength(minPremiseLength);

        assertEquals(minPremiseLength, model.getMinPremiseLength());
    }

    @Test
    public void testValidateMinPremiseLength() {
        Integer minPremiseLength = 7;
        Integer maxPremiseLength = 10;

        ImplicationsModel model = new ImplicationsModel(5, 4);
        model.setMinPremiseLength(minPremiseLength);
        model.setMaxPremiseLength(maxPremiseLength);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.OK, validation.getResult());
    }

    @Test
    public void testFailValidateMinPremiseLength() {
        Integer minPremiseLength = 10;
        Integer maxPremiseLength = 7;

        ImplicationsModel model = new ImplicationsModel(5, 4);
        model.setMinPremiseLength(minPremiseLength);
        model.setMaxPremiseLength(maxPremiseLength);

        ResultModelValidation validation = model.validate();
        assertNotNull(validation);
        assertEquals(ResultValidation.INVALID_PREMISE_LENGTH, validation.getResult());
    }

    @Test
    public void testValidateMinConclusionLength() {
        Integer minConclusionLength = 7;
        Integer maxConclusionLength = 10;

        ImplicationsModel model = new ImplicationsModel(10, 4);
        model.setMinConclusionLength(minConclusionLength);
        model.setMaxConclusionLength(maxConclusionLength);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.OK, validation.getResult());
    }

    @Test
    public void testFailValidateMinConclusionLength() {
        Integer minConclusionLength = 10;
        Integer maxConclusionLength = 7;

        ImplicationsModel model = new ImplicationsModel(5, 4);
        model.setMinConclusionLength(minConclusionLength);
        model.setMaxConclusionLength(maxConclusionLength);

        ResultModelValidation validation = model.validate();
        assertNotNull(validation);
        assertEquals(ResultValidation.INVALID_CONCLUSION_LENGTH, validation.getResult());
    }
    @Test
    public void testValidateNodesLength() {
        Integer numNodes = 7;

        ImplicationsModel model = new ImplicationsModel(numNodes, 4);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.OK, validation.getResult());
    }
    @Test
    public void testFailValidateNodesLength() {
        Integer numNodes = null;

        ImplicationsModel model = new ImplicationsModel(numNodes, 4);

        ResultModelValidation validation = model.validate();
        
        assertNotNull(validation);
        assertEquals(ResultValidation.ZERO_NODES, validation.getResult());
    }

    

    /**
     * Test of getMaxConclusionLength method, of class ImplicationsModel.
     */
    @Test
    public void testGetSetMaxConclusionLength() {
        Integer conclusionLength = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setMaxConclusionLength(conclusionLength);

        assertEquals(conclusionLength, model.getMaxConclusionLength());
    }

    @Test
    public void testGetSetNum() {
        Integer num = 10;
        ImplicationsModel model = new ImplicationsModel();
        model.setNum(num);

        assertEquals(num, model.getNum());
    }

    @Test
    public void tesSetNullNum() {
        Integer num = null;
        ImplicationsModel model = new ImplicationsModel();
        model.setNum(num);

        assertEquals(new Integer(1), model.getNum());
    }

    @Test
    public void tesSetNegativeNum() {
        Integer num = -2;
        ImplicationsModel model = new ImplicationsModel();
        model.setNum(num);

        assertEquals(new Integer(1), model.getNum());
    }
}
