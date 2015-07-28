
package es.uma.pfc.is.bench.benchmarks.domain;

import es.uma.pfc.is.bench.algorithms.domain.Algorithms;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Benchmark entity for persistence.
 * @author Dora Calderón
 */
@XmlRootElement(name = "benchmark")
@XmlType(propOrder = {"name", "input", "algorithms"})
public class BenchmarkEntity {

    /**
     * Name.
     */
    private String name;
    /**
     * Input file path.
     */
    private String input;
    /**
     * Algorithms.
     */
    private Algorithms algorithms;

    public BenchmarkEntity() {
    }

    /**
     * Constructor.
     *
     * @param name Benchmark's name.
     */
    public BenchmarkEntity(String name) {
        this.name = name;
    }
    
     /**
     * Name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Input file path.
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * Input file path.
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Algorithms.
     * @return the algorithms
     */
    public Algorithms getAlgorithms() {
        return algorithms;
    }

    /**
     * Algorithms.
     * @param algorithms the algorithms to set
     */
    public void setAlgorithms(Algorithms algorithms) {
        this.algorithms = algorithms;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.input);
        hash = 53 * hash + Objects.hashCode(this.algorithms);
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
        final BenchmarkEntity other = (BenchmarkEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.input, other.input)) {
            return false;
        }
        if (!Objects.equals(this.algorithms, other.algorithms)) {
            return false;
        }
        return true;
    }

    

   

}
