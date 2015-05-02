

package es.uma.pfc.is.bench.config;

import es.uma.pfc.is.bench.exception.ConfigurationException;
import es.uma.pfc.is.bench.uitls.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
     * Directorio de entradas por defecto.
     */
    public static final String DEFAULT_INPUT_PATH = DEFAULT_WORKSPACE_PATH + File.separator + "input";
    /**
     * Directorio de salidas por defecto.
     */
    public static final String DEFAULT_OUTPUT_PATH = DEFAULT_WORKSPACE_PATH + File.separator + "output";
    
    
    /**
     * Instancia única.
     */
    private static UserConfig me;
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
    private UserConfig() {
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
    private void initConfig() throws IOException {
        config = new Properties();
        
        File configFile = FileUtils.createIfNoExists(CONFIG_PATH);
        
        config.load(new FileInputStream(configFile));
    }
    
    /**
     * Inicializa el workspace por defecto.
     */
    private void initDefaultWorkspace() throws IOException {
        File defautlWs = FileUtils.createDirIfNoExists(DEFAULT_WORKSPACE_PATH);
        config.setProperty(DEFAULT_WORKSPACE, defautlWs.getAbsolutePath());
        
        FileUtils.createDirIfNoExists(DEFAULT_INPUT_PATH);
        FileUtils.createDirIfNoExists(DEFAULT_OUTPUT_PATH);
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
        return config.getProperty(DEFAULT_WORKSPACE);
    }
    /**
     * Establece el workspace por defecto.
     * @param workspace Path del workspace.
     */
    public void setDefaultWorkspace(String workspace) {
        config.setProperty(DEFAULT_WORKSPACE, workspace);
    }

    public File getDefaultInputDir() {
        return new File(getDefaultWorkspace() + File.separator + "input");
    }

    public File getDefaultOutputDir() {
        return new File(getDefaultWorkspace() + File.separator + "output");
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
