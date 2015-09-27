package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.bench.domain.Algorithms;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Business logic for insert, modify and delete algorithms.
 *
 * @author Dora Calderón
 */
public class AlgorithmsBean {
    
    private AlgorithmsPersistence persistence;

    /**
     * Consturctor.
     * @param algorithmsFilePath Workspace path.
     */
    public AlgorithmsBean(String algorithmsFilePath) {
        this.persistence = AlgorithmsPersistence.get(algorithmsFilePath);
    }

    /**
     * Return the algorithms defined in the algorithms file.
     * @return Algorithm Entities list.
     */
    public Set<AlgorithmInfo> getAlgorithms() {
        return persistence.getAlgorithmsCatalog();
    }
    
    /**
     * Updates the algorithms file adding new algorithms.<br/>
     * If any algorithms exists, this is overrided.
     * @param algorithms Algorithms to add.
     */
    public void addAlgorithms(AlgorithmInfo ... algorithms) {
        if (algorithms == null) {
            throw new IllegalArgumentException("algorithms argument can't be null.");
        }
        Algorithms algs = new Algorithms(persistence.getAlgorithmsCatalog());
        
        String [] names = Arrays.asList(algorithms).stream()
                                            .map((AlgorithmInfo t) -> {return (t != null) ? t.getName() : null;})
                                            .collect(Collectors.toList())
                                            .toArray(new String[]{});
        algs.removeAlgorithms(names);
        algs.addAll(algorithms);
        persistence.update(algs);
    }

    /**
     * Removes an algorithm from algorithms file.
     * @param algorithm Algorithm.
     */
    public void delete(AlgorithmInfo algorithm) {
      persistence.delete(algorithm);
    }

    /**
     * If an algorithm exists in algorithms file.
     *
     * @param algorithm Algorithm.
     * @return {@code true} if an algorithm exists in algorithms file, {@code false} otherwise.
     */
    public boolean exists(AlgorithmInfo algorithm) {
        boolean exists = false;
        if (algorithm != null) {

            Set<AlgorithmInfo> algs = getAlgorithms();
            if (algs != null) {
                exists = algs.contains(algorithm);
            }
        }
        return exists;
    }

    /**
     * If exists an algorithm with the name argument.
     *
     * @param name Name.
     * @return {@code true} if exists an algorithm with the name argument exists in algorithms file, {@code false}
     * otherwise.
     */
    public boolean existsName(String name) {
        boolean exists = false;
        if (!StringUtils.isEmpty(name)) {
            
            Set<AlgorithmInfo> algs = getAlgorithms();
            if(algs != null && !algs.isEmpty()) {
                exists = algs.stream().filter(alg -> (name.equalsIgnoreCase(StringUtils.trim(alg.getName())))).count() > 0;
            }

        }
        return exists;
    }

    /**
     * If exists an algorithm with the shortName argument as short name.
     *
     * @param shortName Short name.
     * @return {@code true} if exists an algorithm with the shortName argument as short name exists in algorithms file,
     * {@code false} otherwise.
     */
    public boolean existsShortName(String shortName) {
        boolean exists = false;
        if (!StringUtils.isEmpty(shortName)) {

            Set<AlgorithmInfo> algs = getAlgorithms();
            if(algs != null && !algs.isEmpty()) {
                exists = algs.stream().filter(alg -> (shortName.equalsIgnoreCase(StringUtils.trim(alg.getShortName())))).count() > 0;
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
