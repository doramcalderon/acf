
package es.uma.pfc.is.bench.benchmarks.newbm;

import es.uma.pfc.is.algorithms.Algorithm;
import es.uma.pfc.is.algorithms.AlgorithmInfo;
import java.io.File;
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
    private final ListProperty<AlgorithmInfo> algorithmsListProperty;
    /**
     * Algorhtms filtered list.
     */
    private final FilteredList<AlgorithmInfo> algorithmsFilteredList;

    /**
     * Name.
     */
    private final StringProperty nameProperty;
    /**
     * Input file path.
     */
    private final StringProperty inputDirProperty;
    /**
     * Input files list.
     */
    private ListProperty<File> inputFilesProperty;
    /**
     * Algorithms list.
     */
    private ListProperty<AlgorithmInfo> algorithmsSelectedProperty;

    /**
     * Constructor.
     */
    public NewBenchmarkModel() {
        nameProperty = new SimpleStringProperty("");
        inputDirProperty = new SimpleStringProperty("");
        inputFilesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsSelectedProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        algorithmsFilteredList = new FilteredList(algorithmsListProperty, p -> true);
    }
    public FilteredList<AlgorithmInfo> getAlgorithmsFilteredList() {
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
    public ListProperty<AlgorithmInfo> algorithmsListProperty() {
        return algorithmsListProperty;
    }
    /**
     * Input files list.
     * @return Algorithms list.
     */
    public List<File> getInputFilesList() {
        return (inputFilesProperty != null) ? new ArrayList(inputFilesProperty) : null;
    }
    
    /**
     * Input files list property.
     * @return Input files ListProperty.
     */
    public ListProperty<File> inputFilesListProperty() {
        return inputFilesProperty;
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
     * Input dir path.
     * @return Input dir path.
     */
    public String getInputDir() {
        return (inputDirProperty != null) ? inputDirProperty.get() : null;
    }
    /**
     * Input dir path property.
     * @return the inputProperty
     */
    public StringProperty inputProperty() {
        return inputDirProperty;
    }


    /**
     * Algorithms list.
     * @return Algorithms list.
     */
    public List<AlgorithmInfo> getAlgorithmsSelectedList() {
        return (algorithmsSelectedProperty != null) ? new ArrayList(algorithmsSelectedProperty) : null;
    }
    /**
     * Algorithms list property.
     * @return the algorithmsSelectedProperty
     */
    public ListProperty<AlgorithmInfo> algorithmsSelectedProperty() {
        return algorithmsSelectedProperty;
    }

    /**
     * Algorithms list.
     * @param algorithmsListProperty the algorithmsSelectedProperty to set
     */
    public void setAlgorithmsSelectedProperty(ListProperty<AlgorithmInfo> algorithmsListProperty) {
        this.algorithmsSelectedProperty = algorithmsListProperty;
    }
    
    
}
