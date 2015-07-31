

package es.uma.pfc.is.bench.exception;

/**
 * Error de configuración.
 * @author Dora Calderón
 */
public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
