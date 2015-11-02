
package es.uma.pfc.is.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Filter used in logger appenders for filter the messages by markers.<br/>
 * Example: With this configuration, HISTORY-APPENDER writes the messages with mark 'HISTORY' or 'PERFORMANCE'.<br/><br/>
 * {@code <appender name="HISTORY-APPENDER" class="ch.qos.logback.core.FileAppender">}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp; {@code <file>${outputAlg}_trace.log</file>}<br/>

 * &nbsp;&nbsp;&nbsp;&nbsp; {@code <filter class="es.uma.pfc.is.logging.ModeFilter">}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {@code <Markers>HISTORY,PERFORMANCE</Markers>}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp; {@code </filter>}<br/>
 * {@code </appender>}
 * @author Dora Calderón
 */
public class ModeFilter extends Filter<ILoggingEvent> {

    /**
     * Value of markers tag.
     */
    private String markers;
    /**
     * Marker to accept.
     */
    private List<Marker> markersToAccept;

    @Override
    public void start() {
        if (markers != null && !markers.isEmpty()) {
            markersToAccept = new ArrayList();
            String [] markersSplit = markers.split(",");
            
            for(String marker : markersSplit) {
                markersToAccept.add(MarkerFactory.getMarker(marker));
            }
            super.start();
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        Marker eventMarker = event.getMarker();
        if ((markersToAccept.contains(eventMarker))) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

    /**
     * Markers.
     * @return Markers.
     */
    public String getMarkers() {
        return markers;
    }

    /**
     * Sets the markers.
     * @param markers Markers to set.
     */
    public void setMarkers(String markers) {
        this.markers = markers;
    }

}
