
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class NewBenchmarkModel {
    /**
     * Algorithms list.
     */
    private ListProperty<AlgorithmEntity> algorithmsListProperty;
    private FilteredList<AlgorithmEntity> algorithmsFilteredList;

    /**
     * Name.
     */
    private StringProperty nameProperty;
    /**
     * Input file path.
     */
    private StringProperty inputProperty;
    /**
     * Algorithms list.
     */
    private ListProperty<AlgorithmEntity> algorithmsSelectedProperty;

    /**
     * Constructor.
     */
    public NewBenchmarkModel() {
        nameProperty = new SimpleStringProperty("");
        inputProperty = new SimpleStringProperty("");
        algorithmsSelectedProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsFilteredList = new FilteredList(algorithmsListProperty, p -> true);
    }
    public FilteredList<AlgorithmEntity> getAlgorithmsFilteredList() {
        return algorithmsFilteredList;
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
    public ListProperty<AlgorithmEntity> algorithmsListProperty() {
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
     * Input file path.
     * @return Input file path.
     */
    public String getInput() {
        return (inputProperty != null) ? inputProperty.get() : null;
    }
    /**
     * Input file path property.
     * @return the inputProperty
     */
    public StringProperty inputProperty() {
        return inputProperty;
    }


    /**
     * Algorithms list.
     * @return Algorithms list.
     */
    public List<AlgorithmEntity> getAlgorithmsSelectedList() {
        return (algorithmsSelectedProperty != null) ? new ArrayList(algorithmsSelectedProperty) : null;
    }
    /**
     * Algorithms list property.
     * @return the algorithmsSelectedProperty
     */
    public ListProperty<AlgorithmEntity> algorithmsSelectedProperty() {
        return algorithmsSelectedProperty;
    }

    /**
     * Algorithms list.
     * @param algorithmsListProperty the algorithmsSelectedProperty to set
     */
    public void setAlgorithmsSelectedProperty(ListProperty<AlgorithmEntity> algorithmsListProperty) {
        this.algorithmsSelectedProperty = algorithmsListProperty;
    }
    
    
}
