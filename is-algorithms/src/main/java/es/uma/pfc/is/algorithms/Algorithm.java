package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algoritmo aplicable a sistemas implicacionales.
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
     * Nombre.
     *
     * @return Nombre.
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name Name.
     */
    void setName(String name);

    /**
     * Short name.
     *
     * @return Short name.
     */
    String getShortName();

   
    /**
     * Set the short name.
     *
     * @param shortName Shrot name.
     */
    void setShortName(String shortName);
    
    /**
     * Logger.
     * @return AlgorithmLogger. 
     */
    AlgorithmLogger getLogger();

}
