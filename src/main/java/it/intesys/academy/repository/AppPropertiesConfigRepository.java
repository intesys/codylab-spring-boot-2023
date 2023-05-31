package it.intesys.academy.repository;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Repository per pescare dei messaggi dal file di property
 */
public class AppPropertiesConfigRepository implements ConfigRepository {

    private static Properties appProperties;

    private static Properties appProperties() {
        if (appProperties == null) {
            Properties prop = new Properties();
            try (InputStream input = AppPropertiesConfigRepository.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                prop.load(input);
            } catch (IOException ex) {
                throw new IllegalStateException("Property load fail", ex);
            }
            appProperties = prop;
        }

        return appProperties;
    }


    public String getProperty(String propertyKey) {
        return appProperties().getProperty(propertyKey);
    }
}
