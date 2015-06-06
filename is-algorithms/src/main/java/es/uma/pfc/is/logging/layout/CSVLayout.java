/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.logging.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class CSVLayout extends LayoutBase<ILoggingEvent>{
    private String headers;
    
    @Override
    public void start() {
        setFileHeader(headers);
        super.start(); 
    }
    
    
    @Override
    public String doLayout(ILoggingEvent event) {
        return event.getMessage().concat("\n");
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
    
    

}
