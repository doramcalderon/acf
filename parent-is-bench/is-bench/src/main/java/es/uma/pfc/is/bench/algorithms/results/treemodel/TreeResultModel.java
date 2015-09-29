/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.algorithms.results.treemodel;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @since @author Dora Calderón
 */
public class TreeResultModel {

    private final StringProperty name;
    private final StringProperty date;
    private final StringProperty input;
    private final StringProperty output;
    private final LongProperty executionTime;
    private final StringProperty statsFile;
    private final StringProperty log;

    public TreeResultModel(String name) {
        this.name = new SimpleStringProperty(name);
        date = new SimpleStringProperty();
        executionTime = new SimpleLongProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
        statsFile = new SimpleStringProperty();
        log = new SimpleStringProperty();
    }

    public TreeResultModel() {
        name = new SimpleStringProperty();
        date = new SimpleStringProperty();
        executionTime = new SimpleLongProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
        statsFile = new SimpleStringProperty();
        log = new SimpleStringProperty();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setDate(Date date) {
        String formattedDate = null;
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
            formattedDate = df.format(date);
        }
        this.date.set(formattedDate);
    }

    public StringProperty inputProperty() {
        return input;
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    public StringProperty outputProperty() {
        return output;
    }

    public void setOutput(String output) {
        this.output.set(output);
    }

    public StringProperty logProperty() {
        return log;
    }

    public void setLog(String log) {
        this.log.set(log);
    }

    public StringProperty statsFileProperty() {
        return statsFile;
    }

    public void setStatsFile(String statsFile) {
        this.statsFile.set(statsFile);
    }

    public LongProperty executionTimeProperty() {
        return executionTime;
    }

    public void setExecutionTime(Long time) {
        this.executionTime.setValue(time);
    }

    public boolean isAlgorithmResult() {
        return !StringUtils.isEmpty(input.get());
    }
}
