package es.uma.pfc.is.bench.tasks;

import es.uma.pfc.is.algorithms.io.CSVFileWriter;
import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
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
import javafx.event.EventType;
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

    /**
     * CSV File name.
     */
    private String csvFileName;
    /**
     * Table.
     */
    private TableView table;

    /**
     * Constructor.
     * @param csvFileName CSV File name.
     * @param table Table.
     */
    public StatisticsReaderService(String csvFileName, TableView table) {
        this.csvFileName = csvFileName;
        this.table = table;
    }

    @Override
    protected Task createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final CSVParser parser = CSVParser.parse(new File(csvFileName), Charset.defaultCharset(), CSVFileWriter.getCSVFileFormat());
                
                Platform.runLater(new Runnable() {

                    public void run() {
                        try {
                            printHeaders(parser);
                            printRecords(parser);
                        } finally {
                            try {
                                if(!parser.isClosed()) {
                                    parser.close();
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(StatisticsReaderService.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                return null;
            }
            
            
        };
    }
    
    

    /**
     * Print the headers as columns.
     * @param parser CSV Parser.
     */
    protected void printHeaders(final CSVParser parser) {
        for(CSVRecord headers : parser) {
            int i = 0;
            for(String header : headers) {
                table.getColumns().add(createColumn(i, header));
                i++;
            }
            break;
        }
    }

    /**
     * Print the records as new rows.
     * @param parser CSV Parser.
     */
    protected void printRecords(CSVParser parser) {
        for(CSVRecord record : parser) {
            // Add additional columns if necessary:
            for (int columnIndex = table.getColumns().size(); columnIndex < record.size(); columnIndex++) {
                table.getColumns().add(createColumn(columnIndex, ""));
            }

            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            Iterator<String> recordIterator = record.iterator();

            while (recordIterator.hasNext()) {
                data.add(new SimpleStringProperty(recordIterator.next()));
            }
            table.getItems().add(data);
        }
    }

    /**
     * Create a new column in the TableView.
     * @param columnIndex Index of column.
     * @param columnTitle Title of column.
     * @return A new column.
     */
    protected TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex, String columnTitle) {
        String title = (StringUtils.isEmpty(columnTitle)) ? "Column " + columnIndex : columnTitle;
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn(title);
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
