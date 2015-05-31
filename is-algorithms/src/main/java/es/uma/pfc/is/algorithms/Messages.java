package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.util.ResourceBundle;

/**
 * I18n Messages Keys.
 *
 * @author Dora Calderón
 */
public class Messages {

    public static final String PERFORMANCE_INIT = "performance.init";
    public static final String PERFORMANCE_END = "performance.end";
    public static final String PERFORMANCE_TOTAL = "performance.total";
    public static final String PERFORMANCE_CPU = "performance.cpu";
    
    public static final String EXECUTING = "alg.executing";
    public static final String REDUCE = "alg.reduce";
    public static final String REMOVE_RULE = "alg.remove.rule";
    public static final String ADD_RULE = "alg.add.rule";
    public static final String REPLACE_RULE = "alg.replace.rule";
    /**
     * Single instance.*
     */
    private static Messages me;
    /**
     * Bundle.
     */
    private final ResourceBundle messages;

    /**
     * Constructor.
     */
    private Messages() {
        messages = ResourceBundle.getBundle("es.uma.pfc.is.algorithms.loggingMessages");
    }

    /**
     * Get a single instance.
     * @return Messages instance.
     */
    public static Messages get() {
        synchronized (Messages.class) {
            if (me == null) {
                me = new Messages();
            }
        }
        return me;
    }

    /**
     * Return a message with replaced arguments.
     *
     * @param messageKey Message key.
     * @param args Arguments.
     * @return Message.<br/> {@code messageKey} if doesn't exist.
     */
    public String getMessage(String messageKey, Object... args) {
        String message = null;
        if(messages.containsKey(messageKey)) {
            message = messages.getString(messageKey);
        }
        if (!StringUtils.isEmpty(message)) {
            message = StringUtils.replaceArgs(message, "{}", args);
        } else {
            message = messageKey;
        }
        return message;
    }
}
