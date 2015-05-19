package es.uma.pfc.is.algorithms.io;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * CSV File Writer.
 * @author Dora Calderón
 */
public class CSVFileWriter {

    //Delimiter used in CSV file
    protected static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final Object[] FILE_HEADER = {"id", "firstName", "lastName", "gender", "age"};

    /**
     * CSV file name.
     */
    private final String fileName;
    /**
     * Header.
     */
    private Object[] header;
    /**
     * Delimiter used in CSV file. {@literal \n} by default.
     */
    private String newLineSeparator;
    /**
     * Printer.
     */
    private CSVPrinter csvFilePrinter;

    /**
     * Formatter.
     */
    private CSVFormat csvFileFormat;
    /**
     * File Writer.
     */
    private FileWriter fileWriter;

    /**
     * Constructor.
     * @param fileName CSV file name.
     */
    public CSVFileWriter(String fileName) {
        this.fileName = fileName;
        csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
    }

    /**
     * Sets the header of the CSV file.
     *
     * @param header Header in CSV format.
     * @return CSVFileWriter with a header.
     */
    public CSVFileWriter header(Object... header) {
        this.header = header;
        return this;
    }

    /**
     * Sets the header of the CSV file.<br/>
     * If separator is {@code separator}, {@code separator} property get {@link #NEW_LINE_SEPARATOR} by default.
     *
     * @param separator Header in CSV format.
     * @return CSVFileWriter with a header.
     */
    public CSVFileWriter newLineSeparator(String separator) {
        if (separator != null) {
            this.newLineSeparator = separator;
        } else {
            this.newLineSeparator = NEW_LINE_SEPARATOR;
        }
        csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(newLineSeparator);
        return this;
    }

    /**
     * Starts the CSV file print with the header.
     * @throws java.io.IOException
     */
    public void start() throws IOException {
        fileWriter = new FileWriter(fileName);
        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
        csvFilePrinter.printRecord(header);
        fileWriter.flush();
    }

    /**
     * Print a record in the CSV File.
     * @param fields Record fields.
     * @throws IOException 
     */
    public void printRecord(Object... fields) throws IOException {
        csvFilePrinter.printRecord(fields);
    }

    /**
     * Finish de CSV print and close all streams.
     */
    public void finish() {
        try {
            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        } catch (IOException e) {
            System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
            e.printStackTrace();
        }
    }
    
    /**
     * Header.
     * @return Header. 
     */
    protected Object [] getHeader() {
        return header;
    }

    protected String getNewLineSeparator() {
        return newLineSeparator;
    }

    
    protected CSVFormat getCsvFileFormat() {
        return csvFileFormat;
    }
    
    
}
