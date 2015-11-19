
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.exceptions.InvalidKeyException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Algorithm execution options.
 * @author Dora Calderón
 */
public class AlgorithmOptions {
    /**
     * Execution modes.
     */
    public enum Mode {
        /** Execution times are measured. *//** Execution times are measured. */
        PERFORMANCE, 
        /** Statistics are saved.*/
        STATISTICS, 
        /** Trace is generated.*/
        TRACE}
    public enum Options {
        /** Algorithm input.**/
        INPUT,
        /** Output dir.**/
        OUTPUT,
        /** Output file types. **/
        OUTPUT_TYPE,
        /**
         * Nombre base de los archivos del logger.
         */
        LOG_BASE_NAME,
    }
    
    /**
     * Options.
     */
    private final Map<String, Object> options;

    /**
     * Constructor.
     */
    public AlgorithmOptions() {
        options = new HashMap();
    }
    
    
    /**
     * Returns an option value.
     * @param <T> Value type.
     * @param algOptions Algorihtm options.
     * @param key Option name.
     * @return Option value. <br/> {@code null} if no exists.
     * @throws ClassCastException if the type option not is T.
     */
    public static <T> T getOption(AlgorithmOptions algOptions, String key) {
        T optionValue = null;
        if (algOptions != null) {
            if(key == null || key.isEmpty()) {
                throw new InvalidKeyException("The key can't be empty or null.");
            }
            optionValue = (T) algOptions.getOption(key);
        }
        return optionValue;
    }
    
    /**
     * Add a new option.
     * @param key Key.
     * @param value Value.
     * @return Previous value.
     * @throws InvalidKeyException When the key is null or empty.
     */
    public Object addOption(String key, Object value) {
        checkKey(key);
        return options.put(key, value);
    }
    /**
     * Add a new option
     * @param option Option name.
     * @param value Value.
     * @return Previous value.
     * @throws InvalidKeyException when the key is null or empty.
     */
    public Object addOption(Options option, Object value) {
        return options.put(option.toString(), value);
    }
    
    /**
     * Removes an option.
     * @param key Key.
     * @return Deleted value.
     */
    public Object removeOption(String key) {
        checkKey(key);
        return options.remove(key);
    }
    /**
     * Returns an option value.
     * @param <T> Value type.
     * @param key Option name.
     * @return Value option. <br/> {@code null} if not exists.
     * @throws ClassCastException when the type option not is T.
     */
    public <T> T getOption(String key) {
        checkKey(key);
        return (T) options.get(key);
    }
    /**
     * Returns an option value.
     * @param <T> Value type.
     * @param key Option name.
     * @return Value option. <br/> {@code null} if not exists.
     * @throws ClassCastException when the type option not is T.
     */
    public <T> T getOption(Options key) {
        return (T) options.get(key.toString());
    }
    /**
     * Enables an execution mode.
     * @param mode Execution mode.
     */
    public void enable(Mode mode) {
        checkMode(mode);
        options.put(mode.toString(), true);
        
    }
    /**
     * Disables an execution mode.
     * @param mode Execution mode.
     */
    public void disable(Mode mode) {
        checkMode(mode);
        options.put(mode.toString(), false);
    }
    /**
     * Options size.
     * @return Options size.
     */
    public int optionsSize() {
        return options.size();
    }
  
    /**
     * If an execution mode is enabled.
     * @param mode Execution mode.
     * @return {@code true} if is enabled, {@code false} otherwise.
     */
    public boolean isEnabled(Mode mode) {
        checkMode(mode);
        return Boolean.TRUE.equals(options.get(mode.toString()));
    }
    
    /**
     * Reset the configuration.
     */
    public void clear() {
        options.clear();
    }
  
    /**
     * Checks if the key is valid.
     * @param key Key.
     * @throws InvalidKeyException When the key is empty or null.
     */
    protected void checkKey(String key) {
         if(key == null || key.isEmpty()) {
            throw new InvalidKeyException("The key can't be empty or null.");
        }
    }
    
     /**
     * Checks if the mode is valid.
     * @param mode Mode.
     * @throws InvalidKeyException When the mode is null.
     */
    protected void checkMode(Mode mode) {
        if(mode == null) {
            throw new InvalidKeyException("The mode can't be null");
        }
    }
  

}
