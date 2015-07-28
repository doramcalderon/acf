package es.uma.pfc.is.bench.benchmarks.business;

import es.uma.pfc.is.bench.algorithms.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmark;
import es.uma.pfc.is.bench.benchmarks.domain.Benchmarks;
import es.uma.pfc.is.commons.files.FileUtils;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
     * Initialize the benchmarks file with {@code benchmarks} param.
     *
     * @param path Path of benchmarks.xml.
     * @param benchmarks Algorithms.
     */
    public void create(String path, Benchmarks benchmarks) {
        try {
            if (benchmarks == null) {
                throw new IllegalArgumentException("benchmarks argument can't be null.");
            }
            Path algorithmsFilePath = Paths.get(path, "benchmarks.xml");
            String algorithmsFile =  algorithmsFilePath.toString();
            try {
                FileUtils.createIfNoExists(algorithmsFile);
            } catch (IOException ex) {
                throw new RuntimeException("Error creating algorithms file.", ex);
            }
            JAXBContext context = JAXBContext.newInstance(Benchmarks.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(benchmarks, algorithmsFilePath.toFile());
        } catch (JAXBException ex) {
            Logger.getLogger(BenchmarksPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    /**
     * Add the algorithms of {@code algorithms} param to algorithms file.
     *
     * @param algorithms
     */
    public void insert(Algorithms algorithms) {
       
    }

    /**
     * Add an algorithm to algorithms file.
     *
     * @param algorithm Algorithm.
     */
    public void insert(AlgorithmEntity algorithm) {
    }
    
    
  

    /**
     * Get the algorithms from algorithms file.
     *
     * @param workspace
     * @return Algorithms.
     * @throws java.lang.Exception
     */
    protected List<Benchmark> getBenchmarks(String workspace) throws Exception {
        List<Benchmark> benchmarks = null;
        
        if(!StringUtils.isEmpty(workspace)) {
            Path benchmarksPath = Paths.get(workspace, "benchmarks");
            if(Files.exists(benchmarksPath)) {
                List<Path> benchPaths = Arrays.asList(Files.list(benchmarksPath).toArray(Path[]::new));
                benchmarks = new ArrayList();

                for(Path p : benchPaths) {
                    Algorithms benchAlgorithms;
                    Path algorithmsPath = Paths.get(p.toString(), "algorithms.xml");
                    if(Files.exists(algorithmsPath)) {
                        File algorithmsXml = algorithmsPath.toFile();
                        benchAlgorithms = JAXB.unmarshal(algorithmsXml, Algorithms.class);
                        Benchmark b = new Benchmark(workspace, p.toFile().getName(), Algorithms.convertAll(benchAlgorithms.getAlgorithms()));
                        benchmarks.add(b);
                    }
                }
            }
            
        }
        
        return benchmarks;
    }

}
