package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.commons.i18n.Messages;
import java.util.ResourceBundle;

/**
 * I18n AlgMessages Keys.
 *
 * @author Dora Calderón
 */
public class AlgMessages extends Messages {

    public static final String PERFORMANCE_INIT = "performance.init";
    public static final String PERFORMANCE_END = "performance.end";
    public static final String PERFORMANCE_TOTAL = "performance.total";
    public static final String PERFORMANCE_CPU = "performance.cpu";
    
    public static final String EXECUTING = "alg.executing";
    public static final String REDUCE = "alg.reduce";
    public static final String REMOVE_RULE = "alg.remove.rule";
    public static final String ADD_RULE = "alg.add.rule";
    public static final String REPLACE_RULE = "alg.replace.rule";
    
    public static final String END_OF = "alg.end";
    public static final String START_OF = "alg.start";
    /**
     * Single instance.*
     */
    private static AlgMessages me;
    /**
     * Bundle.
     */
    private final ResourceBundle messages;

    /**
     * Constructor.
     */
    private AlgMessages() {
        messages = ResourceBundle.getBundle("es.uma.pfc.is.algorithms.loggingMessages");
    }

    /**
     * Gets a single instance.
     * @return AlgMessages instance.
     */
    public static AlgMessages get() {
        synchronized (AlgMessages.class) {
            if (me == null) {
                me = new AlgMessages();
            }
        }
        return me;
    }


    @Override
    public ResourceBundle getBundle() {
        return messages;
    }
}
