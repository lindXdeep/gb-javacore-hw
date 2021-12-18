package lx.lindx.drive.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Server
 */
public class Server extends Thread {

  private static Connection connection;
  private static final Logger LOG = LogManager.getLogger(Server.class);

  public Server() {
    LOG.info("test message");
  }

  public Server(int port) {

  }

  
}