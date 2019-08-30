package propertyManager;

import logManager.LogManager;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyManager {

    private static Properties prop = new Properties();

    /**
     * Fetch property from config.properties file
     *
     * @param property Property whose value is required
     * @return value of property received as parameter
     * @author anuj gupta
     */
    public static String getProperty(String property) {

        String filePath = System.getProperty("user.dir") + "/src/test/resources/properties/config.properties";

        try {
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);
        } catch (Exception e) {
            LogManager.error(e.getMessage());
        }
        return prop.getProperty(property);
    }
}
