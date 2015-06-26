/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.algorithms.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @since 
 * @author Dora Calderón
 */
@XmlRootElement(name="algorithms")
public class Algorithms {
    private List<AlgorithmEntity> algorithms;

    public Algorithms() {
        algorithms = new ArrayList();
    }
    
        

    @XmlElements({@XmlElement(name="algorithm")})
    public List<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    
    public void add(AlgorithmEntity algEntity) {
        algorithms.add(algEntity);
    }
    public void addAll(Algorithms algorithms) {
        if(algorithms != null && algorithms.getAlgorithms() != null) {
            this.algorithms.addAll(algorithms.getAlgorithms());
        }
    }
    }
