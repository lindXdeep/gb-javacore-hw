package io.lindx.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread {

  private Server server;
  private Socket client;

  private BufferedReader in;
  private PrintWriter out;

  private String name;

  public Connection(Server server, Socket client) throws IOException {
    this.server = server;
    this.client = client;

    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
  }

  @Override
  public void run() {

   

    out.println("Hello User: " + server.getlastid());
    out.println("name: ");

    try {
      name = in.readLine();
    } catch (IOException e) {
      System.out.println("Client " + server.getlastid() + "lost connection");
    }

    server.broadcastMessage(name);
  }

  public void sendMsg(String msg) {
    out.println(msg);
  }

  public void setId(int generateId) {
  }
}
