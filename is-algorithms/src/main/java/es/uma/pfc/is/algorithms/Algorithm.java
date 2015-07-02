

package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
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
    public void reset();
    /**
     * Ejecuta el algoritmo.
     * @param input Entrada del algoritmo.
     */
    public O execute();
    
    /**
     * Establece la entrada del algoritmo.
     * @param fileName Path del fichero que contiene la entrada.
     * @return Algoritmo con la entrada establecida.
     * @throws InvalidPathException Cuando el fichero no existe.
     */
    public Algorithm input(String fileName);
    /**
     * Establece la entrada del algoritmo.
     * @param fileStream InputStream que contiene la entrada.
     * @return Algoritmo con la entrada establecida.
     */
    public Algorithm input(InputStream fileStream);
    /**
     * Establece la entrada del algoritmo.
     * @param input Entrada del algoritmo.
     * @return Algoritmo con la entrada establecida.
     */
    public Algorithm input(I input);
    
    /**
     * Añade un destino en el que se guardará el resultado.
     * @param fileName Path del fichero de destino.
     * @return Algoritmo con una salida añadida.
     * @throws InvalidPathException Cuando el fichero no existe.
     */
//    public Algorithm traceOutput(String fileName);
    /**
     * Añade un destino en el que se guardará el resultado.
     * @param mode Modo.
     * @param outputStream Destino del resultado.
     * @param closeAtFinish Si se cierra al teminar la ejecución del algoritmo.
     * @return Algoritmo con una salida añadida.
     */
    public Algorithm traceOutput(Mode mode, OutputStream outputStream);

    public Algorithm traceOutputs(Map<Mode, List<PrintStream>> outputs);
    
    public Algorithm output(String file);
    
    /**
     * Habilita un modo de ejecución.
     * @param mode Modo.
     * @return Algoritmo con el modo de ejecución {@code mode} habilitado.
     */
    public GenericAlgorithm enable(AlgorithmOptions.Mode mode);
    
    /**
     * Deshabilita un modo de ejecución.
     * @param mode Modo.
     * @return Algoritmo con el modo de ejecución {@code mode} deshabilitado.
     */
    public GenericAlgorithm disable(AlgorithmOptions.Mode mode);
    
    /**
     * Añade una opción de ejecución.
     * @param name Nombre de la opción.
     * @param value Valor.
     * @return Algoritmo con una opción de ejecución añadida.
     */
    public GenericAlgorithm option(String name, Object value);
    
    /**
     * Nombre.
     * @return Nombre. 
     */
    public String getName();
    /**
     * Set the name.
     * @return Name. 
     */
    public void setName(String name);
    /**
     * Short name.
     * @return Short name.
     */
    public String getShortName();
    /**
     * Set the short name.
     * @param shortName Shrot name.
     */
    public void setShortName(String shortName);
}
