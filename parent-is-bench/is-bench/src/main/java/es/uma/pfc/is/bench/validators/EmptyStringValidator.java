/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.bench.validators;

import es.uma.pfc.is.algorithms.util.StringUtils;
import es.uma.pfc.is.bench.i18n.BenchMessages;
import javafx.scene.control.Control;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class EmptyStringValidator implements Validator<String> {

    @Override
    public ValidationResult apply(Control control, String value) {
        return ValidationResult.fromMessageIf(control, BenchMessages.get().getMessage(BenchMessages.EMPTY_VALUES), Severity.ERROR, StringUtils.isEmpty(value));
    }

}
