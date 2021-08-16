package io.lindx.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private final int PORT;

  public Server(final int port) {
    this.PORT = port;
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      print("Server is started!");
      Socket client = serverSocket.accept();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  private void print(String string) {
    System.out.println("\n\t" + string + "\n");
  }

}
