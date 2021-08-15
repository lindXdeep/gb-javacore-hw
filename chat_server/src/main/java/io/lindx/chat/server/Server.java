package io.lindx.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Server {

  private final int PORT;
  private int idConnect;
  private Map<Integer, Connection> connectionPool;

  private Socket socket;
  private ServerSocket serverSocket;

  public Server(final int port) {
    this.PORT = port;
    connectionPool = new HashMap<>();

    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() {

    System.out.println("Sverver stared.");

    try {

      while (true) {

        socket = serverSocket.accept();

        Connection connect = new Connection(this, socket);
        connect.start();

        


        connectionPool.put(generateId(), connect);

        System.out.println("User: " + getlastid() + " Connected!");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void broadcastMessage(String msg) {

    Iterator<Entry<Integer, Connection>> iterator = connectionPool.entrySet().iterator();

    while (iterator.hasNext()) {
      iterator.next().getValue().sendMsg("User: " + msg + "is connected!");
    }
  }

  private int generateId() {
    return idConnect++;
  }

  public int getlastid() {
    return idConnect;
  }
}
