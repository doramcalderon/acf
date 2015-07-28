
package es.uma.pfc.is.bench.benchmarks.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Set of benchmarks.
 * @author Dora Calderón
 */
@XmlRootElement( name = "benchmarks")
public class Benchmarks {
    private List<BenchmarkEntity> benchmarks;

    public Benchmarks() {
        benchmarks = new ArrayList<>();
    }
    
    
    @XmlElement(name = "benchmark")
    public List<BenchmarkEntity> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(List<BenchmarkEntity> benchmarks) {
        this.benchmarks = benchmarks;
    }

   
}
