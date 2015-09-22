
package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
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
@XmlType(propOrder={"name", "workspace", "inputsDir", "algorithmsEntities"})
@XmlRootElement(name="benchmark")
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
     * Input implicational systems dir path.
     */
    @XmlElement
    private String inputsDir;
    
    /**
     * Input files wich will be copied to input dir.
     */
    @XmlTransient
    private List<File> inputFiles;

    @XmlElementWrapper(name = "algorithms")
    @XmlElement(name = "algorithm")
    private List<AlgorithmEntity> algorithmsEntities;


    /**
     * Constructor.
     */
    public Benchmark() {
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
        this.algorithmsEntities = algorithms;
    }

    

    /**
     * Input implicational system dir path.
     *
     * @return the input
     */
    public String getInputsDir() {
        return inputsDir;
    }

    /**
     * Sets the algorithms input.
     *
     * @param inputFilePath Input dir path.
     */
    public void setInputsDir(String inputFilePath) {
        this.inputsDir = inputFilePath;
    }

    /**
     * Name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public List<AlgorithmEntity> getAlgorithmsEntities() {
        return algorithmsEntities;
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
//        setAlgorithmsOutput(algorithms);
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

        return Paths.get(workspace, name).toString();
    }

    /**
     * Path of output dir of benchmark.
     *
     * @return Path.
     */
    public String getOutputDir() {
        return Paths.get(getBenchmarkPath(), "output").toString();
    }

  
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
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
        final Benchmark other = (Benchmark) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Input files wich will be copied to input dir.
     * @return the inputFiles
     */
    public List<File> getInputFiles() {
        return inputFiles;
    }

    /**
     * Input files wich will be copied to input dir.
     * @param inputFiles the inputFiles to set
     */
    public void setInputFiles(List<File> inputFiles) {
        this.inputFiles = inputFiles;
    }

    
}
