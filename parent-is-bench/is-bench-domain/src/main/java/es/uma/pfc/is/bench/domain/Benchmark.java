
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @since @author Dora Calderón
 */
@XmlType(propOrder = {"name", "workspace", "input", "algorithmsClasses"})
@XmlRootElement(name = "benchmark")
@XmlAccessorType(XmlAccessType.FIELD)
public class Benchmark {

    /**
     * Workspace.
     */
    @XmlAttribute
    private String workspace;
    /**
     * Name.
     */
    @XmlAttribute
    private String name;
    /**
     * Input implicational system file path.
     */
    @XmlElement
    private String input;

    @XmlElementWrapper(name = "algorithms")
    @XmlElement(name = "algorithm")
    private List<Class<? extends Algorithm>> algorithmsClasses;

    /**
     * Algorithms.
     */
    @XmlTransient
    private List<Algorithm> algorithms;

    /**
     * Constructor.
     */
    public Benchmark() {
    }

    /**
     * Constructor.
     *
     * @param workspace
     * @param name Name.
     * @param algorithms Algorithms.
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String workspace, String name, List<Algorithm> algorithms) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("The name is required.");
        }
        if (algorithms == null || algorithms.isEmpty()) {
            throw new IllegalArgumentException("The list algorithms is required.");
        }
        this.name = name;
        this.workspace = workspace;

        this.algorithms = setAlgorithmsOutput(algorithms);
        algorithmsClasses = new ArrayList<>();
        for (Algorithm alg : algorithms) {
            algorithmsClasses.add(alg.getClass());
        }

    }

    /**
     * Constructor.
     *
     * @param name Name.
     * @param algorithms Algorithms.
     * @throws IllegalArgumentException if name or algorithms list is empty.
     */
    public Benchmark(String name, List<AlgorithmEntity> algorithms) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("The name is required.");
        }
        if (algorithms == null || algorithms.isEmpty()) {
            throw new IllegalArgumentException("The list algorithms is required.");
        }
        this.name = name;
//        this.workspace = UserConfig.get().getDefaultWorkspace();
         
        algorithmsClasses = new ArrayList<>();
        algorithms.stream().forEach((alg) -> { algorithmsClasses.add(alg.getType());});
    }

    /**
     * Sets the algorithms output to the default output file in the benchmark output dir.
     *
     * @param algorithms Algorithms.
     * @return List of algorithms.
     */
    protected final List<Algorithm> setAlgorithmsOutput(List<Algorithm> algorithms) {
        if (algorithms != null) {
            algorithms.forEach((alg) -> {
                alg.output(Paths.get(getOutputDir(), alg.getDefaultOutputFileName()).toString());
            });
        }
        return algorithms;
    }

    /**
     * Input implicational system file path.
     *
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * Sets the algorithms input.
     *
     * @param inputFilePath Input file path.
     */
    public void setInput(String inputFilePath) {
        this.input = inputFilePath;
    }

    /**
     * Name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public List<Class<? extends Algorithm>> getAlgorithmsClasses() {
        return algorithmsClasses;
    }

    /**
     * Algorithms.
     *
     * @return the algorithms
     */
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }


    /**
     * Workspace.
     *
     * @return the workspace
     */
    public String getWorkspace() {
        return workspace;
    }

    /**
     * Workspace.
     *
     * @param workspace the workspace to set
     */
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
        setAlgorithmsOutput(algorithms);
    }

    /**
     * The benchmark path.
     *
     * @return Benchmark path.
     */
    public String getBenchmarkPath() {
        if (StringUtils.isEmpty(workspace)) {
            throw new RuntimeException("The workspace isn't established.");
        }

        return Paths.get(workspace, "benchmarks", name).toString();
    }

    /**
     * Path of output dir of benchmark.
     *
     * @return Path.
     */
    public String getOutputDir() {
        return Paths.get(getBenchmarkPath(), "output").toString();
    }

    /**
     * Path of input dir of benchmark.
     *
     * @return Path.
     */
    public String getInputDir() {
        return Paths.get(getBenchmarkPath(), "input").toString();
    }

    @Override
    public String toString() {
        return getName();
    }

    
}
