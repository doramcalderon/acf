
package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.commons.strings.StringUtils;
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
        if(!StringUtils.isEmpty(value)) {
            try {
                Class.forName(value);
                valid = true;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClassNameValidator.class.getName()).log(Level.SEVERE, "The class not exist");
            }
        }
        return ValidationResult.fromErrorIf(control, "The class not exist", !valid);
    }

}
