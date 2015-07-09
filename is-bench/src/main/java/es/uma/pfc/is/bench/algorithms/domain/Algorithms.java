/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.algorithms.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @since @author Dora Calderón
 */
@XmlRootElement(name = "algorithms")
public class Algorithms {

    private List<AlgorithmEntity> algorithms;

    public Algorithms() {
        algorithms = new ArrayList();
    }

    public Algorithms(List<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    @XmlElements({
        @XmlElement(name = "algorithm")})
    public List<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    public void add(AlgorithmEntity algEntity) {
        algorithms.add(algEntity);
    }

    public void addAll(Algorithms algorithms) {
        if (algorithms != null && algorithms.getAlgorithms() != null) {
            this.algorithms.addAll(algorithms.getAlgorithms());
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
}
