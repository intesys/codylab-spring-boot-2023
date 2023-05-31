package it.intesys.academy;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
public class AppConfig {
    public static Properties appProperties;
    public static Properties appProperties(){
        if(appProperties == null){
            Properties prop = new Properties();
            try(InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")){
                prop.load(input);
            }catch (IOException ex){
                throw new IllegalStateException("property load fail", ex);
            }
            appProperties = prop;

        }
        return appProperties;
    }
}
