package es.uma.pfc.is.bench.business;



import es.uma.pfc.is.bench.domain.AlgorithmEntity;
import es.uma.pfc.is.bench.domain.Workspace;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
    /**
     * Persistence.
     */
    private WorkspacePersistence persistence;

    /**
     * Constructor.
     */
    public WorkspaceBean() {
        this.persistence = WorkspacePersistence.get();
    }

    
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
        return persistence.getWorkspace(path);
    }

    /**
     * Update a workspace adding new algorithms.
     * @param path Workspace path.
     * @param algorithms Algorithms to add.
     */
    public void addAlgorithms(String path, AlgorithmEntity ... algorithms) {
        if (algorithms == null) {
            throw new IllegalArgumentException("algorithms argument can't be null.");
        }
        Workspace ws = getWorkspace(path);
        if(ws == null) {
            throw new RuntimeException("Thew workspace not exits in " + path);
        }    
        ws.addAlgorithms(algorithms);
        WorkspacePersistence.update(ws);
    }
    
    /**
     * For test usage.
     * @param p 
     */
    protected void setPersistence(WorkspacePersistence p) {
        this.persistence = p;
    }
}
