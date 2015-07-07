package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.bench.algorithms.business.*;
import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.config.UserConfig;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXB;

/**
 * Persist the benchmarks into an XML file entities using JAXB.
 *
 * @author Dora Calderón
 */
public class BenchmarksPersistence {
    

    /**
     * Single instance.
     */
    private static BenchmarksPersistence me;

    /**
     * Constructor.
     */
    private BenchmarksPersistence() {
    }

    /**
     * Get a single instance of BenchmarksPersistence.
     *
     * @return BenchmarksPersistence.
     */
    public static BenchmarksPersistence get() {
        synchronized(BenchmarksPersistence.class) {
            if (me == null) {
                me = new BenchmarksPersistence();
            }
        }
        return me;
    }

    /**
     * Initialize the algorithms file with {@code algorithms} param.
     *
     * @param algorithms Algorithms.
     */
    public void create(Algorithms algorithms) {
        if (algorithms == null) {
            throw new IllegalArgumentException("algorithms argument can't be null.");
        }
       
        String algorithmsFile =  UserConfig.get().getDefaultWorkspace() + File.separator + "algorithms.xml";
        try {
            FileUtils.createIfNoExists(algorithmsFile);
        } catch (IOException ex) {
            throw new RuntimeException("Error creating algorithms file.", ex);
        }
        JAXB.marshal(algorithms, algorithmsFile);
    }
   

    /**
     * Add the algorithms of {@code algorithms} param to algorithms file.
     *
     * @param algorithms
     */
    public void insert(Algorithms algorithms) {
        if (algorithms == null) {
            throw new IllegalArgumentException("algorithms argument can't be null.");
        }
        Algorithms algs = getAlgorithms();
        if (algs == null) {
            algs = algorithms;
        } else {
            algs.addAll(algorithms);
        }
        
        JAXB.marshal(algs, UserConfig.get().getAlgorithmsFile());
    }

    /**
     * Add an algorithm to algorithms file.
     *
     * @param algorithm Algorithm.
     */
    public void insert(AlgorithmEntity algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("algorithm param can't be null.");
        }
        Algorithms algs = new Algorithms();
        algs.add(algorithm);
        insert(algs);
    }
    
    
    /**
     * Delete an algorithm from algorithms file.
     *
     * @param algorithm Algorithm.
     */
    public void delete(AlgorithmEntity algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("algorithm param can't be null.");
        }
        Algorithms algs = getAlgorithms();
        if(algs.getAlgorithms() != null) {
            algs.getAlgorithms().remove(algorithm);
        }
        create(algs);
    }
    
    public void clear() {
        create(new Algorithms());
    }

    /**
     * Get the algorithms from algorithms file.
     *
     * @return Algorithms.
     */
    protected Algorithms getAlgorithms() {
        Algorithms definedAlgorithms = null;
        File algorithmsXml = UserConfig.get().getAlgorithmsFile();
        if (algorithmsXml != null) {
            definedAlgorithms = JAXB.unmarshal(algorithmsXml, Algorithms.class);

        }
        return definedAlgorithms;
    }

}
