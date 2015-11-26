
package es.uma.pfc.implications.generator.events;

import java.util.Arrays;

/**
 * Event thrown when a generated syste is saved.
 * @author Dora Calderón
 */
public class SystemSaved {
    /**
     * Path of system saved.
     */
    private String [] paths;
    /**
     * Type of class which call to Implications Generator.
     */
    private Class<?> calledBy;

    /**
     * Constructor.
     * @param paths Paths of systems saved.
     */
    public SystemSaved(String ... paths) {
        if(paths != null) {
            this.paths = Arrays.copyOf(paths, paths.length);
        }
    }

    /**
     * Path of system saved.
     * @return the path
     */
    public String[] getPaths() {
        return paths;
    }

    /**
     * Paths of systems saved.
     * @param paths the path to set
     */
    public void setPath(String ... paths) {
        this.paths = paths;
    }

    /**
     * Returns the type of class which call to Implications Generator.
     * @return Type of class which call to Implications Generator.
     */
    public Class<?> getCalledBy() {
        return calledBy;
    }

    /**
     * Sets the type of class which call to Implications Generator.
     * @param calledBy Type of class which call to Implications Generator.
     */
    public void setCalledBy(Class<?> calledBy) {
        this.calledBy = calledBy;
    }
    
    
}
