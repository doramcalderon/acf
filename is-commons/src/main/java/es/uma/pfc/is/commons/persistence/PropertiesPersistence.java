package es.uma.pfc.is.commons.persistence;

import es.uma.pfc.is.commons.strings.StringUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * Properties persistence utils.
 * @author Dora Calderón
 */
public class PropertiesPersistence {

    /**
     * Save values in a property file.
     * @param propertyFile Properties file path.
     * @param values Values.
     * @throws java.io.IOException
     */
    public static void save(String propertyFile, Properties values) throws IOException {
        if(values != null) {
            if(!StringUtils.isEmpty(propertyFile)) {
                String fileName = propertyFile;
                if (!fileName.endsWith(".properties")) {fileName = fileName.concat(".properties");}
                
                FileOutputStream output = new FileOutputStream(fileName);
                values.store(output, "");
            }
        }
    }
    /**
     * Load a property file in an Properties object.
     * @param propertyFile Properties file path.
     * @return Properties object.
     * @throws IOException 
     */
    public static Properties load(String propertyFile) throws IOException {
        Properties properties = null;
        if(!StringUtils.isEmpty(propertyFile)) {
            Path filePath = Paths.get(propertyFile);
            if(!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            properties = new Properties();
            try(FileInputStream is = new FileInputStream(propertyFile)) {
                properties.load(is);
            }
        }
        return properties;
    }
}
