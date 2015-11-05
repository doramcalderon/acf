

package es.uma.pfc.is.bench.algorithms;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Model of Algorithms view.
 */
public class AlgorithmsModel {
    /**
     * Name.
     */
    private StringProperty name;
    /**
     * Short name.
     */
    private StringProperty shortName;
    /**
     * Full qualified name of algorithm implementation.
     */
    private StringProperty className;
    
    /**
     * Class algorithms list found in the classpath.
     */
    private ListProperty<String> algorithmsProperty;
    

    /**
     * Constructor.
     */
    public AlgorithmsModel() {
        this.name = new SimpleStringProperty();    
        this.shortName = new SimpleStringProperty();
        this.className = new SimpleStringProperty();
        this.algorithmsProperty = new SimpleListProperty<>();
    }
    
    

    /**
     * Returns the name property.
     * @return StringProperty.
     */
    public StringProperty getNameProperty() {
        return name;
    }
    
    /**
     * Returns the algorithm name.
     * @return the name
     */
    public String getName() {
        return getStringValue(name);
    }

    /**
     * Sets the algorithm name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
        if (!StringUtils.isEmpty(name) 
                && ((this.shortName == null) || (this.shortName.isEmpty().get()))) {
            if(name.length() < 2) {
                shortName.setValue(name);
            } else {
                shortName.setValue(name.substring(0, 1));
            }
        }
    }

    
    /**
     * Returns the short name property.
     * @return StringProperty.
     */
    public StringProperty getShortNameProperty() {
        return shortName;
    }
    
    /**
     * Returns the algorithm short name.
     * @return the shortName
     */
    public String getShortName() {
        return getStringValue(shortName);
    }

    /**
     * Sets the algorithm short name.
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    /**
     * Returns the class name property.
     * @return StringProperty.
     */
    public StringProperty getClassNameProperty() {
        return className;
    }
    
    /**
     * Returns the full qualified name of algorithm implementation.
     * @return the className
     */
    public String getClassName() {
        return getStringValue(className);
    }

    /**
     * Sets the full qualified name of algorithm implementation.
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className.set(className);
    }
    
    /**
     * Get the value of a StringProperty.
     * @param property Property.
     * @return Value of the StringProperty. {@code null} if property or its value is null.
     */
    protected String getStringValue(StringProperty property) {
        String value = null;
        if(property != null) {
            value = property.get();
        }
        return value;
    }
    
    /**
     * Algorithms list.
     * @return the algorithms
     */
    public ListProperty<String> algorithmsProperty() {
        return algorithmsProperty;
    }

    /**
     * Sets the algorithms list.
     * @param algorithms the algorithms to set
     */
    public void setAlgorithms(List<String> algorithms) {
        algorithmsProperty.set(FXCollections.observableList(algorithms));
    }
    
    
}
