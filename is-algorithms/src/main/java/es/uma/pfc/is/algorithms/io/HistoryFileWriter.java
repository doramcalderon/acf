/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.algorithms.io;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class HistoryFileWriter {
    private PrintWriter fileWriter;
    private String fileName;
    
    public HistoryFileWriter(String fileName) throws IOException {
        if(fileName == null || fileName.trim().isEmpty()) {
            this.fileName = "history.log";
        } else {
            this.fileName = fileName;
        }
        fileWriter = new PrintWriter(this.fileName);
    }
    
    public void print(String message, Object ... args) {
        String messageToPrint = StringUtils.replaceArgs(message, "{}", args);
        fileWriter.println(messageToPrint);
    }
    
    public void finish() {
        fileWriter.flush();
        fileWriter.close();
    }
    
}
