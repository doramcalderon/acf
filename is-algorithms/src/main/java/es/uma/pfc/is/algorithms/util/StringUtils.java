
package es.uma.pfc.is.algorithms.util;

import java.util.regex.Pattern;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class StringUtils {

    /**
     * Constructor.
     */
    private StringUtils() {
    }
    
    /**
     * Reemplaza los argumentos
     * @param message
     * @param token
     * @param args
     * @return 
     */
    public static String replaceArgs(String message, String token, Object ... args) {
        String messageReplaced = message;
        
        if(message != null && message.trim().length() > 0 && token != null) {
            
            for(Object arg : args) {
                messageReplaced = Pattern.compile(token, Pattern.LITERAL).matcher(messageReplaced)
                                         .replaceFirst(String.valueOf(arg));
            }
            
        }
        return messageReplaced;
    }
    
}
