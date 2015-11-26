
package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import es.uma.pfc.is.algorithms.exceptions.AlgorithmException;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.strings.StringUtils;
import fr.kbertet.lattice.ImplicationalSystem;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.slf4j.LoggerFactory;

/**
 * Task for algorithm execution in a thread.
 * After its execution returns an AlgorithmResult.
 * @author Dora Calderón
 */
public class AlgExecutionTask implements Callable<AlgorithmResult> {
 /**
     * Logger.
     */
    private final org.slf4j.Logger logger = LoggerFactory.getLogger (AlgExecutionTask.class);
    /**
     * Algorithm to execute.
     */
    private  Algorithm algorithm;
    /**
     * Execution options.
     */
    private  AlgorithmOptions options;
    /**
     * Input implicational system path.
     */
    private  String input;
    /**
     * Output directory path.
     */
    private  String outputDir;
    /**
     * Output file name.
     */
    private String outputFilename;
    

    /**
     * Constructor.
     * @param algorithm Algorithm to execute.
     * @param options Execution options.
     * @param input Input implicational system path.
     * @param outputDir Output directory path.
     */
    public AlgExecutionTask(Algorithm algorithm, AlgorithmOptions options, String input, String outputDir) {
        try {
            this.options = options;
            this.algorithm = algorithm.getClass().newInstance();
            this.algorithm.setName(algorithm.getName());
            this.algorithm.setShortName(algorithm.getShortName());
            this.algorithm.getLogger().setOptions(options);
                        
            this.input = input;
            this.outputDir = outputDir;
            this.outputFilename = algorithm.getShortName() + "_" + FileUtils.getName(input) 
                                   + "." + getOutputType(options);
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error("Error instantiating the algorithm.", ex);
        }
    }

    private String getOutputType(AlgorithmOptions options) {
        String outputType = options.<String>getOption(Options.OUTPUT_TYPE);
        if (StringUtils.isEmpty(outputType)) {
            outputType = "txt";
        }
        return outputType;
    }
    /**
     * Executes the algorithm with an input and output dir.
     *
     * @return Algorithm result.
     * @throws Exception
     */
    @Override
    public AlgorithmResult call() throws Exception {
        AlgorithmResult result;
        ImplicationalSystem outputSystem;
        double executionTime = 0;
      
        
        try {
            ImplicationalSystem inputSystem = new ImplicationalSystem(input);
            System.out.println("Ejecutando "+ algorithm.getName() + " con entrada " + input);

            double time = System.nanoTime();
            outputSystem = algorithm.execute(inputSystem);
            double endTime = System.nanoTime();
            executionTime = (endTime - time) * 0.000001;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            algorithm.getLogger().log(AlgorithmOptions.Mode.TRACE, "Error en la ejecuión de " + algorithm + ": "+ ex.getMessage());
            throw new AlgorithmException("Error en la ejecución de " + toString(), ex);
        } finally {
            algorithm.getLogger().flush();
            algorithm.getLogger().freeResources();
        }

        result = saveResults(executionTime, outputSystem);
        return result;
    }
  

    /**
     * Save the result implicational system
     *
     * @param executionTime
     * @param outputSystem
     * @return
     * @throws IOException
     */
    public AlgorithmResult saveResults(double executionTime, ImplicationalSystem outputSystem)
            throws IOException {
        AlgorithmResult result = null;
        if (outputSystem != null) {
            
            String outputFile = Paths.get(outputDir, outputFilename).toString();
            outputSystem.save(outputFile);

            result = new AlgorithmResult(input, outputFile, algorithm);
            result.setExecutionTime(executionTime);
            result.setLogFile(Paths.get(outputDir, options.getOption(Options.LOG_BASE_NAME) + "_trace.log").toString());
        }
        return result;
    }

}
