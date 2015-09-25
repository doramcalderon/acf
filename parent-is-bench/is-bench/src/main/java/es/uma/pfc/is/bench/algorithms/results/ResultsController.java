/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.algorithms.results;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * FXML Controller class
 *
 * @author Dora Calderón
 */
public class ResultsController implements Initializable {
    
    private LineChart chart;
    @FXML
    private AnchorPane graphicsPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
//            initChart();
        } catch (Exception ex) {
            System.out.println("ERror: " + ex.getMessage());
        }
    }    
    protected void initChart() throws IOException {
         CSVParser parser = CSVParser.parse(Paths.get("C:\\Users\\Dorilla (Fakul)\\.isbench\\myworkspace\\benchmarks\\Basis\\output\\do_output.csv").toFile(), 
                                    Charset.defaultCharset(), CSVFormat.DEFAULT);
         
         CategoryAxis xAxis = new CategoryAxis();
         NumberAxis yAxis = new NumberAxis("Values for Y-Axis", 5, 20, 5);
         XYChart.Series<String, Number> oldSizeSeries = new XYChart.Series<>();
         XYChart.Series<String, Number> newSizeSeries = new XYChart.Series<>();
         
         for(CSVRecord r : parser.getRecords()) {
             Iterator<String> recordIterator = r.iterator();
             String rule = recordIterator.next();
             String oldSizeStr = recordIterator.next();
             String newSizeStr = recordIterator.next();
             
             if(!"rule".equals(rule.trim())) {
                 xAxis.getCategories().add(rule);
             }
             
             if (oldSizeStr.matches("-?\\d+(\\.\\d+)?")) {
                 oldSizeSeries.getData().add(new Data<>(rule, Integer.valueOf(oldSizeStr)));
             }
             if (newSizeStr.matches("-?\\d+(\\.\\d+)?")) {
                 newSizeSeries.getData().add(new Data<>(rule, Integer.valueOf(newSizeStr)));
             }
             
         }
        chart = new LineChart<>(xAxis, yAxis); 
        chart.setData(FXCollections.observableArrayList(oldSizeSeries, newSizeSeries));
        graphicsPane.getChildren().add(chart);
    }
    
}
