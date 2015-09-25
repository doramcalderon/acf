package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import static es.uma.pfc.is.algorithms.AlgorithmOptions.Options.OUTPUT;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import es.uma.pfc.is.algorithms.util.ImplicationalSystems;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.io.ImplicationalSystemWriterProlog;
import es.uma.pfc.is.commons.strings.StringUtils;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
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
     * Logger.
     */
//    private AlgorithmLogger logger;
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
//        logger = new AlgorithmLogger(algorithm.getName(), options);
    }

    public void execute(Algorithm alg) {
        this.algorithm = alg;
        execute();
    }

    /**
     * Ejecuta un algoritnmo pasando por tres fases: inicialización, ejecución, finalización.
     *
     * @param alg Algoritmo.
     * @return Resultado de la ejecución.
     */
    public void execute() {
        init();
        run();
        stop();
    }

    /**
     * Initializes the algorithm execution.
     */
    protected void init() {

    }

    /**
     * Ejecuta el algoritmo.
     */
    protected void run() {
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        } else {
            try {
                String outputDirName = Paths.get(options.<String>getOption(OUTPUT), getCurrentDateString())
                                            .toString();
                FileUtils.createDirIfNoExists(outputDirName);
                options.addOption(Options.OUTPUT, outputDirName);
                options.addOption(Options.LOG_BASE_NAME, algorithm.getShortName());
                algorithm.getLogger().setOptions(options);
                AlgorithmLogger logger = new AlgorithmLogger(algorithm.getClass().getName(), options, true);
                
                for (String input : inputs) {
                    run(input, outputDirName, logger);
                }
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, null, ex);
                throw new AlgorithmException("Error creting the output dir.", ex);
            }
        }
    }

    protected String getCurrentDateString() {
        Calendar cal = Calendar.getInstance();
        String currentInstantDir = String.valueOf(cal.get(Calendar.YEAR))
                + String.valueOf(cal.get(Calendar.MONTH))
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
                + String.valueOf(cal.get(Calendar.HOUR_OF_DAY))
                + String.valueOf(cal.get(Calendar.MINUTE))
                + String.valueOf(cal.get(Calendar.SECOND));
        return currentInstantDir;
    }

    /**
     * Ejecuta el algoritmo.
     *
     * @return Resultado del algoritmo.
     */
    protected ImplicationalSystem run(String input, String outputDir, AlgorithmLogger logger) {
        ImplicationalSystem outputSystem = null;
        
        try {

            logger.startTime();

            ImplicationalSystem inputSystem = new ImplicationalSystem(input);
            outputSystem = algorithm.execute(inputSystem);
            logger.endTime();
            logger.statistics(ImplicationalSystems.getSize(outputSystem), ImplicationalSystems.getCardinality(outputSystem));

            if (outputSystem != null) {
                String outputType = options.<String>getOption(Options.OUTPUT_TYPE);
                if (StringUtils.isEmpty(outputType)) {
                    outputType = "txt";
                }
                
                String outputFilename = algorithm.getShortName() + "_" + FileUtils.getName(input) + "." + outputType;
                outputSystem.save(Paths.get(outputDir, outputFilename).toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        } finally {
            logger.freeResources();
        }

        return outputSystem;
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
