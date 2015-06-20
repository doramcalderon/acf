
package es.uma.pfc.is.commons.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Dora Calderón
 */
public class PropertiesPersistenceTest {
    
    public PropertiesPersistenceTest() {
    }

    @BeforeClass
    public static void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("test.properties"));
    }

    /**
     * Test of save method, of class PropertiesPersistence.
     */
    @Test
    public void testSave() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("name", "test");
        PropertiesPersistence.save("test.properties", properties);

        File file = new File("test.properties");
        assertTrue(file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            assertTrue(reader.lines().anyMatch(line -> line.equals("name=test")));
        }
    }
  
    /**
     * Test of save method, of class PropertiesPersistence.
     */
    @Test
    public void testSaveWithoutExt() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("name", "test");
        PropertiesPersistence.save("test", properties);

        File file = new File("test.properties");
        assertTrue(file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            assertTrue(reader.lines().anyMatch(line -> line.equals("name=test")));
        }
    }
    /**
     * Test of load method, of class PropertiesPersistence.
     */
    @Test
    public void testLoad() throws Exception {
        Properties properties = PropertiesPersistence.load("test.properties");
        
        assertNotNull(properties);
        File file = new File("test.properties");
        assertTrue(file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            assertTrue(reader.lines().anyMatch(line -> line.equals("name=test")));
        }
    }
    
}
