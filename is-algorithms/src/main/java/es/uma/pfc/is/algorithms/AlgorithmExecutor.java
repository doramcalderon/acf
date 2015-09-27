package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import static es.uma.pfc.is.algorithms.AlgorithmOptions.Options.OUTPUT;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.io.ImplicationalSystemWriterProlog;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio que ejecuta un algoritmo.
 *
 * @author Dora Calderón
 */
public class AlgorithmExecutor {

    /**
     * Algorithm to execute.
     */
    private Algorithm algorithm;
 
    /**
     * Execution options.
     */
    private AlgorithmOptions options;

    /**
     * Path of inputs system.
     */
    private String[] inputs;

    /**
     * I18n messages.
     */
    protected AlgMessages messages;

    /**
     * Constructor.
     */
    public AlgorithmExecutor() {
        options = new AlgorithmOptions();
        messages = AlgMessages.get();
        ImplicationalSystemWriterProlog.register();
    }

    /**
     * Constructor.
     *
     * @param algorithm Algorithm to execute.
     */
    public AlgorithmExecutor(Algorithm algorithm) {
        this();
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        }
        this.algorithm = algorithm;
    }

    public List<AlgorithmResult> execute(Algorithm alg) {
        this.algorithm = alg;
        return execute();
    }

    /**
     * Ejecuta un algoritnmo pasando por tres fases: inicialización, ejecución, finalización.
     *
     * @param alg Algoritmo.
     * @return Resultado de la ejecución.
     */
    public  List<AlgorithmResult> execute() {
        init();
        List<AlgorithmResult> result = run();
        stop();
        return result;
    }

    /**
     * Initializes the algorithm execution.
     */
    protected void init() {

    }

    /**
     * Ejecuta el algoritmo.
     * @return 
     */
    protected List<AlgorithmResult> run() {
        List<AlgorithmResult> results = null;
        
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        } else {
            results = new ArrayList<>();
            try {
                String outputDirName = options.<String>getOption(OUTPUT);
                FileUtils.createDirIfNoExists(outputDirName);
                
                options.addOption(Options.LOG_BASE_NAME, algorithm.getShortName());
                algorithm.getLogger().setOptions(options);
                AlgorithmLogger logger = new AlgorithmLogger(algorithm.getClass().getName(), options, false);
                
                for (String input : inputs) {
                    AlgorithmResult result = run(input, outputDirName, logger);
                    results.add(result);
                }
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, null, ex);
                throw new AlgorithmException("Error creting the output dir.", ex);
            } 
        }
        return results;
    }

    
    /**
     * Ejecuta el algoritmo.
     *
     * @return Resultado del algoritmo.
     */
    protected AlgorithmResult run(String input, String outputDir, AlgorithmLogger logger) {
        AlgorithmResult result = null;
        ImplicationalSystem outputSystem;
        
        try {


            ImplicationalSystem inputSystem = new ImplicationalSystem(input);
            logger.startTime();
            outputSystem = algorithm.execute(inputSystem);
            long executionTime = logger.endTime();

            if (outputSystem != null) {
                String outputType = options.<String>getOption(Options.OUTPUT_TYPE);
                if (StringUtils.isEmpty(outputType)) {
                    outputType = "txt";
                }
                
                String outputFilename = algorithm.getShortName() + "_" + FileUtils.getName(input) + "." + outputType;
                String outputFile = Paths.get(outputDir, outputFilename).toString();
                outputSystem.save(outputFile);
                
                result = new AlgorithmResult(input, outputFile, algorithm);
                result.setExecutionTime(executionTime);
                result.setLogFile(Paths.get(outputDir, options.getOption(Options.LOG_BASE_NAME) + "_trace.log").toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        } finally {
            logger.flush();
            logger.freeResources();
        }

        return result;
    }

    /**
     * Finaliza la ejecución y libera recursos.
     */
    protected void stop() {

    }

    /**
     * Sets the path of the inputs system.
     *
     * @param fileNames Additionals inputs.
     * @return AlgorithmExecuter with inputs system setted.
     */
    public AlgorithmExecutor inputs(String... fileNames) {
        if (fileNames == null) {
            throw new InvalidPathException("El fichero de entrada es nulo.");

        }

        for (String name : fileNames) {
            if (name == null || !Files.exists(Paths.get(name))) {
                throw new InvalidPathException("El fichero de entrada no existe: " + name);
            }

        }

        this.inputs = fileNames;
        return this;
    }

    /**
     * Returns the inputs path.
     *
     * @return Input path.
     */
    public String[] getInputs() {
        return inputs;
    }

    /**
     * Sets the output path of the result execution.
     *
     * @param file Path of the output.
     * @return AlgorithmExecuter with output system setted.
     */
    public AlgorithmExecutor output(String file) {
        this.options.addOption(AlgorithmOptions.Options.OUTPUT.toString(), file);
        return this;
    }

    /**
     * Returns the output path.
     *
     * @return Output path.
     */
    public String getOutput() {
        return options.getOption(AlgorithmOptions.Options.OUTPUT.toString());
    }

    /**
     * Enables an execution mode.
     *
     * @param mode Mode.
     * @return AlgorithmExecutor with an execution mode enabled.
     */
    public AlgorithmExecutor enable(AlgorithmOptions.Mode mode) {
        this.options.enable(mode);
        return this;
    }

    /**
     * Disables an execution mode.
     *
     * @param mode Mode.
     * @return AlgorithmExecutor with an execution mode disabled.
     */
    public AlgorithmExecutor disable(AlgorithmOptions.Mode mode) {
        this.options.disable(mode);
        return this;
    }

    /**
     * Sets an execution option.
     *
     * @param name Option name.
     * @param value Value.
     * @return AlgorithmExecutor with an execution option setted.
     */
    public AlgorithmExecutor option(String name, Object value) {
        this.options.addOption(name, value);
        return this;
    }

    /**
     * Sets an execution options.
     *
     * @param options Execution options.
     * @return AlgorithmExecutor with an execution option setted.
     */
    public AlgorithmExecutor options(AlgorithmOptions options) {
        this.options = options;
        return this;
    }

    public AlgorithmOptions getOptions() {
        return options;
    }

}
