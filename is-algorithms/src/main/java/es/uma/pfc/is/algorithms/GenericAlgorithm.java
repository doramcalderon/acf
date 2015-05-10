package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import es.uma.pfc.is.logging.ISBenchLogger;
import es.uma.pfc.is.logging.PerformanceLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Algoritmo genérico que recibe como entrada una ruta de un fichero y como salida un sistema implicacional.
 *
 * @author Dora Calderón
 */
public abstract class GenericAlgorithm implements Algorithm<String, ImplicationalSystem> {

    private ISBenchLogger logger;
    private AlgorithmOptions options;
    
    private String input;
    private List < OutputStream > outputs;

    public GenericAlgorithm() {
        outputs = new ArrayList();
        options = new AlgorithmOptions();
        logger = new ISBenchLogger();
    }
    
    

    /**
     * Ejecuta un algoritmo tomando como entrada la ruta de un fichero con un sistema implicacional y como salida un
     * sistema implicacional.
     *
     * @param input Ruta de fichero que contiene un sistema implicacional.
     * @return Sistema implicacional.<b/>
     * @throws AlgorithmException Si se produce algún error.
     */
    public ImplicationalSystem execute(AlgorithmOptions options) {
        logger = new PerformanceLogger(options);
        return execute();
    }

    /**
     * Ejecuta un algoritmo tomando como entrada la ruta de un fichero con un sistema implicacional y como salida un
     * sistema implicacional.
     *
     * @param input Ruta de fichero que contiene un sistema implicacional.
     * @return Sistema implicacional.<b/>
     * @throws AlgorithmException Si se produce algún error.
     */
    public ImplicationalSystem execute(AlgorithmOptions options, OutputStream output) {
        this.options = options;
        logger = new ISBenchLogger();
        return execute();
    }

    public ImplicationalSystem execute() {
        try {
            logger.initOutputs(outputs);
            logger.setOptions(options);
            logger.startTime(new Date());
            ImplicationalSystem result = execute(new ImplicationalSystem(getInput()));
            logger.endTime(new Date());
            return result;
        } catch (Exception ex) {
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        }
    }

    @Override
    public GenericAlgorithm input(String fileName) {
        if (fileName == null || !Files.exists(Paths.get(fileName))) {
            throw new InvalidPathException("El fichero de entrada no existe.");
        }
        this.input = fileName;
        return this;
    }

    @Override
    public GenericAlgorithm input(InputStream fileStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GenericAlgorithm output(String fileName) {
        if(fileName != null && !fileName.isEmpty()) {
            try {
                output(new FileOutputStream(fileName, false));
            } catch (IOException ioEx) {
                throw new InvalidPathException("El fichero de salida no existe");
            }
        }
        
        return this;
    }

    @Override
    public GenericAlgorithm output(OutputStream outputStream) {
        getOutputs().add(outputStream);
        return this;
    }
    
    @Override
    public GenericAlgorithm enable(Mode mode) {
        this.options.enable(mode);
        return this;
    }

    @Override
    public GenericAlgorithm disable(Mode mode) {
        this.options.disable(mode);
        return this;
    }

    @Override
    public GenericAlgorithm option(String name, Object value) {
        this.options.addOption(name, value);
        return this;
    }

    @Override
    public void reset() {
        options.clear();
        input = null;
        outputs.clear();
    }
    
    /**
     * Logger.
     * @return Logger. 
     */
    protected ISBenchLogger getLogger() {
        return logger;
    }
    

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @return the outputs
     */
    public List < OutputStream > getOutputs() {
        return outputs;
    }
    
    public AlgorithmOptions getOptions() {
        return options;
    }
    

    public abstract ImplicationalSystem execute(ImplicationalSystem system);

}
