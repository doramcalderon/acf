package es.uma.pfc.is.bench.business;

import static es.uma.pfc.is.bench.business.Persistence.read;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.Algorithms;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;

/**
 * Persist the algorithms into an XML file entities using JAXB.
 *
 * @author Dora Calderón
 */
public class AlgorithmsPersistence extends Persistence {
    /** Logger. **/
    protected static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AlgorithmsPersistence.class);
    /**
     * Single instance.
     */
    private static AlgorithmsPersistence me;
    private final String algorithmsFile;

    /**
     * Constructor.
     */
    private AlgorithmsPersistence(String algorithmsFile) {
        this.algorithmsFile = algorithmsFile;
    }

    /**
     * Get a single instance of AlgorithmsPersistence.
     *
     * @param algorithmsFilePath
     * @return AlgorithmsPersistence.
     */
    public static AlgorithmsPersistence get(String algorithmsFilePath) {
        synchronized(AlgorithmsPersistence.class) {
            if (me == null) {
                me = new AlgorithmsPersistence(algorithmsFilePath);
            }
        }
        return me;
    }

   
    /**
     * Gets an Algorithms entity from a file path.
     * @return Algorithms.
     */
    public Algorithms getAlgorithms() {
        return read(Paths.get(algorithmsFile).toString(), Algorithms.class);
    }

    /**
     * Add the algorithms of {@code algorithms} param to algorithms file.
     * @param algorithms Algorithms to add.
     */
    public void insert(AlgorithmEntity ... algorithms) {
        if (algorithms == null) {
            throw new IllegalArgumentException("algorithms argument can't be null.");
        }
        Algorithms algs = getAlgorithms();
        
        if (algs == null) {
            algs = new Algorithms();
        }
        algs.addAll(Arrays.asList(algorithms));
        
        try {
            persist(algs, algorithmsFile, true);
        } catch (JAXBException ex) {
            LOGGER.error("Error adding algorithms.", ex);
        }
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
        
        if (algs != null) {
            try {
                algs.removeAlgorithms(algorithm.getName());
                persist(algs, algorithmsFile, true);
            } catch (JAXBException ex) {
                LOGGER.error("Error deleting algorithms.", ex);
            }
        }
    }
    /**
     * Clear the algorithms file.
     */
    public void clear() {
        try {
            persist(new Algorithms(), algorithmsFile, false);
        } catch (JAXBException ex) {
            LOGGER.error("Error restarting algorithms.", ex);
        }
    }
    /**
     * Update the algorithms.
     * @param algorithms Algorithms.
     */
    public void update(Algorithms algorithms) {
        try {
            Persistence.persist(algorithms, algorithmsFile, true);
        } catch (JAXBException ex) {
            LOGGER.error("Error updating algorithms.");
        }
    }
    
    /**
     * Returns the algorithms catalog.
     * @return Algorithms catalog.
     */
    protected Set<AlgorithmEntity> getAlgorithmsCatalog() {
        Set<AlgorithmEntity> algorithms = null;
        Algorithms algs = getAlgorithms();
        if(algs != null) {
            algorithms = algs.getAlgorithms();
        }
        return algorithms;
                
    }

}
