
package es.uma.pfc.is.bench.domain.ws;

import java.util.Objects;

/**
 * User preference.
 */
public class Preference {
    /**
     * Name.
     */
    private String name;
    /**
     * Value.
     */
    private String value;

    /**
     * Constructor.
     */
    public Preference() {
    }

    /**
     * Constructor.
     * @param name Name.
     * @param value Value.
     */
    public Preference(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value.
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Preference other = (Preference) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
