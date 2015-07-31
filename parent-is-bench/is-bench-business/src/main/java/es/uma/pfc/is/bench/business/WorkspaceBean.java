package es.uma.pfc.is.bench.business;


import es.uma.pfc.is.bench.domain.Workspace;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.IOException;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workspace business methods.
 *
 * @author Dora Calderón
 */
public class WorkspaceBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceBean.class);

    /**
     * Directorio de entradas por defecto dentro del workspace.
     */
    public static final String DEFAULT_INPUT_PATH = "input";
    /**
     * Directorio de salidas por defecto dentro del workspace.
     */
    public static final String DEFAULT_OUTPUT_PATH = "output";

    public void createIfNoExists(Workspace workspace) {
        if(workspace != null && getWorkspace(workspace.getPath()) == null) {
            create(workspace);
        }
    }
    /**
     * Create a workspace.
     * @param workspace Workspace.
     */
    public void create(Workspace workspace) {

        try {
            FileUtils.createDirIfNoExists(workspace.getPath());
            FileUtils.createDirIfNoExists(Paths.get(workspace.getPath(), DEFAULT_INPUT_PATH).toString());
            FileUtils.createDirIfNoExists(Paths.get(workspace.getPath(), DEFAULT_OUTPUT_PATH).toString());
            WorkspacePersistence.create(workspace);
        } catch (IOException ex) {
            LOGGER.error("Error creating workspace.", ex);
        }

    }

    public Workspace getWorkspace(String path) {
        return WorkspacePersistence.getWorkspace(path);
    }

}
