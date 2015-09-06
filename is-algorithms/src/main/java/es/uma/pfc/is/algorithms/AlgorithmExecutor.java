package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import es.uma.pfc.is.logging.AlgorithmLogger;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private AlgorithmLogger logger;
    /**
     * Execution options.
     */
    private AlgorithmOptions options;

    /**
     * Path of input system.
     */
    private String input;

    /**
     * I18n messages.
     */
    protected AlgMessages messages;


    /**
     * Constructor.
     *
     * @param algorithm Algorithm to execute.
     */
    public AlgorithmExecutor() {
        options = new AlgorithmOptions();
        messages = AlgMessages.get();
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
        logger = new AlgorithmLogger(algorithm.getName(), options);
    }

    public ImplicationalSystem execute(Algorithm alg) {
        this.algorithm = alg;
        return execute();
    }
    /**
     * Ejecuta un algoritnmo pasando por tres fases: inicialización, ejecución, finalización.
     * @param alg Algoritmo.
     * @return Resultado de la ejecución.
     */
    public ImplicationalSystem execute() {
        init();
        ImplicationalSystem outputSystem = run();
        stop();
        return outputSystem;
    }

    /**
     * Initializes the algorithm execution.
     */
    protected void init() {

    }

    /**
     * Ejecuta el algoritmo.
     * @return Resultado del algoritmo.
     */
    protected ImplicationalSystem run() {
        ImplicationalSystem outputSystem = null;
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        } else {
            try {
                logger = new AlgorithmLogger(algorithm.getName(), options);

                logger.startTime();
                algorithm.getLogger().setOptions(options);
                outputSystem = algorithm.execute(new ImplicationalSystem(input));
                logger.endTime();
                if (outputSystem != null) {
                    outputSystem.save(options.<String>getOption(AlgorithmOptions.Options.OUTPUT.toString()));
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
            } finally {
                logger.freeResources();
            }
        }
        return outputSystem;
    }

    /**
     * Finaliza la ejecución y libera recursos.
     */
    protected void stop() {

    }

    /**
     * Sets the path of the input system.
     *
     * @param fileName Path of the input system.
     * @return AlgorithmExecuter with input system setted.
     */
    public AlgorithmExecutor input(String fileName) {
        if (fileName == null || !Files.exists(Paths.get(fileName))) {
            throw new InvalidPathException("El fichero de entrada no existe: " + fileName);
        }
        this.input = fileName;
        return this;
    }

    /**
     * Returns the input path.
     *
     * @return Input path.
     */
    public String getInput() {
        return input;
    }

    /**
     * Sets the output path of the result execution.
     *
     * @param file Path of the output.
     * @return AlgorithmExecuter with output system setted.
     */
    public AlgorithmExecutor output(String file) {
        Path filePath = Paths.get(file);
        if (!Files.exists(filePath) && !Files.isDirectory(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
                Logger.getLogger(GenericAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
