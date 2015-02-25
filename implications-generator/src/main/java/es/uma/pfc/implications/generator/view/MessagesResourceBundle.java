package es.uma.pfc.implications.generator.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.PropertyResourceBundle;

/**
 * ResourceBundle con los mensajes y etiquetas mostrados en la aplicación.
 * @since 1.0.0
 * @author Dora Calderón
 */
public class MessagesResourceBundle extends PropertyResourceBundle {

    /**
     * Constructor.
     * @throws IOException 
     */
    public MessagesResourceBundle() throws IOException {
        this(Thread.currentThread().getContextClassLoader().getResourceAsStream("es/uma/pfc/implications/generator/controller/"));
    }

    /**
     * Constructor.
     * @param stream
     * @throws IOException 
     */
    public MessagesResourceBundle(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Constructor.
     * @param reader
     * @throws IOException 
     */
    public MessagesResourceBundle(Reader reader) throws IOException {
        super(reader);
    }
    
   
}
