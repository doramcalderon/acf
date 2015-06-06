/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @since @author Dora Calderón
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

    public String getMarkers() {
        return markers;
    }

    public void setMarkers(String markers) {
        this.markers = markers;
    }

}
