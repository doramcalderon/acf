
package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.commons.strings.StringUtils;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class ClassNameValidator implements Validator<String> {

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
                    errorMessage = "The class is not an implementation of " + Algorithm.class.getName();
                    valid = false;
                } else {
                    valid = true;
                }

            } catch (ClassNotFoundException ex) {
                errorMessage = "The class not exists.";
                Logger.getLogger(ClassNameValidator.class.getName()).log(Level.SEVERE, "The class not exists.");
            }
        } else {
            errorMessage ="The class can't be empty.";
        }
        return ValidationResult.fromErrorIf(control, errorMessage, !valid);
    }

}
