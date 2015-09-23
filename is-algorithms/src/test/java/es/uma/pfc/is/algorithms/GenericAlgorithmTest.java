/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.algorithms;

import fr.kbertet.lattice.ImplicationalSystem;
import org.junit.Test;

/**
 *
 * @author Dora Calderón
 */
public class GenericAlgorithmTest {
    
    public GenericAlgorithmTest() {
    }

     
    
    
    
    /**
     * Test of execute method, of class GenericAlgorithm.
     */
    @Test
    public void testExecute_ImplicationalSystem() {
    }

    public class GenericAlgorithmImpl extends GenericAlgorithm {
            @Override
            public ImplicationalSystem execute(ImplicationalSystem system) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getName() {
                return "GenericAlgImpl";
            }

        @Override
        public String getShortName() {
            return "do";
        }
            
                
    }
    

  
}
