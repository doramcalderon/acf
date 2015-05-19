package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Mode;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
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
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Algoritmo genérico que recibe como entrada una ruta de un fichero y como salida un sistema implicacional.
 *
 * @author Dora Calderón
 */
public abstract class GenericAlgorithm implements Algorithm<String, ImplicationalSystem> {

    private ISBenchLogger logger;
    private AlgorithmOptions options;
    
    private String input;
    private Map<Mode, List < PrintStream >> outputs;

    public GenericAlgorithm() {
        outputs = new HashMap();
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
            logger.createStatisticLog(System.getProperty("isbench.output.dir") + File.separator + getName(), "Rule", "Old Size", "Current Size");
            logger.initOutputs(outputs);
            logger.setOptions(options);
            logger.startTime(new Date());
            ImplicationalSystem result = execute(new ImplicationalSystem(getInput()));
            logger.endTime(new Date());
            result.save(options.<String>getOption(Options.OUTPUT.toString()));
            return result;
        } catch (Exception ex) {
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        } finally {
            logger.freeResources();
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
    public GenericAlgorithm traceOutput(Mode mode, OutputStream outputStream) {
        List<PrintStream> outputs = getOutputs().get(mode);
        if(outputs == null) {
            outputs = new ArrayList();
        }
        outputs.add(new PrintStream(outputStream));
        this.outputs.put(mode, outputs);
        
        return this;
    }

    @Override
    public GenericAlgorithm traceOutputs(Mode mode, Collection<OutputStream> outputs) {
        if(outputs != null && !outputs.isEmpty()) {
            for(OutputStream os : outputs) {
                GenericAlgorithm.this.traceOutput(mode, os);
            }
        }
        return this;
    }

    @Override
    public Algorithm traceOutputs(Map<Mode, List<PrintStream>> outputs) {
        this.outputs = outputs;
        return this;
    }
    
    
    
    @Override
    public Algorithm output(String file) {
        this.options.addOption(Options.OUTPUT.toString(), file);
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
     * @return the traceOutputs
     */
    public Map<Mode, List < PrintStream >> getOutputs() {
        return outputs;
    }
    
    public AlgorithmOptions getOptions() {
        return options;
    }
    

    public abstract ImplicationalSystem execute(ImplicationalSystem system);

}
