/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.pfc.is.logging;

import es.uma.pfc.is.algorithms.AlgorithmOptions;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @since 
 * @author Dora Calderón
 */
public class AlgorithmLoggerFactory {
//
//    public static AlgorithmLogger getLogger(Class clazz) throws JoranException {
//        InputStream configFileStream = AlgorithmLoggerFactory.class.getResourceAsStream("log4j2.xml");
//        
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        JoranConfigurator configurator = new JoranConfigurator();
//        lc.reset();
//        configurator.setContext(lc);
//        configurator.doConfigure(configFileStream);
//
//        return new AlgorithmLogger(clazz.getName(), null);
//    }
    /**
     * Registered loggers.
     */
    private Map<String, AlgorithmLogger> loggers;
    /**
     * Single instance.
     */
    private static AlgorithmLoggerFactory me;

    private AlgorithmLoggerFactory() {
        loggers = new HashMap<>();
    }
    
    public static AlgorithmLoggerFactory get() {
        synchronized(AlgorithmLoggerFactory.class) {
            if(me == null) {
                me = new AlgorithmLoggerFactory();
            }
            return me;
        }
    }
    
    
    /**
     * Gets the logger registered with a name.<br/>
     * If the logger isn't registerd yet, this is created and registered.
     * @param name Name.
     * @return Logger registerd.
     */
    public AlgorithmLogger getLogger(String name) {
        AlgorithmLogger logger = loggers.get(name);
        if(logger == null)  {
            logger = new AlgorithmLogger(name, new AlgorithmOptions());
            loggers.put(name, logger);
        }
            
        return logger;
    }
}