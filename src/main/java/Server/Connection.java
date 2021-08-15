package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Connection
 */
public class Connection extends Thread {

  private String msg;
  private Socket client;
  private Server server;

  private BufferedReader in;
  private PrintWriter out;
  private byte[] buffer = new byte[256];

  public Connection(Socket socket, Server server) {

    this.server = server;
    this.client = socket;

    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    } catch (IOException e) {
      server.fail(e, "can'c Connect");
    } 
    System.out.println("User: " + server.getConnectCount());
    start();
  }

  @Override
  public void run() {
    int count = server.getConnectCount();
    out.println("you are is: ".concat(String.valueOf(count)));

    try {
      out.print("Your name: ");
      out.flush();
      msg = in.readLine();
      server.broadcast(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMsg(String msg) {
    out.println("Присоеденился: " + msg);
  }
}