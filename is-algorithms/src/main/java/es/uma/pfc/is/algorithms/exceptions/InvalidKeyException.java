package es.uma.pfc.is.algorithms.exceptions;

/**
 * Invalid key exception.
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
     * @param message Message.
     */
    public InvalidKeyException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message Message.
     * @param cause Cause.
     */
    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause Cause. 
     */
    public InvalidKeyException(Throwable cause) {
        super(cause);
    }
    

}
