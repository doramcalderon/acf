/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @since @author Dora Calderón
 */
@XmlRootElement(name = "algorithms")
public class Algorithms {

    private Set<AlgorithmEntity> algorithms;

    public Algorithms() {
        algorithms = new HashSet<>();
    }

    public Algorithms(Set<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    @XmlElements({
        @XmlElement(name = "algorithm")})
    public Set<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    /**
     * Adds an algorithm.
     *
     * @param algEntity Algorithm.
     */
    public void add(AlgorithmEntity algEntity) {
        algorithms.add(algEntity);
    }

    /**
     * Adds new algorithms.
     *
     * @param newAlgorithms New algorithms.
     */
    public void addAll(AlgorithmEntity... newAlgorithms) {
        if (newAlgorithms != null) {
            addAll(Arrays.asList(newAlgorithms));
        }
    }

    /**
     * Adds new algorithms.
     *
     * @param newAlgorithms New algorithms.
     */
    public void addAll(Collection<AlgorithmEntity> newAlgorithms) {
        if (newAlgorithms != null) {
            if (this.algorithms == null) {
                this.algorithms = new HashSet<>();
            }
            this.algorithms.addAll(newAlgorithms);
        }
    }

    /**
     * Removes algorithms.
     *
     * @param algNames Algorithms names.
     */
    public void removeAlgorithms(String... algNames) {
        if (algNames != null) {
            Set<AlgorithmEntity> auxAlgs = new HashSet<>();
            if (algorithms != null) {
                auxAlgs.addAll(algorithms);
                for (String name : algNames) {
                    algorithms.stream().filter((alg) -> (alg.getName().equals(name))).forEach((alg) -> {
                        auxAlgs.remove(alg);
                    });
                }
            }
            this.algorithms = auxAlgs;
        }
    }

    public static List<Algorithm> convertAll(List<AlgorithmEntity> algs) throws Exception {
        List<Algorithm> algorithmsObjects = null;
        if (algs != null) {
            algorithmsObjects = new ArrayList();
            for (AlgorithmEntity entity : algs) {
                Algorithm alg = entity.getType().newInstance();
                alg.setName(entity.getName());
                alg.setShortName(entity.getShortName());
                algorithmsObjects.add(alg);
            }
        }
        return algorithmsObjects;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.algorithms);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Algorithms other = (Algorithms) obj;
        if (this.algorithms == null && other.algorithms != null) {
            return false;
        }
        if (this.algorithms != null && other.algorithms == null) {
            return false;
        }
        return Arrays.equals(this.algorithms.toArray(new AlgorithmEntity[]{}),
                other.algorithms.toArray(new AlgorithmEntity[]{}));
    }

}
