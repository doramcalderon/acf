/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import es.uma.pfc.is.algorithms.AlgorithmOptions.Options;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_END;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_INIT;
import static es.uma.pfc.is.algorithms.Messages.PERFORMANCE_TOTAL;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class PerformanceLogger extends ISBenchLogger {
    private long startTime;
    private OutputStream output;
    
    public PerformanceLogger(AlgorithmOptions options) {
        super(options);
    }
    public PerformanceLogger(AlgorithmOptions options, OutputStream output) {
        super(options);
        this.output = output;
    }
    
    protected void writeMessage(String message)  {
        char[] messageChars = message.toCharArray();
        for (char c : messageChars) {
            try {
                output.write(message.getBytes());
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(PerformanceLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    /**
     * Escribe la hora de comienzo.
     * @param time HOra de inicio.
     */
    public void startTime(Date time) {
        if (isPerformanceEnabled()) {
            startTime = time.getTime();
            writeMessage(getMessage(PERFORMANCE_INIT));
            logger.info(getMessage(PERFORMANCE_INIT), startTime);
        }
    }
    
    /**
     * Escribe la hora de fin y la diferencia entre el inicio y el fin.
     * @param time Hora de fin.
     */
    public void endTime(Date time) {
        if(isPerformanceEnabled()) {
            long total = time.getTime() - startTime;

            logger.info(getMessage(PERFORMANCE_END), time);
            logger.info(getMessage(PERFORMANCE_TOTAL), total);
        }
    }

}
