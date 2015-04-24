/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms.util;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import java.io.File;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class SystemProperties {
    public static final String PERFORMANCE_FILE = "performanceFile";
    public static final String STATISTICS_FILE = "statisticsFile";
    public static final String TRACE_FILE = "traceFile";

    /**
     * Constructor.
     */
    private SystemProperties() {
    }
    
    /**
     * Establece la propiedad del fichero de salida de traza según el modo de ejecución.
     * @param mode Modo.
     * @param dir Directorio.
     * @param fileName Nombre de archivo de salida.
     */
    public static void setOutputFile(Mode mode, String dir, String fileName) {
        if(dir == null || dir.isEmpty() || fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("El directorio o el archivo es vacío.");
        }
        //String path = dir + File.separator + fileName;
        String path = dir;
        if(!dir.endsWith("\\")) {
            path = path.concat(File.separator);
        } 
        path = path.concat(fileName);
        
        switch (mode) {
            case PERFORMANCE:
                System.setProperty(PERFORMANCE_FILE, path);
                break;
            case STATISTICS:
                System.setProperty(STATISTICS_FILE, path);
                break;
            case TRACE:
                System.setProperty(TRACE_FILE, path);
                break;
            default:
                System.out.println("Modo no encontrado");
        }
    }

}
