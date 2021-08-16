package io.lindx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server {

  private boolean chprnt;
  private static long clientId;
  private Map<Long, Connection> connections;

  private final int PORT;

  public Server(final int port) {
    this.PORT = port;
    connections = new HashMap<>();
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      log("Server is started!");

      while (true) {
        log("Waiting connections...");
        Socket client = serverSocket.accept();
        log("Client connected!");
        new Connection(client, this).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void log(String string) {
    if (!chprnt) {
      System.out.println("\n-> start.\n\t│\n\t├── " + string + "\n\t│");
    } else {
      System.out.println("\t├── " + string + "\n\t│");
    }
    chprnt = true;
  }

  protected synchronized long generateId() {
    return clientId++;
  }
}
