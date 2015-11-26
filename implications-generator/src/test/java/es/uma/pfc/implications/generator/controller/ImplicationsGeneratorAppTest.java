
package es.uma.pfc.implications.generator.controller;

import es.uma.pfc.implications.generator.view.FXMLViews;
import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Dora Calderón
 */
public class ImplicationsGeneratorAppTest {
    
    
    @Test
    public void testLoadRootFxml() {
        URL fxmlUrl = Thread.currentThread().getContextClassLoader().getResource(FXMLViews.ROOT_VIEW);
        assertNotNull(fxmlUrl);
    }
    
    @Test
    public void testLoadImplicationsFxml() {
        URL fxmlUrl = Thread.currentThread().getContextClassLoader().getResource(FXMLViews.IMPLICATIONS_VIEW);
        assertNotNull(fxmlUrl);
    }
}
