
package es.uma.pfc.is.algorithms.io;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class PrintStream extends java.io.PrintStream {
    /**
     * If close when algorithm execution ends.<br/>
     * {@code true} by default.
     */
    private boolean closeAtFinish;

    public PrintStream(OutputStream out) {
        this(out, true);
    }

    
    public PrintStream(OutputStream out, boolean closeAtFinish) {
        super(new BufferedOutputStream(out));
        this.closeAtFinish = closeAtFinish;
    }

    /**
     * If close when algorithm execution ends.
     * @return the closeAtFinish
     */
    public boolean isCloseAtFinish() {
        return closeAtFinish;
    }

    /**
     * If close when algorithm execution ends.
     * @param closeAtFinish the closeAtFinish to set
     */
    public void setCloseAtFinish(boolean closeAtFinish) {
        this.closeAtFinish = closeAtFinish;
    }


}
