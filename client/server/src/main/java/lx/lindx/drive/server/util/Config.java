package lx.lindx.drive.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App
 */
public class Config {

  private static final Logger LOG = LogManager.getLogger(Config.class);

  public static Properties srv;
  public static Properties cfg;
  private static final int PORT;

  static {
    srv = getProp("server.properties");
    cfg = getProp("config.properties");
    PORT = Integer.parseInt(cfg.getProperty("server.port"));
  }

  public static int getPort() {

    return (PORT > 0 && PORT <= 65535) ? PORT : 0;
  }
  
  public static Properties getProp(String conf) {

    Properties properties = new Properties();

    try (InputStream serv = Config.class.getClassLoader().getResourceAsStream(conf)) {

      if (serv == null) {
        throw new RuntimeException();
      }

      properties.load(serv);
      return properties;

    } catch (IOException e) {
      LOG.error(e.getMessage());
    }
    throw new RuntimeException();
  }
}