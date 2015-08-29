package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.business.AlgorithmsBean;
import es.uma.pfc.is.bench.domain.Algorithms;
import es.uma.pfc.is.bench.business.WorkspaceBean;
import static es.uma.pfc.is.bench.business.WorkspaceBean.DEFAULT_INPUT_PATH;
import static es.uma.pfc.is.bench.business.WorkspaceBean.DEFAULT_OUTPUT_PATH;
import es.uma.pfc.is.bench.domain.Workspace;
import es.uma.pfc.is.bench.exception.ConfigurationException;
import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuración de usuario.
 *
 * @author Dora Calderón
 */
public class ConfigManager implements UserConfigProperties {

    /**
     * Path del archivo de configruación.*
     */
    public static final String CONFIG_PATH = Paths.get(System.getProperty("user.home"),
            ".isbench", "isbench.properties").toString();
    /**
     * Path del workspace por defecto.
     */
    public static final String DEFAULT_WORKSPACE_PATH = Paths.get(System.getProperty("user.home"),
            ".isbench", "default").toString();

    /**
     * File that contains the algorithms to load.
     */
    public static final String ALGORITHMS_FILE = "algorithms.xml";

    /**
     * Instancia única.
     */
    protected static ConfigManager me;
    /**
     * Configuración.
     */
    private Properties config;
    private WorkspaceBean wsBean;

    /**
     * Constructor.<br/>
     * Carga la configuración del archivo <home_usuario>/.isbench/isbench.properties.<br/>
     * Si el archivo no existe, lo crea con las propiedades básicas por defecto.
     *
     * @throws ConfigurationException Si se produce algún error al cargar el archivo de configuración.
     */
    protected ConfigManager() {
        wsBean = new WorkspaceBean();
        try {
            initConfig();
            initDefaultWorkspace();
        } catch (IOException ex) {
            throw new ConfigurationException("Error al inicializar la configuración.", ex);
        }
    }

    /**
     * Carga el archivo isbench.properties y lo crea si no existe.
     *
     * @throws IOException Eror al leer / escribir en el archivo de configuración.
     */
    protected final void initConfig() throws IOException {
        config = new Properties();

        File configFile = FileUtils.createIfNoExists(CONFIG_PATH);

        config.load(new FileInputStream(configFile));
    }

    /**
     * Inicializa el workspace por defecto.
     *
     * @throws java.io.IOException
     */
    protected final void initDefaultWorkspace() throws IOException {
        String defaultWsPath = config.getProperty(DEFAULT_WORKSPACE_PROPERTY);
        if (defaultWsPath == null || defaultWsPath.trim().length() == 0) {
            defaultWsPath = DEFAULT_WORKSPACE_PATH;
            wsBean.createIfNoExists(new Workspace(defaultWsPath));
            config.setProperty(DEFAULT_WORKSPACE_PROPERTY, defaultWsPath);
            save();
        }
    }

    /**
     * Devuelve una instancia única de ConfigManager.
     *
     * @return Configuración del usuario.
     */
    public static ConfigManager get() {
        if (me == null) {
            me = new ConfigManager();
        }
        return me;
    }

    /**
     * Workspace por defecto.
     *
     * @return Path del workspace.
     */
    public String getDefaultWorkspace() {
        return config.getProperty(DEFAULT_WORKSPACE_PROPERTY);
    }

    /**
     * Establece el workspace por defecto.
     *
     * @param workspace Path del workspace.
     */
    public void setDefaultWorkspace(String workspace) {
        wsBean.createIfNoExists(new Workspace(workspace));
        config.setProperty(DEFAULT_WORKSPACE_PROPERTY, workspace);
    }

    public File getDefaultInputDir() {
        String inputDir = getDefaultWorkspace() + File.separator + "input";
        if (!Files.exists(Paths.get(inputDir))) {
            inputDir = getDefaultWorkspace();
        }
        return new File(inputDir);
    }

    public File getDefaultOutputDir() {
        String outputDir = getDefaultWorkspace() + File.separator + "output";
        if (!Files.exists(Paths.get(outputDir))) {
            outputDir = getDefaultWorkspace();
        }
        return new File(outputDir);
    }

    /**
     * Return the properties with the algorithms to load.
     *
     * @return Properties.{@code null} if {@link #ALGORITHMS_FILE} doesn't exist.
     */
    @Deprecated
    public File getAlgorithmsFile() {
        return null;
    }

    /**
     * Establece el valor de una propiedad en la configuración.
     *
     * @param key Nombre.
     * @param value Valor.
     * @return Valor antiguo.
     */
    public Object setProperty(String key, String value) {
        return config.setProperty(key, value);
    }

    /**
     * Devuelve el valor de una propiedad en la configuración.
     *
     * @param key Nombre.
     * @return Valor.
     */
    public String getProperty(String key) {
        return config.getProperty(key);
    }

    /**
     * Persiste los cambios en el fichero de configuración.
     *
     * @throws IOException Error al guardar los cambios.
     */
    public void save() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(CONFIG_PATH));
            config.store(fos, null);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Configuración.
     *
     * @return Properties.
     */
    protected Properties getConfig() {
        return config;
    }
}
