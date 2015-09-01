

package es.uma.pfc.is.bench.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Preference model for a TableView.
 * @since 
 * @author Dora Calderón
 */
public class PreferenceModel {
    private StringProperty name;
    private StringProperty value;

    public PreferenceModel() {
        this(null, null);
    }

    public PreferenceModel(String name, String value) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getValue() {
        return value;
    }

    public void setValue(StringProperty value) {
        this.value = value;
    }
    
    
    
}
