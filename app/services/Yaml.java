package services;

import com.google.inject.Inject;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import play.Application;
import play.Environment;

import java.io.InputStream;

/**
 * Created by marcelsteffen on 24.08.16.
 */
public class Yaml {

    private Application application;

    @Inject
    public Yaml(Application application) {
        this.application = application;
    }

    /**
     * Load a Yaml file from the classpath.
     */
    public Object load(String resourceName) {
        return load(
                application.resourceAsStream(resourceName),
                application.classloader()
        );
    }

    /**
     * Load the specified InputStream as Yaml.
     *
     * @param classloader The classloader to use to instantiate Java objects.
     */
    public static Object load(InputStream is, ClassLoader classloader) {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml(new CustomClassLoaderConstructor(classloader));
        return yaml.load(is);
    }

}
