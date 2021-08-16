package io.lindx.server;

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
  private PrintWriter out;
  private BufferedReader in;

  public Connection(Socket client, Server server) {

    this.server = server;
    this.client = client;

    try {
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    server.log("Connection created!");
  }

  @Override
  public void run() {
    authorize();
  }

  private void authorize() {
    while (true) {
      out.println("hello you are " + server.generateId());
      break;
    }
  }
}
