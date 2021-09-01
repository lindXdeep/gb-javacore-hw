package lx.talx.server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lx.talx.server.auth.IUserAuthProvider;
import lx.talx.server.net.Connection;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

/**
 * Server
 */
public class Server extends Thread {

  private int PORT;
  private Socket socket;

  private IUserAuthProvider authProvider;

  public Server(int port) {
    this.PORT = port;
    this.authProvider = new IUserAuthProvider();
  }

  @Override
  public void run() {

    while (true) {
      try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        Log.info("Server is started on: -> " + PORT);
        while (true) {
          Log.info("Waiting connections...");
          socket = serverSocket.accept();
          Log.info("Client" + Util.getAddress(socket) + "connected!");
          new Connection(socket, this).start();
        }
      } catch (BindException e) {
        this.PORT = Util.getFreePort();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String getSocket() {
    return socket.getInetAddress().toString();
  }

  public IUserAuthProvider getAuthProvider() {
    return authProvider;
  }

}