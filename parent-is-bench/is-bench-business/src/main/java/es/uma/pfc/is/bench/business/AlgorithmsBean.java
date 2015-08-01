package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.Workspace;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.Set;

/**
 * Business logic for insert, modify and delete algorithms.
 *
 * @author Dora Calderón
 */
public class AlgorithmsBean {
    /**
     * Workspace path.
     */
    private final String workspacePath;
    private WorkspacePersistence persistence;

    /**
     * Consturctor.
     * @param workspacePath Workspace path.
     */
    public AlgorithmsBean(String workspacePath) {
        this.workspacePath = workspacePath;
        this.persistence = WorkspacePersistence.get();
    }

    /**
     * Return the algorithms defined in the algorithms file.
     * @return Algorithm Entities list.
     */
    public Set<AlgorithmEntity> getAlgorithms() {
        Set<AlgorithmEntity> algorithms = null;
        Workspace ws = persistence.getWorkspace(workspacePath);
        if (ws != null) {
            algorithms = ws.getAlgorithms();
        }
        return algorithms;
    }

    /**
     * Remove an algorithm from workspace.
     * @param algorithm Algorithm.
     */
    public void delete(AlgorithmEntity algorithm) {
        Workspace ws = persistence.getWorkspace(workspacePath);
        ws.getAlgorithms().remove(algorithm);
        WorkspacePersistence.update(ws);
    }

    /**
     * If an algorithm exists in algorithms file.
     *
     * @param algorithm Algorithm.
     * @return {@code true} if an algorithm exists in algorithms file, {@code false} otherwise.
     */
    public boolean exists(AlgorithmEntity algorithm) {
        boolean exists = false;
        if (algorithm != null) {

            Set<AlgorithmEntity> algs = getAlgorithms();
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
            
            Set<AlgorithmEntity> algs = getAlgorithms();
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

            Set<AlgorithmEntity> algs = getAlgorithms();
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
    protected void setPersistence(WorkspacePersistence persistence) {
        this.persistence = persistence;
    }

}
