package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.bench.domain.Workspace;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @since @author Dora Calderón
 */
public class WorkspacePersistence extends Persistence {
    protected static final Logger LOGGER = LoggerFactory.getLogger(WorkspacePersistence.class);
    
    
    /**
     * Persist a workspace in workspace.xml file.
     * @param ws Workspace.
     */
    public static void create(Workspace ws) {
         try {
            if (ws == null) {
                throw new IllegalArgumentException("ws argument can't be null.");
            }
            String workspaceFilePath = Paths.get(ws.getPath(), "workspace.xml").toString();
            persist(ws, workspaceFilePath, true);
        } catch (JAXBException ex) {
            LOGGER.error("Error creating a workspace.", ex);
        }
    }

    /**
     * Gets a workspace from a file path.
     * @param path Path.
     * @return Workspace.
     */
    public static Workspace getWorkspace(String path) {
        return read(Paths.get(path, "workspace.xml").toString(), Workspace.class);
    }
}
