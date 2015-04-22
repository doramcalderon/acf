
package es.uma.pfc.is.algorithms;

/**
 * Error al ejecutar un algoritmo.
 * @author Dora Calderón
 */
public class AlgorithmException extends RuntimeException {

    /**
     * Constructor.
    */
    public AlgorithmException() {
    }

    /**
     * Constructor.
     * @param message Mensaje.
     */
    public AlgorithmException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message Mensaje.
     * @param cause Causa.
     */
    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
    

}
