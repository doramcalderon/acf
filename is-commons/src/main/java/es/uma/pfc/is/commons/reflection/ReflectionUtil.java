
package es.uma.pfc.is.commons.reflection;

import es.uma.pfc.is.commons.files.FileUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reflection utils.
 * @author Dora Calderón
 */
public class ReflectionUtil {
       /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    /**
     * Extensión .class *
     */
    public static final String CLASS_EXTENSION = ".class";
    /**
     * Extensión .jar *
     */
    public static final String JAR_EXTENSION = ".jar";
    /**
     * Clase con el package-info.*
     */
    private static final String PACKAGE_INFO_CLASS = "package-info.class";

    /**
     * Searches for a field in the given class and all of its super classes.
     *
     * @param clazz Class to start the search for the field
     * @param name Name of the field
     * @return The field that was found
     * @throws NoSuchFieldException
     */
    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
        Class<?> searchClass = clazz;
        Field field = null;
        while (field == null && searchClass != null) {
            try {
                field = searchClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                searchClass = searchClass.getSuperclass();
            }
        }
        if (field == null) {
            throw new NoSuchFieldException(clazz.getSimpleName() + "." + name); //$NON-NLS-1$
        }
        return field;
    }

    /**
     * Utility method to set a field to a value. If the field is not accessible, it will be set to be accessible.
     *
     * @param object Instance in which the value should be set
     * @param name Name of the field who's value should be set
     * @param value The value to be set
     */
    public static void setFieldValue(Object object, String name, Object value) {
        try {
            Field field = getField(object.getClass(), name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(object, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Could not set field value: " + object.getClass().getSimpleName() + "." + name, e); //$NON-NLS-1$ //$NON-NLS-2$
        }

    }

    /**
     * Utility method to read a field value. If the field is not accessible, it will be set to be accessible.
     *
     * @param object Instance in which the value should be read
     * @param name Name of the field who's value should be read
     * @return The value of the field
     */
    public static Object getFieldValue(Object object, String name) {
        try {
            Field field = getField(object.getClass(), name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Could not read field value: " + object.getClass().getSimpleName() + "." + name, e); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Returns {@code true} if the class is an implementation, that is, the class it is not an interface, and it is not
     * an abstract class.
     *
     * @param clazz Type.
     * @return {@code ture} if the class is an implementation, {@code false} otherwise.
     */
    public static boolean isImplementation(Class clazz) {
        return !(Modifier.isAbstract(clazz.getModifiers()) || Modifier.isInterface(clazz.getModifiers()));
    }

    /**
      * Returns the classes from a JAR.
     *
     * @param path Path del JAR.
     * @return Classes from a JAR.
     * @throws IOException If the file not found.
     */
    public static List<Class> getClassesFromJar(String path) throws IOException {
        List<Class> classes = null;

        if (path == null || !path.endsWith(".jar")) {
            throw new RuntimeException("El path no se corresponde con un JAR.");
        } else {
            try {
                classes = getClassesFromJar(new JarFile(path), Thread.currentThread().getContextClassLoader());

            } catch (IOException ioEx) {
                logger.error("Error al cargar el archivo " + path, ioEx);
                throw ioEx;
            }

        }

        return classes;
    }

    /**
     * Returns the classes from a JAR.
     * @param jar JAR.
     * @param loader ClassLoader.
     * @return Classes from a JAR.
     */
    public static List<Class> getClassesFromJar(JarFile jar, ClassLoader loader) {
        List<Class> classes = null;

        try {
            List<String> classNames = getClassNames(jar);
            classes = new ArrayList();

            for (String className : classNames) {
                classes.add(Class.forName(className, false, loader));
            }

        } catch (ClassNotFoundException | NoClassDefFoundError error) {
            logger.error("Error al cargar las clases del jar " + ((jar != null) ? jar.getName() : "null"), error);
            throw new RuntimeException(error);
        }
        
        return classes;
    }
    /**
     * Returns the classes from a JAR.
     * @param <T> Type.
     * @param jar JAR.
     * @param loader ClassLoader.
     * @param type Class type.
     * @return Classes from a JAR.
     */
    public static <T> List<Class<? extends T>> getClassesFromJar(JarFile jar, ClassLoader loader, Class<? extends T> type) {
        List<Class<? extends T>> classes = null;
        
        if(jar != null) {
            List<Class> allClasses = getClassesFromJar(jar, loader);
            classes = allClasses.stream().filter(c -> type.isAssignableFrom(c))
                                         .map(c -> (Class <? extends T>) c)
                                         .collect(Collectors.toList());
        }
        
        return classes;
    }
    /**
     * Returns the implementation classes of a type in a JAR file.
     * @param <T> Type.
     * @param jar JAR file.
     * @param loader Classloader.
     * @param type Type.
     * @return Implementation classes of a type in a JAR file.
     */
    public static <T> List<Class<? extends T>> getImplFromJar(JarFile jar, ClassLoader loader, Class<? extends T> type) {
        List<Class<? extends T>> classes = getClassesFromJar(jar, loader, type);
        return classes.stream().filter(clazz -> isImplementation(clazz)).collect(Collectors.toList());
    }

    /**
     * Returns the names of a JAR elements.
     * @param jar JAR.
     * @return The names of a JAR elements.
     */
    protected static List<String> getClassNames(JarFile jar) {
        List<String> classNames = null;

        if (jar != null) {
            List<JarEntry> jarEntries = Collections.list(jar.entries());
            classNames = new ArrayList();
            String entryName;

            for (JarEntry entry : jarEntries) {
                entryName = entry.getName();

                if (entryName.endsWith(CLASS_EXTENSION)
                        && !PACKAGE_INFO_CLASS.equalsIgnoreCase(entryName)) {
                    classNames.add(getClassName(entry.getName()));
                }
            }
        }
        return classNames;
    }
      /**
     * Returns the  <i>fully-qualified name </i> of the path parameter.
     *
     * @param path Path.
     * @return <i>fully-qualified name </i> of a path.
     */
    protected static String getClassName(String path) {
        String className = null;
        if (path != null) {

            className = path.replace("\\", ".").replace("/", ".").replace(CLASS_EXTENSION, "");
            if (className.startsWith(".")) {
                className = className.replaceFirst("\\.", "");
            }
        }
        return className;
    }
    
    /**
     * Añade al claspath el path donde se ubican los JAR's a validar.
     *
     * @param path path.
     * @return ClassLoader.
     */
    public static final URLClassLoader getClassLoader(Path path) throws IOException {
        ClassLoader parentLoader = Thread.currentThread().getContextClassLoader();
        URL[] jarsUrl = FileUtils.getURLs(path, "*.jar").toArray(new URL[]{});  
        URLClassLoader loader = new URLClassLoader(jarsUrl, parentLoader);

        return loader;
    }
}
