
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.exceptions.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

/**
 * Opciones de ejecución de un algoritmo.
 * @author Dora Calderón
 */
public class AlgorithmOptions {
    /**
     * Modos de ejecución.
     */
    public enum Mode {
        /** Se miden los tiempos de ejecuión.*//** Se miden los tiempos de ejecuión.*/
        PERFORMANCE, 
        /** Se guardan estadísticas.*/
        STATISTICS, 
        /** Se genera traza.*/
        HISTORY}
    public enum Options {
        /** Entrada del algoritmo.**/
        INPUT,
        /** Directorio de salida.**/
        OUTPUT,
        /** Tipo de archivos de salida. **/
        OUTPUT_TYPE,
        /**
         * Nombre base de los archivos del logger.
         */
        LOG_BASE_NAME,
    }
    
    /**
     * Opciones.
     */
    private final Map<String, Object> options;

    public AlgorithmOptions() {
        options = new HashMap();
    }
    
    
    /**
     * Devuelve el valor de una opción.
     * @param <T> Tipo del valor.
     * @param algOptions Opciones de algoritmo.
     * @param key Nombre.
     * @return Valor de la opción. <br/> {@code null} si no existe.
     * @throws ClassCastException si el tipo de la opción no es T.
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
     * Añade una nueva opción.
     * @param key Clave.
     * @param value Valor.
     * @return Valor anterior.
     * @throws InvalidKeyException Cuando la clave es nula o vacía.
     */
    public Object addOption(String key, Object value) {
        checkKey(key);
        return options.put(key, value);
    }
    /**
     * Añade una nueva opción.
     * @param option Nombre de la opción.
     * @param value Valor.
     * @return Valor anterior.
     * @throws InvalidKeyException Cuando la clave es nula o vacía.
     */
    public Object addOption(Options option, Object value) {
        return options.put(option.toString(), value);
    }
    
    /**
     * Elimina una nueva opción.
     * @param key Clave.
     * @return Valor eliminado.
     */
    public Object removeOption(String key) {
        checkKey(key);
        return options.remove(key);
    }
    /**
     * Devuelve el valor de una opción.
     * @param <T> Tipo del valor.
     * @param key Nombre.
     * @return Valor de la opción. <br/> {@code null} si no existe.
     * @throws ClassCastException si el tipo de la opción no es T.
     */
    public <T> T getOption(String key) {
        checkKey(key);
        return (T) options.get(key);
    }
    /**
     * Devuelve el valor de una opción.
     * @param <T> Tipo del valor.
     * @param key Nombre.
     * @return Valor de la opción. <br/> {@code null} si no existe.
     * @throws ClassCastException si el tipo de la opción no es T.
     */
    public <T> T getOption(Options key) {
        return (T) options.get(key.toString());
    }
    /**
     * Habilita un modo de ejecución.
     * @param mode Modo de ejecución.
     */
    public void enable(Mode mode) {
        checkMode(mode);
        options.put(mode.toString(), true);
        
    }
    /**
     * Deshabilita un modo de ejecución.
     * @param mode Modo de ejecución.
     */
    public void disable(Mode mode) {
        checkMode(mode);
        options.put(mode.toString(), false);
    }
    /**
     * Número de opciones.
     * @return Nümero de opciones.
     */
    public int optionsSize() {
        return options.size();
    }
  
    /**
     * Si un modo de ejecución está habilitado.
     * @param mode Modo de ejecución.
     * @return {@code true} si está habilitado, {@code false} en otro caso.
     */
    public boolean isEnabled(Mode mode) {
        checkMode(mode);
        return Boolean.TRUE.equals(options.get(mode.toString()));
    }
    
    /**
     * Limpia la configuración.
     */
    public void clear() {
        options.clear();
    }
  
    /**
     * Comprueba si la clave es válida.
     * @param key Clave.
     * @throws InvalidKeyException Cuando la clave es nula o vacía.
     */
    protected void checkKey(String key) {
         if(key == null || key.isEmpty()) {
            throw new InvalidKeyException("The key can't be empty or null.");
        }
    }
    
     /**
     * Comprueba si el modo es válido.
     * @param mode Modo.
     * @throws InvalidKeyException Cuando el modo es nulo.
     */
    protected void checkMode(Mode mode) {
        if(mode == null) {
            throw new InvalidKeyException("The mode can't be null");
        }
    }
  

}
