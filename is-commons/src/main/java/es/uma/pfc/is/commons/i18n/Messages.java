package es.uma.pfc.is.commons.i18n;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.ResourceBundle;

/**
 * I18n Messages Keys.
 *
 * @author Dora Calderón
 */
public abstract class Messages {


    public abstract ResourceBundle getBundle();
    
    /**
     * Return a message with replaced arguments.
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
