package es.uma.pfc.is.algorithms;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import static es.uma.pfc.is.algorithms.AlgorithmOptions.Options.OUTPUT;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.algorithms.exceptions.InvalidPathException;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.io.ImplicationalSystemWriterProlog;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service which executes an algorithm.
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
     * Number of concurrent executions.
     */
    private int threadsNum;
    /**
     * Timeout in seconds for task executed. 1800 (30 minutes) by default.
     */
    private int timeoutTask;
    
    private ExecutorService executor;
    /**
     * Constructor.
     */
    public AlgorithmExecutor() {
        options = new AlgorithmOptions();
        messages = AlgMessages.get();
        threadsNum = getThreadsNum();
        timeoutTask = getTimeoutTask();
        executor = Executors.newFixedThreadPool(threadsNum, 
                                                new ThreadFactoryBuilder().setNameFormat("Algorithms-%d")
                                                        .setDaemon(true).build());
        ImplicationalSystemWriterProlog.register();
    }
    
    /**
     * Gets the threads num from the "isbench.threads.num" system property.
     * If it is not established or the value it is not numeric, the default value is 10.
     * @return Number of threads.
     */
    private int getTimeoutTask() {
        String value = System.getProperty("isbench.timeout.task");
        if (!StringUtils.isNumeric(value)) {
            value = "7200";
        }
        return Integer.valueOf(value);
    }
    
    /**
     * Gets the threads num from the "isbench.threads.num" system property.
     * If it is not established or the value it is not numeric, the default value is 10.
     * @return Number of threads.
     */
    private int getThreadsNum() {
        String value = System.getProperty("isbench.threads.num");
        if (!StringUtils.isNumeric(value)) {
            value = "2";
        }
        return Integer.valueOf(value);
    }

    /**
     * Constructor. For testing purpose only.
     *
     * @param algorithm Algorithm to execute.
     */
    protected AlgorithmExecutor(Algorithm algorithm) {
        this();
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        }
        this.algorithm = algorithm;
    }

    /**
     * Executes an algorithm.
     *
     * @param alg Algorithm.
     * @return Algorithm results.
     */
    public List<AlgorithmResult> execute(Algorithm alg) {
        this.algorithm = alg;
        return execute();
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
     * Sets an execution options.
     *
     * @param options Execution options.
     * @return AlgorithmExecutor with an execution option setted.
     */
    public AlgorithmExecutor options(AlgorithmOptions options) {
        this.options = options;
        return this;
    }

    /**
     * Executes an algorithm in three stages: initialization, execution, finalization.
     *
     * @return Resultado de la ejecución.
     */
    protected List<AlgorithmResult> execute() {
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
     * Executes the algorithm with the inputs and options stablished.
     *
     * @return Algorithm results.
     * @throws IllegalArgumentException if the algorithm is null.
     * @throw IOException if IO error ocurred.
     */
    protected List<AlgorithmResult> run() {
        List<AlgorithmResult> results = null;

        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm can't be null");
        } else {
            results = new ArrayList<>();
            try {
                options.addOption(Options.LOG_BASE_NAME, algorithm.getShortName());
                String outputDirName = options.<String>getOption(OUTPUT);
                FileUtils.createDirIfNoExists(outputDirName);
                results = concurrentExecution(inputs, outputDirName);

            } catch (IOException ex) {
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, null, ex);
                throw new AlgorithmException("Error creting the output dir.", ex);
            }
        }
        return results;
    }

    /**
     * Executes the algorithm in a thread by input, with a threadsNum property value as max.
     * If the inputs number is greater than the number of threads, the executions will be enqued.
     * @param inputs Algorithm inputs paths.
     * @param outputDir Output directory path.
     * @return Execution results.
     */
    protected List<AlgorithmResult> concurrentExecution(String[] inputs, String outputDir) {
        List<AlgorithmResult> results = Collections.synchronizedList(new ArrayList());

        for (String input : inputs) {
            Future<AlgorithmResult> futureResult = null;
            try {
                futureResult = executor.submit(new AlgExecutionTask(algorithm, options, input, outputDir));
                AlgorithmResult r = futureResult.get(timeoutTask, TimeUnit.SECONDS);
                if(r != null) {
                    results.add(r);
                }
            } catch (InterruptedException ex) {
                cancelFuture(futureResult);
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                cancelFuture(futureResult);
                 Logger.getLogger(AlgorithmExecutor.class.getName())
                        .log(Level.SEVERE, "The task has been cancelled because takes too long.");
            } catch (RejectedExecutionException ex) {
                cancelFuture(futureResult);
                Logger.getLogger(AlgorithmExecutor.class.getName()).log(Level.SEVERE, "The task has been rejected.");
            }
        }

        return results;
    }
    private void cancelFuture(Future f) {
        if (f != null) {
            f.cancel(true);
        }
    }

    /**
   
    /**
     * Finaliza la ejecución y libera recursos.
     */
    protected void stop() {
       
    }
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
    /**
     * Returns the inputs path.
     *
     * @return Input path.
     */
    protected String[] getInputs() {
        return inputs;
    }

    /**
     * Returns the output path.
     *
     * @return Output path.
     */
    protected String getOutput() {
        return options.getOption(AlgorithmOptions.Options.OUTPUT.toString());
    }
}
