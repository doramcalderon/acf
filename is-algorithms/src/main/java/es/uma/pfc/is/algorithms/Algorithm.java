package es.uma.pfc.is.algorithms;

import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algoritmo aplicable a sistemas implicacionales.
 *
 * @author Dora Calderón
 */
public interface Algorithm {


    /**
     * Executes the algorithm with the implicational system in the input path.
     *
     * @param input Path of the file wich contains the implicational system.
     * @return Implicational System.
     */
    ImplicationalSystem execute(String input, AlgorithmOptions options);

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
     * Default output file path.
     *
     * @return File path.
     */
    String getDefaultOutputFileName();

    /**
     * Set the short name.
     *
     * @param shortName Shrot name.
     */
    void setShortName(String shortName);

}
