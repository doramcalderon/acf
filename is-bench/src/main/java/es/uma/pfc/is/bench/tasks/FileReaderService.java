package es.uma.pfc.is.bench.tasks;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Reads a file in background thread and stores it into StringProperty.
 *
 * @author Dora Calderón
 */
public class FileReaderService extends Service<StringBuilder> {

    /**
     * File name.
     */
    private String fileName;
    /**
     * Property that contains content file.
     */
    private final StringProperty contentFileProperty;

    /**
     * Constructor.
     */
    public FileReaderService() {
        this.contentFileProperty = new SimpleStringProperty();
    }

    /**
     * Sets the file name.
     *
     * @param fileName File name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Property that contains content file.
     *
     * @return StringProperty.
     */
    public StringProperty contentFileProperty() {
        return contentFileProperty;
    }

    @Override
    protected Task<StringBuilder> createTask() {
        return new Task<StringBuilder>() {

            @Override
            protected StringBuilder call() throws Exception {
                StringBuilder fileContent = new StringBuilder();

                try (RandomAccessFile readFile = new RandomAccessFile(fileName, "r");
                        FileChannel inChannel = readFile.getChannel()) {

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    long fileLength = readFile.length();
                    long block = 1;

                    while (inChannel.read(buffer) > 0) {
                        buffer.flip();
                        for (int i = 0; i < buffer.limit(); i++) {
                            fileContent.append(String.valueOf((char) buffer.get()));
                        }
                        buffer.clear(); 
                        updateProgress(calculateReaded(fileLength, 1024, block++), 100);
                    }

                }
                
                return fileContent;
            }

        };
    }

    /**
     * Calculate the length readed.
     *
     * @param totalLength Total length of file.
     * @param sizeBlock Block size.
     * @param block Index of block.
     * @return Length readed.
     */
    protected long calculateReaded(long totalLength, long sizeBlock, long block) {
        long readed = 0;
        if (totalLength > 0 && sizeBlock > 0) {
            readed = ((block * sizeBlock) * 100) / totalLength;
        }
        return readed;
    }

    @Override
    protected void succeeded() {
        if (getValue() != null) {
            this.contentFileProperty.set(getValue().toString());
        }
    }

    @Override
    protected void failed() {
        System.out.println("ERROR!!");
    }

}
