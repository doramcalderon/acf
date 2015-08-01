package es.uma.pfc.is.bench.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @since @author Dora Calderón
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Workspace {

    @XmlAttribute
    private String path;

    @XmlElementWrapper(name = "algorithms")
    @XmlElement(name = "algorithm")
    private Set<AlgorithmEntity> algorithms;

//    @XmlElementWrapper(name = "benchmarks")
//    @XmlElement(name = "benchmark")
//    private List<Benchmark> benchmarks;
    /**
     * Constructor.
     */
    public Workspace() {

    }

    /**
     * Constructor.
     *
     * @param path Path.
     */
    public Workspace(String path) {
        this.path = path;
    }

    /**
     * Workspace absolute path.
     *
     * @return Absolute path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the workspace absolute path.
     *
     * @param path Absolute path.
     */
    public void setPath(String path) {
        this.path = path;
    }
//
//    /**
//     * Benchmarks.
//     * @return Benchmarks.
//     */
//    public List<Benchmark> getBenchmarks() {
//        return benchmarks;
//    }
//
//    /**
//     * Sets the benchmarks of workspace.
//     * @param benchmarks Benchmarks.
//     */
//    public void setBenchmarks(List<Benchmark> benchmarks) {
//        this.benchmarks = benchmarks;
//    }
//
//    /**
//     * Add a benchmark to the workspace.
//     * @param bench Benchmark.
//     */
//    public void addBenchmark(Benchmark bench) {
//        if (bench != null) {
//            if(benchmarks == null) {
//                benchmarks = new ArrayList<>();
//            }
//            benchmarks.add(bench);
//        }
//    }

    /**
     * Algorithms catalog.
     *
     * @return the algorithms
     */
    public Set<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    /**
     * Sets the algorithms catalog.
     *
     * @param algorithms the algorithms to set
     */
    public void setAlgorithms(Set<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    public void addAlgorithms(AlgorithmEntity... newAlgorithms) {
        if (newAlgorithms != null) {
            addAllAlgorithms(Arrays.asList(newAlgorithms));
        }
    }

    public void addAllAlgorithms(Collection<AlgorithmEntity> newAlgorithms) {
        if (newAlgorithms != null) {
            if (this.algorithms == null) {
                this.algorithms = new HashSet<>();
            }
            this.algorithms.addAll(newAlgorithms);
        }
    }

}
