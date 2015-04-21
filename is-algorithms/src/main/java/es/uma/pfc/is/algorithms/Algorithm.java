

package es.uma.pfc.is.algorithms;

/**
 * Algoritmo aplicable a sistemas implicacionales.
 * @author Dora Calderón
 * @param <I> Tipo de la entrada.
 * @param <O> Tipo de la salida.
 */
public interface Algorithm <I, O> {
    /**
     * Ejecuta el algoritmo.
     * @param input Entrada del algoritmo.
     */
    public O execute(I input);
    
    /**
     * Nombre.
     * @return Nombre. 
     */
    public String getName();

}
