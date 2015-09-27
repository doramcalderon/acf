/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.benchmarks.execution;

import java.text.DateFormat;
import java.util.Date;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class BenchmarkResultsModel {
    private StringProperty name;
    private StringProperty date;
    private StringProperty input;
    private StringProperty output;
    private LongProperty executionTime;
    private StringProperty log;

    public BenchmarkResultsModel(String name) {
        this.name = new SimpleStringProperty(name);
        date = new SimpleStringProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
        log = new SimpleStringProperty();
    }

    public BenchmarkResultsModel() {
        name = new SimpleStringProperty();
        date = new SimpleStringProperty();
        executionTime = new SimpleLongProperty();
        input = new SimpleStringProperty();
        output = new SimpleStringProperty();
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
        if(date != null) {
            formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(date);
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

    public LongProperty executionTimeProperty() {
        return executionTime;
    }

    public void setExecutionTime(Long time) {
        this.executionTime.setValue(time);
    }
    
    
}
