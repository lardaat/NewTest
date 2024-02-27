package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    public static final Properties PROPERTIES = new Properties();

    static {
        loadPropertises();
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    private static void loadPropertises() {
       try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(inputStream);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    private PropertiesUtil(){

    }
}
