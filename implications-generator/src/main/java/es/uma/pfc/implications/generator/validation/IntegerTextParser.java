
package es.uma.pfc.implications.generator.validation;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter.Change;

/**
 * Formatter for text fields thant can contains only integer numbers.
 * @author Dora Calderón
 */
public class IntegerTextParser implements UnaryOperator<Change> {
    /**
     * Number formatter.
     */
    private final NumberFormat format;
    /**
     * Parse position.
     */
    private final ParsePosition parsePosition;

    /**
     * Constructor.
     */
    public IntegerTextParser() {
        format = NumberFormat.getNumberInstance();
        format.setRoundingMode(RoundingMode.HALF_UP);
        parsePosition = new ParsePosition(0);
    }

    @Override
    public Change apply(Change controlChange) {
        parsePosition.setIndex(0);
        String newValue = controlChange.getControlNewText();
        Number parsed = format.parse(newValue, parsePosition);
        

        return ((!controlChange.isDeleted() &&
                (parsed == null || parsed.equals(0) || parsePosition.getIndex() < newValue.length())) ? null : controlChange);
    }

}
