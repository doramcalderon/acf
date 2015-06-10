
package es.uma.pfc.implications.generator.model;

import es.uma.pfc.implications.generator.i18n.I18n;

/**
 *
 * @author Dora Calderón
 */
public enum ResultValidation {
    OK, KO,
    ZERO_NODES, INVALID_PREMISE_LENGTH;
    
    /**
     * Si la validación es correcta.
     * @return {@code true} si la validación es correcta, {@code false} en otro caso.
     */
    public  boolean isValid() {
        return OK.equals(this);
    }

    @Override
    public String toString() {
        String message = null;
        
        switch (this) {
            case ZERO_NODES:
                message = I18n.INVALID_ATTRIBUTES_NUM;
                break;
            case INVALID_PREMISE_LENGTH:
                message = I18n.INVALID_PREMISE_LENGTH;
                break;
            default:
                message = super.toString();
        }
        
        return message;
    }
    
    
}
