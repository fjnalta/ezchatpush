package eu.ezlife.ezChatPush.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ajo on 09.04.17.
 */
public class PropertiesService {

    private Properties prop;
    private static FileInputStream fis = null;
    private static FileOutputStream fos = null;

    public PropertiesService() {
        prop = new Properties();
        initializeProperties();
    }

    private void initializeProperties() {
        try {
            fis = new FileInputStream("config.properties");
            System.out.println("Properties File found, loading properties");
            loadProperties();
        } catch (IOException e) {
            System.out.println("No Properties File found, creating default");
            createProperties();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadProperties() {
        try {
            fis = new FileInputStream("config.properties");
            // load a properties file
            prop.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createProperties() {
        try {
            fos = new FileOutputStream("config.properties");

            prop.setProperty("appid", "changeme");
            prop.setProperty("database", "changeme");
            prop.setProperty("dbuser", "changeme");
            prop.setProperty("dbpassword", "changeme");
            prop.setProperty("openfireDatabase", "changeme");
            prop.setProperty("openfireDbuser", "changeme");
            prop.setProperty("openfireDbpassword", "changeme");

            // save properties to project root folder
            prop.store(fos, null);
            System.out.println("Created properties file");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Properties getProp() {
        return prop;
    }
}
