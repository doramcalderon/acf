package es.uma.pfc.implications.generator.exception;

/**
 * Excepción que se ha de lanzar cuando el número de nodos de un sistema es 0.
 * @since 1.0.0
 * @author Dora Calderón
 */
public class ZeroNodesException extends RuntimeException {

    public ZeroNodesException() {
        super();
    }

    public ZeroNodesException(String message) {
        super(message);
    }
    

}
