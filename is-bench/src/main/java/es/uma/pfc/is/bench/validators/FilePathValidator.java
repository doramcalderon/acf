
package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.algorithms.util.StringUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * File path validator.
 * @author Dora Calderón
 */
public class FilePathValidator implements Validator<String> {

    @Override
    public ValidationResult apply(Control control, String value) {
        return ValidationResult.fromMessageIf(control, "The path no exists.", Severity.ERROR, !Files.exists(Paths.get(value)));
    }

}
