package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algorithm of implicational system basis computation.
 *
 * @author Dora Calderón
 */
public interface Algorithm {

    /**
     * Executes the algorithm with the implicational system arg.
     *
     * @param input Input implicational system.
     * @return Implicational System.
     */
    ImplicationalSystem execute(ImplicationalSystem input);

    /**
     * Algorithm name.
     * @return Name.
     */
    String getName();

    /**
     * Sets the algorithm name.
     * @param name Name.
     */
    void setName(String name);

    /**
     * Algorithm short name.
     * @return Short name.
     */
    String getShortName();

   
    /**
     * Sets the algorithn short name.
     * @param shortName Shrot name.
     */
    void setShortName(String shortName);
    
    /**
     * Logger for trace generation.
     * @return AlgorithmLogger. 
     */
    AlgorithmLogger getLogger();

}
