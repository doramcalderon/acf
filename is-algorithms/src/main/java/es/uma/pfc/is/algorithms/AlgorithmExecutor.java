
package es.uma.pfc.is.algorithms;

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
    public <I,O> O execute(Algorithm<I, O> alg) {
        O output = run(alg);
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
    protected <I,O> O run(Algorithm<I, O> alg) {
        O output = null;
        if(alg != null) {
            output = alg.execute();
        }
        return output;
    }
    
    /**
     * Finaliza la ejecución y libera recursos.
     */
    protected void stop() {
        
    }
    
}
