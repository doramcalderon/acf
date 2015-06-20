

package es.uma.pfc.is.bench.algorithms;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

/**
 * Model form Algorithms view.
 * @author Dora Calderón
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

    public AlgorithmsModel() {
        this.name = new SimpleStringProperty();    
        this.shortName = new SimpleStringProperty();
        this.className = new SimpleStringProperty();
    }
    
    

    /**
     * Return the name property.
     * @return StringProperty.
     */
    public StringProperty getNameProperty() {
        return name;
    }
    
    /**
     * Name.
     * @return the name
     */
    public String getName() {
        return getStringValue(name);
    }

    /**
     * Name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    
    /**
     * Return the short name property.
     * @return StringProperty.
     */
    public StringProperty getShortNameProperty() {
        return shortName;
    }
    
    /**
     * Short name.
     * @return the shortName
     */
    public String getShortName() {
        return getStringValue(shortName);
    }

    /**
     * Short name.
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    /**
     * Return the class name property.
     * @return StringProperty.
     */
    public StringProperty getClassNameProperty() {
        return className;
    }
    
    /**
     * Full qualified name of algorithm implementation.
     * @return the className
     */
    public String getClassName() {
        return getStringValue(className);
    }

    /**
     * Full qualified name of algorithm implementation.
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className.set(className);
    }
    
    /**
     * Get the value of a StringPRoperty.
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
    
    
}
