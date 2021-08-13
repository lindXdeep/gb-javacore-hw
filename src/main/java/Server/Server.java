package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

  private int countConnect;
  private int port;

  private ServerSocket serverSocket;
  private Socket socket;

  private Connection connection;
  private List<Connection> connectionPool;

  public Server(int port) {

    this.port = port;
    this.connectionPool = new ArrayList<>();

    try {
      serverSocket = new ServerSocket(this.port);
    } catch (IOException e) {
      fail(e, "Can't open port");
    }
  }

  public void start() {
    System.out.println("Server Stared");
    try {
      while (true) {
        socket = serverSocket.accept();
        connection = new Connection(socket, this);
        connectionPool.add(connection);
        countConnect++;
      }
    } catch (IOException e) {
      fail(e, "Connection is close!");
    } finally {
      System.out.println("Server offline...");
      try {
        serverSocket.close();
      } catch (IOException e) {
        fail(e, "Can't disconnect");
      }
    }
  }

  public void broadcast(String msg) {
    for (Connection connect : connectionPool) {
      connect.sendMsg(msg);
    }
  }

  public void fail(IOException exception, String errorMsg) {
    System.out.println(errorMsg + " because: " + exception.getMessage());
  }

  public int getConnectCount() {
    return countConnect;
  }
}
