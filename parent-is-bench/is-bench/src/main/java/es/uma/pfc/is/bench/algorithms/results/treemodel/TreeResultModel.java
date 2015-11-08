
package es.uma.pfc.is.bench.algorithms.results.treemodel;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Results tree table view model.<br/>
 * Represents a row of the results table.
 * @author Dora Calderón
 */
public class TreeResultModel {
    /**
     * Name.
     */
    private final StringProperty name;
    /**
     * Date.
     */
    private final StringProperty date;
    /**
     * Input.
     */
    private final StringProperty input;
    /**
     * Output.
     */
    private final StringProperty output;
    /**
     * Execution time.
     */
    private final LongProperty executionTime;
    /**
     * Statistics file.
     */
    private final StringProperty statsFile;
    /**
     * Log file.
     */
    private final StringProperty log;

    /**
     * Constructor.
     * @param name Name.
     */
    public TreeResultModel(String name) {
        this.name = new SimpleStringProperty(name);
        date = new SimpleStringProperty();
        executionTime = new SimpleLongProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
        statsFile = new SimpleStringProperty();
        log = new SimpleStringProperty();
    }
    /**
     * Constructor.
     */
    public TreeResultModel() {
        name = new SimpleStringProperty();
        date = new SimpleStringProperty();
        executionTime = new SimpleLongProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
        statsFile = new SimpleStringProperty();
        log = new SimpleStringProperty();
    }

    /**
     * Name property.
     * @return Name property.
     */
    public StringProperty nameProperty() {
        return name;
    }
    /**
     * Establishes the name.
     * @param name Name.
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * Date property.
     * @return String property.
     */
    public StringProperty dateProperty() {
        return date;
    }
    /**
     * Establishes the date.
     * @param date Date string.
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Establishes the date with the format "dd/MM/yyyy HH:mm:SS".
     * @param date Date.
     */
    public void setDate(Date date) {
        String formattedDate = null;
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
            formattedDate = df.format(date);
        }
        this.date.set(formattedDate);
    }
    /**
     * Input property.
     * @return StringProperty.
     */
    public StringProperty inputProperty() {
        return input;
    }
    /**
     * Establishes the input.
     * @param input Input.
     */
    public void setInput(String input) {
        this.input.set(input);
    }
    /**
     * Output property.
     * @return StringProperty.
     */
    public StringProperty outputProperty() {
        return output;
    }
    /**
     * Establishes the output.
     * @param output Output.
     */
    public void setOutput(String output) {
        this.output.set(output);
    }
    /**
     * Log property.
     * @return StringProperty.
     */
    public StringProperty logProperty() {
        return log;
    }
    /**
     * Establishes the log path.
     * @param log Log path.
     */
    public void setLog(String log) {
        this.log.set(log);
    }
    /**
     * Statistics file property.
     * @return StringProperty.
     */
    public StringProperty statsFileProperty() {
        return statsFile;
    }
    /**
     * Establishes the statistics file path.
     * @param statsFile Statistics file path.
     */
    public void setStatsFile(String statsFile) {
        this.statsFile.set(statsFile);
    }
    /**
     * Execution time property.
     * @return LongProperty.
     */
    public LongProperty executionTimeProperty() {
        return executionTime;
    }
    /**
     * Establishes the execution time.
     * @param time Execution time.
     */
    public void setExecutionTime(Long time) {
        this.executionTime.setValue(time);
    }
    /**
     * If the row is an algorithm result.
     * @return {@code true} if is an algorithm result, {@code false} otherwise.
     */
    public boolean isAlgorithmResult() {
        return !StringUtils.isEmpty(input.get());
    }
}
