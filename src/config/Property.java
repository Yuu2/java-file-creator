package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Yuu2
 * updated 2020.02.15
 */
public class Property {

  private Properties properties;

  public Property(String fileName) {
    properties = new Properties();
    load(properties, fileName);
  }

  private void load(Properties properties, String fileName) {

    try {

      properties.load(new FileReader(Env.PROPERTY_PATH + "/" + fileName));

    } catch(IOException ioe) {
      Program.exit();
    }
  }

  public String get(String key) {
    return properties.getProperty(key);
  }
}
