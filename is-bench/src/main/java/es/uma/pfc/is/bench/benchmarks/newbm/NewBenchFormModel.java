
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class NewBenchFormModel {
    /**
     * Name.
     */
    private StringProperty nameProperty;
    /**
     * Algorithms list.
     */
    private ListProperty<Algorithm> algorithmsListProperty;

    /**
     * Constructor.
     */
    public NewBenchFormModel() {
        nameProperty = new SimpleStringProperty("");
        algorithmsListProperty = new SimpleListProperty();
    }
    
    

    public String getName() {
        return (nameProperty != null) ? nameProperty.get() : null;
    }
    /**
     * Name.
     * @return the nameProperty
     */
    public StringProperty nameProperty() {
        return nameProperty;
    }


    /**
     * Algorithms list.
     * @return Algorithms list.
     */
    public List<Algorithm> getAlgorithmsList() {
        return (algorithmsListProperty != null) ? new ArrayList(algorithmsListProperty) : null;
    }
    /**
     * Algorithms list property.
     * @return the algorithmsListProperty
     */
    public ListProperty<Algorithm> algorithmsListProperty() {
        return algorithmsListProperty;
    }

    /**
     * Algorithms list.
     * @param algorithmsListProperty the algorithmsListProperty to set
     */
    public void setAlgorithmsListProperty(ListProperty<Algorithm> algorithmsListProperty) {
        this.algorithmsListProperty = algorithmsListProperty;
    }
    
    
}
