
package es.uma.pfc.is.algorithms.exceptions;

/**
 * Invalida path exception.
 * @author Dora Calderón
 */
public class InvalidPathException extends RuntimeException {

    /**
     * Constructor.
     * @param message Message.
     */
    public InvalidPathException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message Message.
     * @param cause Cause.
     */
    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }

}
