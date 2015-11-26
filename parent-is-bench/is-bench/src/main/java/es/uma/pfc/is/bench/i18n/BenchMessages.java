
package es.uma.pfc.is.bench.i18n;

import es.uma.pfc.is.commons.i18n.Messages;
import java.util.ResourceBundle;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchMessages extends Messages {
    /**
    * Empty values validation message.
    */
    public static final String EMPTY_VALUES = "msg.validation.empty.values";
    /**
     * Algorithm exists validation message.
     */
    public static final String ALGORITHM_EXISTS = "msg.algorithm.exists";
    /**
     * Implementation error.
     */
    public static final String MSG_NOT_IMPLEMENTATION = "msg.algorithm.error.implementation";
    /**
     * Class not exists.
     */
    public static final String CLASS_NOT_EXISTS = "msg.algorithm.error.classnotexists";
    /**
     * Empty class error.
     */
    public static final String EMPTY_CLASS = "msg.algorithm.error.emptyclass";
    /**
     * Algorithm name attribute.
     */
    public static final String ALGORITHM_NAME = "msg.algorithm.name";
    /**
     * Algorithm short name attribute.
     */
    public static final String ALGORITHM_SHORT_NAME = "msg.algorithm.shortname";
    /**
     * Empty algorithm name.
     */
    public static final String EMPTY_ALGORITHM_NAME = "msg.algorithm.empty.name";
    /**
     * Empty algorithms list.
     */
    public static final String EMPTY_ALGORITHM_LIST = "msg.algorithm.empty.algorithms";
    /**
     * Empty algorithms list.
     */
    public static final String DUPLICATED_BENCHMARK = "msg.benchmarks.duplicated";
    /**
     * Creation benchmark error.
     */
    public static final String BENCHMARK_CREATION_ERROR = "msg.benchmarks.creation.error";
    /**
     * Creation benchmark error.
     */
    public static final String BENCHMARK_CREATION_SUCCEEDED = "msg.benchmarks.creation.succeeded";
    /**
     * Deleted inputs files confirmation.
     */
    public static final String DELETE_INPUTS_CONFIRM = "msg.benchmarks.delete.inputs.confirm";
    /**
     * Algorithm execution time.
     */
    public static final String ALGORITHM_EXECUTION_TIME = "msg.results.execution.time";
    /**
     * Algorithm Results viewer title.
     */
    public static final String RESULTS_VIEWER_TITLE = "msg.results.viewer.title";
    /**
     * IO error.
     */
    public static final String COPYING_JARS_ERROR = "msg.copy.jars.error";
    /**
     * File exists title.
     */
    public static final String FILE_EXISTS_TITLE = "msg.library.exists.title";
    /**
     * File exists.
     */
    public static final String FILE_EXISTS = "msg.library.exists";
    /**
     * Remove libraries dialog title.
     */
    public static final String REMOVE_LIBRARIES_TITLE = "msg.library.remove.title";
    /**
     * Remove libraries confirmation.
     */
    public static final String CONFIRM_REMOVE_LIBRARIES = "msg.library.remove.confirm";
    /**
     * Language change confirmation title.
     */
    public static final String LANGUAGE_CHANGE_TITLE = "msg.language.change.title";
    /**
     * * Language change confirmation message.
     */
    public static final String LANGUAGE_CHANGE = "msg.language.change";
            
            
/**
     * Single instance.*
     */
    private static BenchMessages me;
    /**
     * Bundle.
     */
    protected final ResourceBundle messages;
    

    /**
     * Constructor.
     */
    private BenchMessages() {
        messages = ResourceBundle.getBundle("bundles.messages");
    }
    
    public static BenchMessages get() {
        if(me == null) {
            me = new BenchMessages();
        }
        return me;
    }
    
    @Override
    public ResourceBundle getBundle() {
        return messages;
    }

}
