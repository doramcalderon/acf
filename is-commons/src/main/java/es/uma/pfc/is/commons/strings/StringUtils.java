
package es.uma.pfc.is.commons.strings;

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
    
    public static boolean isEmpty(String str) {
        return (str == null) || str.trim().isEmpty() || "null".equals(str.trim().toLowerCase());
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
        
        if(message != null && message.trim().length() > 0 && token != null && args != null) {
            
            for(Object arg : args) {
                messageReplaced = Pattern.compile(token, Pattern.LITERAL).matcher(messageReplaced)
                                         .replaceFirst(String.valueOf(arg));
            }
            
        }
        return messageReplaced;
    }
    
    /**
     * Returns a string whose value is this string, with any leading and trailing whitespace removed.
     * @see java.lang.String#trim() 
     * @param str String.
     * @return String without spaces. {@code null} if original string is null.
     */
    public static String trim(String str) {
        String strTrimmed = new String(str);
        if(!isEmpty(str)) {
            strTrimmed = str.trim();
        }
        return strTrimmed;
    }
    
    /**
     * If a string contains a substring with case insensitive.
     * @param str String.
     * @param substring Substring.
     * @return {@code true} if str contains substring, {@code false} otherwise.
     */
    public static boolean containsIgnoreCase(String str, String substring) {
        boolean contains = false;
        if(str != null && substring!= null) {
            contains = Pattern.compile(Pattern.quote(substring), Pattern.CASE_INSENSITIVE).matcher(str).find();
        }
        return contains;
    }
    
}
