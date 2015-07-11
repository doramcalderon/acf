

package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Algoritmo aplicable a sistemas implicacionales.
 * @author Dora Calderón
 * @param <I> Tipo de la entrada.
 * @param <O> Tipo de la salida.
 */
public interface Algorithm <I, O> {
    /**
     * Limpia todas la configuración del algoritmo.
     */
    void reset();
    /**
     * Ejecuta el algoritmo.
     * @param input Entrada del algoritmo.
     */
    O execute();
    
    /**
     * Establece la entrada del algoritmo.
     * @param fileName Path del fichero que contiene la entrada.
     * @return Algoritmo con la entrada establecida.
     * @throws InvalidPathException Cuando el fichero no existe.
     */
    Algorithm input(String fileName);
    /**
     * Establece la entrada del algoritmo.
     * @param fileStream InputStream que contiene la entrada.
     * @return Algoritmo con la entrada establecida.
     */
    Algorithm input(InputStream fileStream);
    /**
     * Establece la entrada del algoritmo.
     * @param input Entrada del algoritmo.
     * @return Algoritmo con la entrada establecida.
     */
    Algorithm input(I input);
    
    /**
     * Añade un destino en el que se guardará el resultado.
     * @param fileName Path del fichero de destino.
     * @return Algoritmo con una salida añadida.
     * @throws InvalidPathException Cuando el fichero no existe.
     */
//    Algorithm traceOutput(String fileName);
    /**
     * Añade un destino en el que se guardará el resultado.
     * @param mode Modo.
     * @param outputStream Destino del resultado.
     * @return Algoritmo con una salida añadida.
     */
    Algorithm traceOutput(Mode mode, OutputStream outputStream);

    Algorithm traceOutputs(Map<Mode, List<PrintStream>> outputs);
    
    Algorithm output(String file);
    
    /**
     * Habilita un modo de ejecución.
     * @param mode Modo.
     * @return Algoritmo con el modo de ejecución {@code mode} habilitado.
     */
    GenericAlgorithm enable(AlgorithmOptions.Mode mode);
    
    /**
     * Deshabilita un modo de ejecución.
     * @param mode Modo.
     * @return Algoritmo con el modo de ejecución {@code mode} deshabilitado.
     */
    GenericAlgorithm disable(AlgorithmOptions.Mode mode);
    
    /**
     * Añade una opción de ejecución.
     * @param name Nombre de la opción.
     * @param value Valor.
     * @return Algoritmo con una opción de ejecución añadida.
     */
    GenericAlgorithm option(String name, Object value);
    
    /**
     * Nombre.
     * @return Nombre. 
     */
    String getName();
    /**
     * Set the name.
     */
    void setName(String name);
    /**
     * Short name.
     * @return Short name.
     */
    String getShortName();
    /**
     * Set the short name.
     * @param shortName Shrot name.
     */
    void setShortName(String shortName);
     /**
     * Default output file path.
     * @return String.
     */
    String getDefaultOutputPath();
}
