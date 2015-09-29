
package es.uma.pfc.is.algorithms.optbasis;

import es.uma.pfc.is.algorithms.GenericAlgorithm;
import fr.kbertet.lattice.ImplicationalSystem;

/**
 *
 * @author Dora Calderón
 */
public class ClaAlgorithm extends GenericAlgorithm {

    @Override
    public ImplicationalSystem execute(ImplicationalSystem input) {
        ImplicationalSystem directOptimalBasis = new ImplicationalSystem(input);
        
        try {
            directOptimalBasis = SimplificationLogic.reduce(directOptimalBasis, getLogger());
            directOptimalBasis = SimplificationLogic.strongSimplification(directOptimalBasis, getLogger());
            directOptimalBasis = SimplificationLogic.composition(directOptimalBasis, getLogger());
            directOptimalBasis = SimplificationLogic.optimize(directOptimalBasis, getLogger());
            
        } finally {
            getLogger().flush();
            getLogger().freeResources();
        }
        
        return directOptimalBasis;
    }

    @Override
    public String getShortName() {
        return "cla";
    }

    @Override
    public String getName() {
        return "CLA";
    }


}
