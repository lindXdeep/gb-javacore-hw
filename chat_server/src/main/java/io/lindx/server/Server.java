package io.lindx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import io.lindx.server.dao.InMemoryAuthentication;

public class Server {

  private boolean chprnt;

  private Socket client;

  private final int PORT;

  private InMemoryAuthentication inMemory;
  private ConnectionPool connectionPool;

  private Thread thread;

  public Server(final int port) {
    this.PORT = port;
    this.inMemory = new InMemoryAuthentication();
    this.connectionPool = new ConnectionPool();
  }

  public void start() {

    thread = new Thread(() -> {

      try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        log("Server is started!");

        while (true) {
          log("Waiting connections...");
          client = serverSocket.accept();
          log("Client connected!");
          new Connection(client, this).start();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }

  protected void log(String string) {
    if (!chprnt) {
      System.out.println("\n-> start.\n\t│\n\t├── " + string + "\n\t│");
    } else {
      System.out.println("\t├── " + string + "\n\t│");
    }
    chprnt = true;
  }

  public InMemoryAuthentication getAuth() {
    return inMemory;
  }

  public ConnectionPool getConnectionPool() {
    return connectionPool;
  }

  public synchronized void killConnection(Connection connection) {

    connectionPool.remove(connection.getConnectId());
    try {
      connection.kill();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
