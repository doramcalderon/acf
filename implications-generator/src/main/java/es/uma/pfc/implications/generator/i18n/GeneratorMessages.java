package es.uma.pfc.implications.generator.i18n;

import es.uma.pfc.is.commons.i18n.Messages;
import java.util.ResourceBundle;

/**
 *
 * @since @author Dora Calderón
 */
public class GeneratorMessages extends Messages {

    private static GeneratorMessages me;

    private GeneratorMessages() {
        
    }
    
    public static GeneratorMessages get() {
        if(me == null) {
            me = new GeneratorMessages();
        }
        return me;
    }

    @Override
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("es.uma.pfc.implications.generator.i18n.messages");
    }

    
    
    
}
