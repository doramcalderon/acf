
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;

/**
 * Servicio que ejecuta un algoritmo.
 * @author Dora Calderón
 */
public class AlgorithmExecutor {

    /**
     * Ejecuta un algoritnmo pasando por tres fases: inicialización, ejecución, finalización.
     * @param <I> Tipo de entrada del algoritmo.
     * @param <O> Tipo de salida del algoritmo.
     * @param alg Algoritmo.
     * @param options Opciones de ejecución.
     * @return Resultado de la ejecución.
     */
    public <I,O> O execute(Algorithm<I, O> alg, AlgorithmOptions options) {
        init(options);
        O output = run(alg, options);
        stop();
        return output;
    }
    /**
     * Inicializa
     * @param options 
     */
    protected void init(AlgorithmOptions options) {
        
    }
    /**
     * Ejecuta el algoritmo.
     * @param <I> Tipo de entrada.
     * @param <O> Tipo de salida.
     * @param alg Algoritmo.
     * @param options Opciones.
     * @return Resultado del algoritmo.
     */
    protected <I,O> O run(Algorithm<I, O> alg, AlgorithmOptions options) {
        O output = null;
        if(alg != null) {
            I input = options.<I>getOption(Options.INPUT.toString());
            output = alg.execute(input);
        }
        return output;
    }
    
    /**
     * Finaliza la ejecución y libera recursos.
     */
    protected void stop() {
        
    }
    
}
