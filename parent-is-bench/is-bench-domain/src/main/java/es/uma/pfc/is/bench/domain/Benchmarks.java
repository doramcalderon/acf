/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @since @author Dora Calderón
 */
//TODO esta clase desaparecerá ya que se debe guardar en el Workspace
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Benchmarks {

    
    @XmlElement(name = "benchmark")
    private List<Benchmark> benchmarks;

    public Benchmarks() {
        benchmarks = new ArrayList<>();
    }

    
    public List<Benchmark> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(List<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public void addBenchmark(Benchmark bench) {
        if(bench != null) {
            benchmarks.add(bench);
        }
    }
    /**
     * Returns the benchmark with the name {@code name}. 
     * @param name Name of benchmark to search.
     * @return Bechmark found.<br/> {@code null} if benchmark not found.
     */
    public Benchmark getBenchmark(String name) {
        Benchmark bench = null;
        if(benchmarks != null && !benchmarks.isEmpty()) {
            Optional<Benchmark> result = benchmarks.stream().filter(b -> name.trim().equals(b.getName())).findFirst();
            if(result.isPresent()) {
                bench = result.get();
            }
        }
        return bench;
    }
    /**
     * Returns the benchmark with the name {@code name}. 
     * @param name Name of benchmark to search.
     */
    public void removeBenchmark(String name) {
        if(benchmarks != null && !benchmarks.isEmpty()) {
            benchmarks.remove(getBenchmark(name));
        }
    }
    
    public int size() {
        return (benchmarks != null) ? benchmarks.size() : 0;
    }
}
