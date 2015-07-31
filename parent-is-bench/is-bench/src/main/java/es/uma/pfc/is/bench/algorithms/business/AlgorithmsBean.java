

package es.uma.pfc.is.bench.algorithms.business;

import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.List;

/**
 * Business logic for insert, modify and delete algorithms.
 * @author Dora Calderón
 */
public class AlgorithmsBean {
    private AlgorithmsPersistence persistence;

    public AlgorithmsBean() {
        persistence = AlgorithmsPersistence.get();
    }
    
    /**
     * Return the algorithms defined in the algorithms file.
     * @return Algorithm Entities list.
     */
    public List<AlgorithmEntity> getAlgorithms() {
        List<AlgorithmEntity> algorithms = null;
        Algorithms algs = persistence.getAlgorithms();
        if(algs != null) {
            algorithms = algs.getAlgorithms();
        }
        return algorithms;
    }
    
    public void create(Algorithms algorithms) {
        persistence.create(algorithms);
        
    }
    

    /**
     * Insert a new algorithm.
     * @param algorithm Algorithm.
     */
    public void insert(AlgorithmEntity algorithm) {
        persistence.insert(algorithm);
    }
    
    public void delete(AlgorithmEntity algorithm) {
        persistence.delete(algorithm);
    }
    
    /**
     * If an algorithm exists in algorithms file.
     * @param algorithm Algorithm.
     * @return {@code true} if an algorithm exists in algorithms file, {@code false} otherwise.
     */
    public boolean exists(AlgorithmEntity algorithm) {
        boolean exists = false;
        if(algorithm != null) {
            Algorithms algs = persistence.getAlgorithms();
            if(algs != null && algs.getAlgorithms() != null) {
                exists = algs.getAlgorithms().contains(algorithm);
            }
        }
        return exists;
    }
    
    /**
     * If exists an algorithm with the name argument.
     * @param name Name.
     * @return {@code true} if exists an algorithm with the name argument exists in algorithms file, {@code false} otherwise.
     */
    public boolean existsName(String name) {
        boolean exists = false;
        if(!StringUtils.isEmpty(name)) {
            Algorithms algs = persistence.getAlgorithms();
            if(algs != null && algs.getAlgorithms() != null) {
                exists = algs.getAlgorithms().stream().filter(alg -> (name.equalsIgnoreCase(StringUtils.trim(alg.getName())))).count() > 0;
            }
            
        }
        return exists;
    }
    
    /**
     * If exists an algorithm with the  shortName argument as short name.
     * @param shortName Short name.
     * @return {@code true} if exists an algorithm with the shortName argument as short name exists in algorithms file, {@code false} otherwise.
     */
    public boolean existsShortName(String shortName) {
        boolean exists = false;
        if(!StringUtils.isEmpty(shortName)) {
            Algorithms algs = persistence.getAlgorithms();
            if(algs != null && algs.getAlgorithms() != null) {
                exists = algs.getAlgorithms().stream().filter(alg -> (shortName.equalsIgnoreCase(StringUtils.trim(alg.getShortName())))).count() > 0;
            }
            
        }
        return exists;
    }
    
    /**
     * For testing usage.
     * @param persistence 
     */
    protected void setPersistence(AlgorithmsPersistence persistence) {
        this.persistence = persistence;
    }
}
