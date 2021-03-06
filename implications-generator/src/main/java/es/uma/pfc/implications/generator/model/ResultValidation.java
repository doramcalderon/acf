
package es.uma.pfc.implications.generator.model;

import es.uma.pfc.implications.generator.i18n.GeneratorMessages;
import es.uma.pfc.implications.generator.i18n.I18n;

/**
 *
 * @author Dora Calderón
 */
public enum ResultValidation {
    OK, KO,
    ZERO_NODES, INVALID_IMPLICATIONS_NUM, INVALID_PREMISE_LENGTH, INVALID_CONCLUSION_LENGTH;
    
    /**
     * Si la validación es correcta.
     * @return {@code true} si la validación es correcta, {@code false} en otro caso.
     */
    public  boolean isValid() {
        return OK.equals(this);
    }

    @Override
    public String toString() {
        String message;
        
        switch (this) {
            case ZERO_NODES:
                message = GeneratorMessages.get().getMessage(I18n.INVALID_ATTRIBUTES_NUM);
                break;
            case INVALID_IMPLICATIONS_NUM:
                message =  GeneratorMessages.get().getMessage(I18n.INVALID_IMPLICATIONS_NUM);
                break;
            case INVALID_PREMISE_LENGTH:
                message =  GeneratorMessages.get().getMessage(I18n.INVALID_PREMISE_LENGTH);
                break;
            case INVALID_CONCLUSION_LENGTH:
                message =  GeneratorMessages.get().getMessage(I18n.INVALID_CONCLUSION_LENGTH);
                break;
            default:
                message = super.toString();
        }
        
        return message;
    }
    
    
}
