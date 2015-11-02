
package es.uma.pfc.is.algorithms.util;

import java.util.Set;
import java.util.TreeSet;

/**
 * Sets utilities.
 * @author Dora Calderón
 */
public class Sets {
    /**
     * Sets union.
     * @param set1 Set.
     * @param set2 Set.
     * @return Set with the elemens of the set1 and set2. If one of them is null, the other is returned.
     */
    public static final TreeSet  union(final Set set1, final Set set2) {
        TreeSet union;
        if(set1 == null && set2 == null) {
            union = null;
        } else if(set1 == null) {
            union = new TreeSet(set2);
        } else if (set2 == null) {
            union = new TreeSet(set1);
        } else {
            union = new TreeSet(set1);
            union.addAll(set2);
        }
        
        return union;
    }

    /**
     * Sets intersection.
     * @param set1 Set.
     * @param set2 Set.
     * @return Intersection of set1 and set2.<br/>
     * {@code null} if any of them is null.
     */
    public static final TreeSet intersection(final Set set1, final Set set2) {
        TreeSet intersection = null;
        if (set1 != null && set2 != null) {
            intersection = new TreeSet(set1);
            intersection.retainAll(set2);
        }
        return intersection;
    }
    /**
     * Sets difference.
     * @param set1 Set.
     * @param set2 Set.
     * @return Difference sets.<br/>
     * {@code null} if any of them is null.
     */
    public static final TreeSet difference(final Set set1, final Set set2) {
        TreeSet difference = null;
        if (set1 != null && set2 != null) {
            difference = new TreeSet(set1);
            difference.removeAll(set2);
        }
        return difference;
    }
    
    
    /**
     * Returns if a set is empty or null.
     * @param set Set.
     * @return {@code true} if the set is empty or null, {@code false} otherwise.
     */
    public static final boolean isEmpty(final Set set) {
        return (set == null || set.isEmpty());
    }
}
