
package es.uma.pfc.is.bench.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Colleciton of benchmark results.
 * @author Dora Calderón
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BenchmarkResultSet {
    /**
     * Benchmark results.
     */
    @XmlElement(name ="benchmarkResult")
    private List<BenchmarkResult> results;
    /**
     * Benchmark name.
     */
    @XmlAttribute
    private String name;

    /**
     * Constructor.
     */
    public BenchmarkResultSet() {
        results = new ArrayList<>();
    }
    
    

    /**
     * Benchmark results.
     * @return the results
     */
    public List<BenchmarkResult> getResults() {
        return results;
    }

    /**
     * Benchmark results.
     * @param results the results to set
     */
    public void setResults(List<BenchmarkResult> results) {
        this.results = results;
    }

    /**
     * Benchmark name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Benchmark name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
