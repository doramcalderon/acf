package es.uma.pfc.is.commons.i18n;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.ResourceBundle;

/**
 * Utility for get i18n messages by a resource bundle.
 * @author Dora Calderón
 */
public abstract class Messages {

   /**
    * Resource bundle with i18n messages.
    * @return ResourceBundle.
    */ 
    public abstract ResourceBundle getBundle();
    
    /**
     * Return a translated message with replaced arguments.
     *
     * @param messageKey Message key.
     * @param args Arguments.
     * @return Message.<br/> {@code messageKey} if doesn't exist.
     */
    public String getMessage(String messageKey, Object... args) {
        String message = null;
        if(getBundle().containsKey(messageKey)) {
            message = getBundle().getString(messageKey);
        }
        if (!StringUtils.isEmpty(message)) {
            message = StringUtils.replaceArgs(message, "{}", args);
        } else {
            message = messageKey;
        }
        return message;
    }
}
