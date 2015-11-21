

package es.uma.pfc.is.algorithms;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Entity with an algorithm attributes.
 * @author Dora Calderón
 */
@XmlRootElement(name="algorithm")
public class AlgorithmInfo {
    /**
     * Name.
     */
    private String name;
    /**
     * Short name.
     */
    private String shortName;
    /**
     * Algorithm implementation class.
     */
    private String type;

    /**
     * Constructor.
     */
    public AlgorithmInfo() {
    }

    /**
     * Constructs an AlgorithmInfo instance from an algorithm implementation.
     * @param alg Algorithm.
     */
    public AlgorithmInfo(Algorithm alg) {
        if(alg != null) {
            this.name = alg.getName();
            this.shortName = alg.getShortName();
            this.type = alg.getClass().getName();
        }
    }
    
    

    /**
     * Algorithm name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the algorithm name.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Algorithm short name.
     * @return the short name.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the algorithm short name.
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Class of implementation.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Class of implementation.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return (this.shortName != null) ? this.shortName : String.valueOf(this.getName());
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.shortName);
        hash = 17 * hash + Objects.hashCode(this.type);
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
        final AlgorithmInfo other = (AlgorithmInfo) obj;
        
        if(name != null) {
            if(!name.trim().equalsIgnoreCase(StringUtils.trim(other.name))) {
                return false;
            }
        } else if (other.name != null) {
            return false;
        }
        if(shortName != null) {
            if(!shortName.trim().equalsIgnoreCase(StringUtils.trim(other.shortName))) {
                return false;
            }
        } else if (other.shortName != null) {
            return false;
        }
        
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    
    
}
