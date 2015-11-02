package es.uma.pfc.is.algorithms.exceptions;

/**
 * Algorithm execution error.
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
     * @param message Message.
     */
    public AlgorithmException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message Message.
     * @param cause Cause.
     */
    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
    

}
