package es.uma.pfc.is.algorithms.exceptions;

/**
 * Excepción de clave inválida. 
 * @author Dora Calderón
 */
public class InvalidKeyException extends RuntimeException {

    /**
     * Constructor.
     */
    public InvalidKeyException() {
    }

    /**
     * Constructor.
     * @param message Mensaje.
     */
    public InvalidKeyException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message Mensaje.
     * @param cause Causa.
     */
    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause Causa. 
     */
    public InvalidKeyException(Throwable cause) {
        super(cause);
    }
    

}
