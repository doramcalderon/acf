
package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.commons.workspace.Workspace;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ListProperty;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dora Calderón
 */
public class UserConfigModelTest {
    
    public UserConfigModelTest() {
    }

    /**
     * Test of workspacesListProperty method, of class UserConfigModel.
     */
    @Test
    public void testWorkspacesListProperty() {
        UserConfigModel model = new UserConfigModel();
        ListProperty list = model.workspacesListProperty();
        
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * Test of setWorkspacesList method, of class UserConfigModel.
     */
    @Test
    public void testSetWorkspacesList() {
        Workspace ws1 = new Workspace("ws1", "");
        Workspace ws2 = new Workspace("ws2", "");
        Workspace ws3 = new Workspace("ws3", "");
        
        List<Workspace> workspaces = Arrays.asList(ws1, ws2, ws3);
        UserConfigModel model = new UserConfigModel();
        model.setWorkspacesList(workspaces);
        
        ListProperty list = model.workspacesListProperty();
        
        assertNotNull(list);
        assertEquals(3, list.size());
        assertTrue(list.contains(ws1));
        assertTrue(list.contains(ws2));
        assertTrue(list.contains(ws3));
    }
    
}
