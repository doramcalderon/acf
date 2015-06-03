

package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.exception.ConfigurationException;
import es.uma.pfc.is.bench.uitls.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuración de usuario.
 * @author Dora Calderón
 */
public class UserConfig implements UserConfigProperties {
    /** Path del archivo de configruación.**/
    public static final String CONFIG_PATH = System.getProperty("user.home") 
                                             + File.separator + ".isbench"
                                             + File.separator + "/isbench.properties";
    /**
     * Path del workspace por defecto.
     */
    public static final String DEFAULT_WORKSPACE_PATH = System.getProperty("user.home") 
                                                        + File.separator + ".isbench" 
                                                        + File.separator + "default";
    /**
     * Directorio de entradas por defecto dentro del workspace.
     */
    public static final String DEFAULT_INPUT_PATH =  "input";
    /**
     * Directorio de salidas por defecto dentro del workspace.
     */
    public static final String DEFAULT_OUTPUT_PATH =  "output";
    
    
    /**
     * Instancia única.
     */
    protected static UserConfig me;
    /**
     * Configuración.
     */
    private Properties config;

    /**
     * Constructor.<br/>
     * Carga la configuración del archivo <home_usuario>/.isbench/isbench.properties.<br/>
     * Si el archivo no existe, lo crea con las propiedades básicas por defecto.
     * @throws ConfigurationException Si se produce algún error al cargar el archivo de configuración.
     */
    protected UserConfig() {
        try {
            initConfig();
            initDefaultWorkspace();
        } catch (IOException ex) {
            throw new ConfigurationException("Error al inicializar la configuración.", ex);
        }
    }
    /**
     * Carga el archivo isbench.properties y lo crea si no existe.
     * @throws IOException Eror al leer / escribir en el archivo de configuración.
     */
    protected final void initConfig() throws IOException {
        config = new Properties();
        
        File configFile = FileUtils.createIfNoExists(CONFIG_PATH);
        
        config.load(new FileInputStream(configFile));
    }
    
    /**
     * Inicializa el workspace por defecto.
     */
    protected final void initDefaultWorkspace() throws IOException {
        String defaultWsPath = config.getProperty(DEFAULT_WORKSPACE_PROPERTY);
        if(defaultWsPath == null || defaultWsPath.trim().length() == 0) {
            defaultWsPath = DEFAULT_WORKSPACE_PATH;
        } 
        FileUtils.createDirIfNoExists(defaultWsPath);
        FileUtils.createDirIfNoExists(defaultWsPath + File.separator + DEFAULT_INPUT_PATH);
        FileUtils.createDirIfNoExists(defaultWsPath + File.separator + DEFAULT_OUTPUT_PATH);
        config.setProperty(DEFAULT_WORKSPACE_PROPERTY, defaultWsPath);
        save();    
    }
    
    /**
     * Devuelve una instancia única de UserConfig.
     * @return Configuración del usuario.
     */
    public static UserConfig get() {
        if(me == null) {
            me = new UserConfig();
        }
        return me;
    }
    
    /**
     * Workspace por defecto.
     * @return Path del workspace.
     */
    public String getDefaultWorkspace() {
        return config.getProperty(DEFAULT_WORKSPACE_PROPERTY);
    }
    /**
     * Establece el workspace por defecto.
     * @param workspace Path del workspace.
     */
    public void setDefaultWorkspace(String workspace) {
        try {
            FileUtils.createDirIfNoExists(workspace);
            config.setProperty(DEFAULT_WORKSPACE_PROPERTY, workspace);
        } catch (IOException ex) {
            throw new ConfigurationException(workspace + " not exists.", ex);
        }
    }

    public File getDefaultInputDir() {
        String inputDir = getDefaultWorkspace() + File.separator + "input";
        if (!Files.exists(Paths.get(inputDir))) {
            inputDir = getDefaultWorkspace();
        }
        return new File(inputDir);
    }

    public File getDefaultOutputDir() {
        String outputDir=  getDefaultWorkspace() + File.separator + "output";
        if (!Files.exists(Paths.get(outputDir))) {
            outputDir = getDefaultWorkspace();
        }
        return new File(outputDir);
    }
    /**
     * Establece el valor de una propiedad en la configuración.
     * @param key Nombre.
     * @param value Valor.
     * @return Valor antiguo.
     */
    public Object setProperty(String key, String value) {
        return config.setProperty(key, value);
    }

    /**
     * Devuelve el valor de una propiedad en la configuración.
     * @param key Nombre.
     * @return Valor.
     */
    public String getProperty(String key) {
        return config.getProperty(key);
    }
    
    /**
     * Persiste los cambios en el fichero de configuración.
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
     * @return Properties.
     */
    protected Properties getConfig() {
        return config;
    }
}