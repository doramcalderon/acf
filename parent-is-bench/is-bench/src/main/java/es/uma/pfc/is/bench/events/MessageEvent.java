package es.uma.pfc.is.bench.events;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class MessageEvent {
    public enum Level {INFO, SUCCEEDED, WARNING, ERROR};
    /**
     * Message.
     */
    private String message;
    /**
     * Level.
     */
    private Level level;

    public MessageEvent(String message) {
        this.message = message;
        this.level = Level.INFO;
    }

    /**
     * Constructor.
     * @param message Message.
     * @param level Level.
     */
    public MessageEvent(String message, Level level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    
    
    
}
