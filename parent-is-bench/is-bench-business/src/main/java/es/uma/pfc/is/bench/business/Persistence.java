/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.pfc.is.bench.business;

import es.uma.pfc.is.commons.files.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @since @author Dora Calderón
 */
public class Persistence {

    protected static void persist(Object entity, String outputPath, boolean createIfNoExists) throws JAXBException {
        if (entity != null && outputPath != null) {
            File outputFile = getOuputFile(outputPath, createIfNoExists);
            JAXBContext context = JAXBContext.newInstance(entity.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(entity, outputFile);
        }
    }
    /**
     * Returns the object from a file.
     * @param <T> Type.
     * @param path File path.
     * @param type Object type.
     * @return Object.
     */
    protected static <T> T read(String path, Class<T> type) {
        T object = null;
        if(Files.exists(Paths.get(path))) {
            object = JAXB.unmarshal(path, type);
        }
        return object;
    }
    
    protected static File getOuputFile(String outputPath, boolean createIfNoExists) {
        File outputFile;
        if (createIfNoExists) {
            try {
                outputFile = FileUtils.createIfNoExists(outputPath);
            } catch (IOException ex) {
                throw new RuntimeException("Error creating algorithms file.", ex);
            }
        } else if (!Files.exists(Paths.get(outputPath))) {
            throw new RuntimeException(outputPath + " not exists.");
        } else {
            outputFile = Paths.get(outputPath).toFile();
        }
        return outputFile;
    }
}
