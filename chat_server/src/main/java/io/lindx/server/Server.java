package io.lindx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

  private boolean chprnt;

  private final int PORT;

  public Server(final int port) {
    this.PORT = port;
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      log("Server is started!");

      while (true) {
        log("Waiting connections...");
        Socket client = serverSocket.accept();
        log("Client connected!");
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
}
