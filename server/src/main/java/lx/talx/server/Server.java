package lx.talx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lx.talx.server.net.Connection;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

/**
 * Server
 */
public class Server extends Thread {

  private final int PORT;

  public Server(int port) {
    this.PORT = port;
  }

  @Override
  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      Log.info("Server is started!");
      while (true) {
        Log.info("Waiting connections...");
        Socket socket = serverSocket.accept();
        Log.info("Client" + Util.getAddress(socket) + "connected!");
        new Connection(socket, this).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}