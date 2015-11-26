
package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.lang.reflect.Modifier;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ClassNameValidator implements Validator<String> {
    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ClassNameValidator.class);

    @Override
    public ValidationResult apply(Control control, String value) {
        boolean valid = false;
        String errorMessage = "";
        
        if(!StringUtils.isEmpty(value)) {
            try {
                Class clazz = Class.forName(value);
                if(Modifier.isAbstract(clazz.getModifiers()) 
                        || Modifier.isInterface(clazz.getModifiers()) 
                        || !Algorithm.class.isAssignableFrom(clazz)) {
                    errorMessage = BenchMessages.get().getMessage(BenchMessages.MSG_NOT_IMPLEMENTATION, Algorithm.class.getName());
                    valid = false;
                } else {
                    valid = true;
                }

            } catch (ClassNotFoundException ex) {
                errorMessage = BenchMessages.get().getMessage(BenchMessages.CLASS_NOT_EXISTS);
                logger.error(errorMessage, ex);
            }
        } else {
            errorMessage = BenchMessages.get().getMessage(BenchMessages.EMPTY_CLASS);
        }
        return ValidationResult.fromErrorIf(control, errorMessage, !valid);
    }

}
