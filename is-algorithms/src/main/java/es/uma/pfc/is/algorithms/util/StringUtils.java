
package es.uma.pfc.is.algorithms.util;

import java.util.regex.Pattern;

/**
 * Stirng utilities.
 * @author Dora Calderón
 */
public class StringUtils {

    /**
     * Constructor.
     */
    private StringUtils() {
    }
    
    /**
     * If a string is empty or null.
     * @param str String.
     * @return {@code true} if {@code str} is empty or null.
     */
    public static boolean isEmpty(String str) {
        return (str == null) || str.trim().isEmpty() || "null".equals(str.trim().toLowerCase());
    }
    
    /**
     * Replace the arguments in a string.
     * @param message Message.
     * @param token Token which will be replaced with the arguments.
     * @param args Arguments.
     * @return Message with the tokens replaced with the arguments.
     */
    public static String replaceArgs(String message, String token, Object ... args) {
        String messageReplaced = message;
        
        if(message != null && message.trim().length() > 0 && token != null && args != null) {
            
            for(Object arg : args) {
                messageReplaced = Pattern.compile(token, Pattern.LITERAL).matcher(messageReplaced)
                                         .replaceFirst(String.valueOf(arg));
            }
            
        }
        return messageReplaced;
    }
    
}
