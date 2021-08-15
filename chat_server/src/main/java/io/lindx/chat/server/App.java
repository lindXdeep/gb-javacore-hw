package io.lindx.chat.server;
/**
 * Char Server
 */
public class App {

  public static final int PORT = 8181;

  public static void main(String[] args) {
    Server chatServer = new Server(PORT);
    chatServer.start();
  }
}