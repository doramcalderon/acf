package es.uma.pfc.is.algorithms;

import fr.kbertet.lattice.ImplicationalSystem;

/**
 * Algoritmo genérico que recibe como entrada una ruta de un fichero y como salida un sistema implicacional.
 * @author Dora Calderón
 */
public abstract class GenericAlgorithm implements Algorithm<String, ImplicationalSystem> {

    /**
     * Ejecuta un algoritmo tomando como entrada la ruta de un fichero con un sistema implicacional y como salida
     * un sistema implicacional.
     * @param input Ruta de fichero que contiene un sistema implicacional.
     * @return Sistema implicacional.<b/>
     * @throws AlgorithmException Si se produce algún error.
     */
    @Override
    public ImplicationalSystem execute(String input) {
        try {
            return execute(new ImplicationalSystem(input));
        } catch (Exception ex) {
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        }
    }

    public abstract ImplicationalSystem execute(ImplicationalSystem system);

    
}
