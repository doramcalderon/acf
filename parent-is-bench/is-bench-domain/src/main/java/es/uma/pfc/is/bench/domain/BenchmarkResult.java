

package es.uma.pfc.is.bench.domain;

import es.uma.pfc.is.algorithms.AlgorithmInfo;
import es.uma.pfc.is.algorithms.AlgorithmResult;
import es.uma.pfc.is.algorithms.util.StringUtils;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dora Calderón
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BenchmarkResult {
    
    /**
     * Benchmark name.
     */
    @XmlAttribute
    private String benchmarkName;
    /**
     * Execution date/time.
     */
    @XmlAttribute
    private Date date;
    /**
     * Algorithm results.
     */
    @XmlElement(name="algorithmResult")
    private List<AlgorithmResult> algorithmResults;

    /**
     * Constructor.
     */
    public BenchmarkResult() {
        date = new Date();
    }
    
    

    /**
     * Constructor.
     * @param algorithmResults
     * @param timestamp 
     */
    public BenchmarkResult(String benchmarkName, List<AlgorithmResult> algorithmResults, Date timestamp) {
        this.benchmarkName = benchmarkName;
        this.algorithmResults = algorithmResults;
        this.date = timestamp;
    }

    
    /**
     * Algorithm results.
     * @return the algorithmResults
     */
    public List<AlgorithmResult> getAlgorithmResults() {
        return algorithmResults;
    }
    /**
     * Sets the algorithms results.
     * @param algorithmResults Algorithms results.
     */
    public void setAlgorithmResults(List<AlgorithmResult> algorithmResults) {
        this.algorithmResults = algorithmResults;
    }
    
    

    /**
     * Execution date/time.
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Map<AlgorithmInfo, List<AlgorithmResult>> groupByAlgorithm() {
        return getAlgorithmResults().stream()
                .collect(Collectors.groupingBy(AlgorithmResult::getAlgorithmInfo));
    }

    
    
    /**
     * Returns the path dir of results.
     * @return Path output dir.
     */
    public String getOutputDir() {
        String outputDir = "";
        if (algorithmResults != null && !algorithmResults.isEmpty()) {
            outputDir = Paths.get(algorithmResults.get(0).getOutputFile()).getParent().toString();
        }
        return outputDir;
    }

    /**
     * Benchmark name.
     * @return the benchmarkName
     */
    public String getBenchmarkName() {
        return benchmarkName;
    }

    /**
     * Benchmark name.
     * @param benchmarkName the benchmarkName to set
     */
    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }
    @XmlTransient
    public String getStatisticsFileName() {
        String statsFileName = null;
        if(!StringUtils.isEmpty(getBenchmarkName()) && getDate() != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
            statsFileName = Paths.get(getOutputDir(), 
                                      getBenchmarkName() + "_" + df.format(getDate()) + ".csv").toString();
        }
        return statsFileName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.benchmarkName);
        hash = 79 * hash + Objects.hashCode(this.date);
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
        final BenchmarkResult other = (BenchmarkResult) obj;
        if (!Objects.equals(this.benchmarkName, other.benchmarkName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
}
