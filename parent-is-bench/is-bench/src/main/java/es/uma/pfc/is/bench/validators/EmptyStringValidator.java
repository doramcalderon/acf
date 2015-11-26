package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * Validator of strings which can be empty.
 * @author Dora Calderón
 */
public class EmptyStringValidator implements Validator<String> {

    @Override
    public ValidationResult apply(Control control, String value) {
        return ValidationResult.fromMessageIf(control, BenchMessages.get().getMessage(BenchMessages.EMPTY_VALUES), Severity.ERROR, StringUtils.isEmpty(value));
    }

}
