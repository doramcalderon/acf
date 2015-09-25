
package es.uma.pfc.is.logging.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

/**
 * Layout for CSV appenders.
 * @author Dora Calderón
 */
public class CSVLayout extends LayoutBase<ILoggingEvent>{
    /**
     * Headers.
     */
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
