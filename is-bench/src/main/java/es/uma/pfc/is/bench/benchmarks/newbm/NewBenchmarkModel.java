
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class NewBenchmarkModel {
    /**
     * Algorithms list.
     */
    private ListProperty<Algorithm> algorithmsListProperty;

    /**
     * Name.
     */
    private StringProperty nameProperty;
    /**
     * Algorithms list.
     */
    private ListProperty<Algorithm> algorithmsSelectedProperty;

    /**
     * Constructor.
     */
    public NewBenchmarkModel() {
        nameProperty = new SimpleStringProperty("");
        algorithmsSelectedProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    
    /**
     * Algorithms list.
     * @return Algorithms list.
     */
    public List<Algorithm> getAlgorithmsList() {
        return (algorithmsListProperty != null) ? new ArrayList(algorithmsListProperty) : null;
    }
    
    /**
     * Algorihtms list property.
     * @return Algorithms ListProperty.
     */
    public ListProperty<Algorithm> algorithmsListProperty() {
        return algorithmsListProperty;
    }

    /**
     * Name.
     * @return Name.
     */
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
    public List<Algorithm> getAlgorithmsSelectedList() {
        return (algorithmsSelectedProperty != null) ? new ArrayList(algorithmsSelectedProperty) : null;
    }
    /**
     * Algorithms list property.
     * @return the algorithmsSelectedProperty
     */
    public ListProperty<Algorithm> algorithmsSelectedProperty() {
        return algorithmsSelectedProperty;
    }

    /**
     * Algorithms list.
     * @param algorithmsListProperty the algorithmsSelectedProperty to set
     */
    public void setAlgorithmsSelectedProperty(ListProperty<Algorithm> algorithmsListProperty) {
        this.algorithmsSelectedProperty = algorithmsListProperty;
    }
    
    
}
