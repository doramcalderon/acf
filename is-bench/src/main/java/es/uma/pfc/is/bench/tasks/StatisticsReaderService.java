package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Read the statistics generated and show them in a TableView.
 *
 * @author Dora Calderón
 */
public class StatisticsReaderService extends Service {

    private String csvFileName;
    private TableView table;

    public StatisticsReaderService(String csvFileName, TableView table) {
        this.csvFileName = csvFileName;
        this.table = table;
    }

    @Override
    protected Task createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                final CSVParser parser = new CSVParser(new BufferedReader(new FileReader(csvFileName)),
                        CSVFileWriter.getCSVFileFormat());
                Platform.runLater(new Runnable() {

                    public void run() {
                        try {
                            printHeaders(parser.getHeaderMap());
                            printRecords(parser.getRecords());
                        } catch (IOException ex) {
                            Logger.getLogger(StatisticsReaderService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return null;
            }
        };
    }

    protected void printHeaders(final Map<String, Integer> headers) {
        if (headers != null) {
            Platform.runLater(new Runnable() {

                public void run() {
                    for (String headerName : headers.keySet()) {
                        table.getColumns().add(createColumn(headers.get(headerName), headerName));
                    }
                }
            });
        }

    }

    protected void printRecords(List<CSVRecord> records) {
        for(CSVRecord record : records) {
            // Add additional columns if necessary:
            for (int columnIndex = table.getColumns().size(); columnIndex < record.size(); columnIndex++) {
                table.getColumns().add(createColumn(columnIndex, ""));
            }
            
            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            Iterator<String> recordIterator = record.iterator();
            
            while(recordIterator.hasNext()) {
                data.add(new SimpleStringProperty(recordIterator.next()));
            }
            table.getItems().add(data);
        }
    }

    protected TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn();

        String title = (StringUtils.isEmpty(columnTitle)) ? "Column " + columnIndex : columnTitle;
        column.setText(title);

        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
                ObservableList<StringProperty> values = cellDataFeatures.getValue();
                if (columnIndex >= values.size()) {
                    return new SimpleStringProperty("");
                } else {
                    return cellDataFeatures.getValue().get(columnIndex);
                }
            }
        });

        return column;
    }

}
